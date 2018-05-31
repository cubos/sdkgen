require "./target"

class TypeScriptNodeClient < Target
  def gen
    @io << <<-END
import * as https from "https";
import * as http from "http";
import { URL } from "url";

END

    @ast.struct_types.each do |t|
      @io << t.typescript_definition
      @io << "\n\n"
    end

    @ast.enum_types.each do |t|
      @io << t.typescript_definition
      @io << "\n\n"
    end

    @io << <<-END
export class ApiClient {
  deviceId: string | null = null;

  constructor(private baseUrl = #{("https://" + @ast.options.url).inspect}, private useStaging = false) {}


END

    @ast.operations.each do |op|
      args = op.args.map { |arg| "#{arg.name}: #{arg.type.typescript_native_type}" }
      @io << "  async #{op.pretty_name}(#{args.join(", ")}): Promise<#{op.return_type.typescript_native_type}> {\n"
      if op.args.size > 0
        @io << "    const args = {\n"
        op.args.each do |arg|
          @io << "      #{arg.name}: #{arg.type.typescript_encode(arg.name)},"
          @io << "\n"
        end
        @io << "    };\n"
      end

      @io << "    "
      @io << "const ret = " unless op.return_type.is_a? AST::VoidPrimitiveType
      @io << "await this.makeRequest({name: #{op.pretty_name.inspect}, #{op.args.size > 0 ? "args" : "args: {}"}});\n"
      @io << "    return " + op.return_type.typescript_decode("ret") + ";\n" unless op.return_type.is_a? AST::VoidPrimitiveType
      @io << "  }\n\n"
    end

    @io << <<-END
  private device() {
    const device: any = {
      type: "node",
    };
    if (this.deviceId)
      device.id = this.deviceId;
    return device;
  }

  private randomBytesHex(len: number) {
    let hex = "";
    for (let i = 0; i < 2 * len; ++i)
      hex += "0123456789abcdef"[Math.floor(Math.random() * 16)];
    return hex;
  }

  private async makeRequest({name, args}: {name: string, args: any}) {
    const deviceData = this.device();
    const body = {
      id: this.randomBytesHex(8),
      device: deviceData,
      name: name,
      args: args
    };

    const url = new URL(this.baseUrl + (this.useStaging ? "-staging" : "") + "/" + name);
    const options = {
      hostname: url.hostname,
      path: url.pathname,
      method: "POST",
      port: url.port
    };

    return new Promise<any>((resolve, reject) => {
      const request = (url.protocol === "http:" ? http.request : https.request)
      const req = request(options, resp => {
        let data = "";
        resp.on("data", (chunk) => {
          data += chunk;
        });
        resp.on("end", () => {
          try {
            const response = JSON.parse(data);

            try {
              this.deviceId = response.deviceId;
              if (response.ok) {
                resolve(response.result);
              } else {
                reject(response.error);
              }
            } catch (e) {
              console.error(e);
              reject({type: "Fatal", message: e.toString()});
            }
          } catch (e) {
            console.error(e);
            reject({type: "BadFormattedResponse", message: `Response couldn't be parsed as JSON (${data}):\\n${e.toString()}`});
          }
        });

      });

      req.on("error", (e) => {
        console.error(`problem with request: ${e.message}`);
        reject({type: "Fatal", message: e.toString()});
      });

      // write data to request body
      req.write(JSON.stringify(body));
      req.end();
    });
  }
}

END
  end
end

Target.register(TypeScriptNodeClient, target_name: "typescript_nodeclient")
