require "./target_typescript"

class TypeScriptWebTarget < TypeScriptTarget
  def gen
    @io << <<-END
import * as moment from "moment";
import {UAParser} from "ua-parser-js";

const baseUrl = #{@ast.options.url.inspect};
let useStaging = false;

export function setStaging(use: boolean) {
  useStaging = !!use;
}

END

    @ast.struct_types.each do |t|
      @io << t.typescript_definition
      @io << "\n\n"
    end

    @ast.enum_types.each do |t|
      @io << t.typescript_definition
      @io << "\n\n"
    end

    @ast.operations.each do |op|
      @io << "export async function #{op.pretty_name}#{operation_args(op)}: Promise<#{operation_ret(op)}> {\n"
      if op.args.size > 0
        @io << "  const args = {\n"
        op.args.each do |arg|
          @io << ident ident "#{arg.name}: #{arg.type.typescript_encode(arg.name)},"
          @io << "\n"
        end
        @io << "  };\n"
      end

      @io << "  "
      @io << "const ret = " unless op.return_type.is_a? AST::VoidPrimitiveType
      @io << "await makeRequest({name: #{op.pretty_name.inspect}, #{op.args.size > 0 ? "args" : "args: {}"}});\n"
      @io << ident "return " + op.return_type.typescript_decode("ret") + ";"
      @io << "\n"
      @io << "}\n\n"
    end

    @io << <<-END
//////////////////////////////////////////////////////

async function device() {
  const parser = new UAParser();
  parser.setUA(navigator.userAgent);
  const agent = parser.getResult();
  const me = document.currentScript as HTMLScriptElement;
  const device: any = {
    type: "web",
    platform: {
       browser: agent.browser.name,
       browserVersion: agent.browser.version,
       os: agent.os.name,
       osVersion: agent.os.version
    },
    screen: {
      width: screen.width,
      height: screen.height
    },
    version: me ? me.src : "",
    language: navigator.language
  };
  const deviceId = localStorage.getItem("deviceId");
  if (deviceId)
    device.id = deviceId;
  return device;
}

function randomBytesHex(len: number) {
  let hex = "";
  for (let i = 0; i < 2 * len; ++i)
    hex += "0123456789abcdef"[Math.floor(Math.random()*16)];
  return hex;
}

export interface ListenerTypes {
  fail: (e: Error, name: string, args: any) => void;
  fatal: (e: Error, name: string, args: any) => void;
  success: (res: string, name: string, args: any) => void;
}

type HookArray = Array<Function>;
export type Listenables = keyof ListenerTypes;
export type ListenersDict = { [k in Listenables]: Array<ListenerTypes[k]> };

const listenersDict: ListenersDict = {
  fail: [],
  fatal: [],
  success: [],
};

export function addEventListener(listenable: Listenables, hook: ListenerTypes[typeof listenable]) {
  const listeners: HookArray = listenersDict[listenable];
  listeners.push(hook);
}

export function removeEventListener(listenable: Listenables, hook: ListenerTypes[typeof listenable]) {
  const listeners: HookArray = listenersDict[listenable];
  listenersDict[listenable] = listeners.filter(h => h !== hook) as any;
}

async function makeRequest({name, args}: {name: string, args: any}) {
  const deviceData = await device();
  return new Promise<any>((resolve, reject) => {
    const req = new XMLHttpRequest();
    req.open("POST", "https://" + baseUrl + (useStaging ? "-staging" : "") + "/" + name);
    const body = {
      id: randomBytesHex(8),
      device: deviceData,
      name: name,
      args: args
    };
    req.onreadystatechange = () => {
      if (req.readyState !== 4) return;
      try {
        const response = JSON.parse(req.responseText);

        try {
          localStorage.setItem("deviceId", response.deviceId);
          if (response.ok) {
            resolve(response.result);
            listenersDict["success"].forEach(hook => hook(response.result, name, args));
          } else {
            reject(response.error);
            listenersDict["fail"].forEach(hook => hook(new Error((response.error || "").toString()), name, args));
          }
        } catch (e) {
          console.error(e);
          reject({type: "Fatal", message: e.toString()});
          listenersDict["fatal"].forEach(hook => hook(e, name, args));
        }
      } catch (e) {
        console.error(e);
        reject({type: "BadFormattedResponse", message: `Response couldn't be parsed as JSON (${req.responseText}):\\n${e.toString()}`});
        listenersDict["fatal"].forEach(hook => hook(e, name, args));
      }
    };
    req.send(JSON.stringify(body));
  });
}

END
  end

  def native_type(t : AST::Type)
    if t.is_a? AST::OptionalType
      t.typescript_native_type + " | undefined"
    else
      t.typescript_native_type
    end
  end
end

Target.register(TypeScriptWebTarget, target_name: "typescript_web")
