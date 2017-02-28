require "./target_typescript"

class TypeScriptWebTarget < TypeScriptTarget
  def gen
    @io << <<-END
import moment from "moment";
import {UAParser} from "ua-parser-js";

const baseUrl = #{@ast.options.url.inspect};


END

    @ast.custom_types.each do |custom_type|
      generate_custom_type_interface(@io, custom_type)
      @io << "\n"
    end

    @ast.operations.each do |op|
      @io << "export async function #{operation_name op}#{operation_args(op)} {\n"
      @io << "  const args: any = {};\n"
      op.args.each do |arg|
        @io << ident type_to_json(arg.type, "args.#{arg.name}", arg.name)
        @io << "\n"
      end
      @io << "  const retJson = await makeRequest({name: #{operation_name(op).inspect}, args});\n"
      @io << "  let ret: #{operation_ret op};\n"
      @io << ident type_from_json(op.return_type, "ret", "retJson")
      @io << "\n"
      @io << "  return ret;\n"
      @io << "}\n\n"
    end

    @io << <<-END
//////////////////////////////////////////////////////

function device() {
  const parser = new UAParser();
  parser.setUA(navigator.userAgent);
  const agent = parser.getResult();
  const me = document.currentScript as HTMLScriptElement;
  const device: any = {
    platform: "web",
    platformVersion: `${agent.browser.name} ${agent.browser.version} on ${agent.os.name} ${agent.os.version}`,
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

async function makeRequest({name, args}: {name: string, args: any}) {
  return new Promise<any>((resolve, reject) => {
    const req = new XMLHttpRequest();
    req.open("POST", "https://" + baseUrl + "/" + name);
    const body = {
      id: randomBytesHex(16),
      device: device(),
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
        reject({type: "ServerError", message: e.toString()});
      }
    };
    req.send(JSON.stringify(body));
  });
}

END
  end
end

Target.register(TypeScriptWebTarget, language: "ts", is_server: false)
