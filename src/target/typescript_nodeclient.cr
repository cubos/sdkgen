require "./target"

class TypeScriptNodeClient < Target
  def gen
    @io << <<-END
import * as moment from "moment";
import * as https from "https";
import { URL } from "url";

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
      args = op.args.map { |arg| "#{arg.name}: #{arg.type.typescript_native_type}" }
      @io << "export async function #{op.pretty_name}(#{args.join(", ")}): Promise<#{op.return_type.typescript_native_type}> {\n"
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

let fallbackDeviceId: string | null = null;

function setDeviceId(deviceId: string) {
  fallbackDeviceId = deviceId;
  try {
    localStorage.setItem("deviceId", deviceId);
  } catch (e) {}
}

function getDeviceId() {
  try {
    return localStorage.getItem("deviceId");
  } catch (e) {}
  return fallbackDeviceId;
}

async function device() {
  const device: any = {
    type: "node",
  };
  const deviceId = getDeviceId();
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
  const body = {
    id: randomBytesHex(8),
    device: deviceData,
    name: name,
    args: args
  };

  const url = new URL("https://" + baseUrl + (useStaging ? "-staging" : "") + "/" + name);
  const options = {
    hostname: url.hostname,
    path: url.pathname,
    method: 'POST',
  };
  
  return new Promise<any>((resolve, reject) => {
    const req = https.request(options, (resp) => {
      let data = '';
      resp.on('data', (chunk) => {
        data += chunk;
      });
      resp.on('end', () => {
        try {
          const response = JSON.parse(data);

          try {
            setDeviceId(response.deviceId);
            if (response.ok) {
              resolve(response.result);
              listenersDict["success"].forEach(hook => hook(response.result, name, args));
            } else {
              reject(response.error);
              listenersDict["fail"].forEach(hook => hook(response.error, name, args));
            }
          } catch (e) {
            console.error(e);
            reject({type: "Fatal", message: e.toString()});
            listenersDict["fatal"].forEach(hook => hook(e, name, args));
          }
        } catch (e) {
          console.error(e);
          reject({type: "BadFormattedResponse", message: `Response couldn't be parsed as JSON (${data}):\\n${e.toString()}`});
          listenersDict["fatal"].forEach(hook => hook(e, name, args));
        }
      });
    
    });

    req.on('error', (e) => {
      console.error(`problem with request: ${e.message}`);
    });

    // write data to request body
    req.write(JSON.stringify(body));
    req.end();
  });
}

END
  end
end

Target.register(TypeScriptNodeClient, target_name: "typescript_nodeclient")
