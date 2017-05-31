require "./target_typescript"

class TypeScriptWebTarget < TypeScriptTarget
  def gen
    @io << <<-END
import * as moment from "moment";
import {UAParser} from "ua-parser-js";

const baseUrl = #{@ast.options.url.inspect};


END

    @ast.struct_types.each do |t|
      @io << generate_struct_type(t)
      @io << "\n\n"
    end

    @ast.enum_types.each do |t|
      @io << generate_enum_type(t)
      @io << "\n\n"
    end

    @ast.operations.each do |op|
      @io << "export async function #{op.pretty_name}#{operation_args(op)}: Promise<#{operation_ret(op)}> {\n"
      if op.args.size > 0
        @io << "  const args = {\n"
        op.args.each do |arg|
          @io << ident ident "#{arg.name}: #{type_to_json(arg.type, arg.name)},"
          @io << "\n"
        end
        @io << "  };\n"
      end

      @io << "  "
      @io << "const ret: #{native_type op.return_type} = " unless op.return_type.is_a? AST::VoidPrimitiveType
      @io << "await makeRequest({name: #{op.pretty_name.inspect}, #{op.args.size > 0 ? "args" : "args: {}"}});\n"
      @io << ident "return " + type_from_json(op.return_type, "ret") + ";"
      @io << "\n"
      @io << "}\n\n"
    end

    @io << <<-END
//////////////////////////////////////////////////////

async function device() {
  const ip = localStorage.getItem("ip") || await getIP();
  const parser = new UAParser();
  parser.setUA(navigator.userAgent);
  const agent = parser.getResult();
  const me = document.currentScript as HTMLScriptElement;
  const device: any = {
    ip,
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

const ipRequest: Promise<string>;
function getIP() {
    if (!ipRequest) {
        ipRequest = new Promise<string>((resolve, reject) => {
            const req = new XMLHttpRequest();
            req.open("GET", "https://api.ipify.org/?format=json");
            req.onreadystatechange = () => {
                if (req.readyState !== 4) return;
                try {
                    const response = JSON.parse(req.responseText);
                    localStorage.setItem("ip", response.ip);

                    if (response.ip) {
                        resolve(response.ip);
                    } else {
                        reject();
                    }
                } catch (err) {
                    console.log(err);
                    reject();
                }
            };
            req.send();
        });
    }

    return ipRequest;
}

async function makeRequest({name, args}: {name: string, args: any}) {
  const deviceData = await device();
  return new Promise<any>((resolve, reject) => {
    const req = new XMLHttpRequest();
    req.open("POST", "https://" + baseUrl + "/" + name);
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
        localStorage.setItem("deviceId", response.deviceId);
        if (response.ok) {
          resolve(response.result);
        } else {
          reject(response.error);
        }
      } catch (e) {
        console.error(e);
        reject({type: "Fatal", message: e.toString()});
      }
    };
    req.send(JSON.stringify(body));
  });
}

END
  end

  def native_type(t : AST::OptionalType)
    native_type(t.base) + " | null | undefined"
  end
end

Target.register(TypeScriptWebTarget, target_name: "typescript_web")
