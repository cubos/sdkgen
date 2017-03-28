require "./target_typescript"

class TypeScriptWebTarget < TypeScriptTarget
  def gen
    @io << <<-END
import * as moment from "moment";
import {UAParser} from "ua-parser-js";

const baseUrl = #{@ast.options.url.inspect};


END

    @ast.type_definitions.each do |type_definition|
      @io << generate_type_definition_type(type_definition)
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
