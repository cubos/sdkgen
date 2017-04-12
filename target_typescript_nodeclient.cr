require "./target_typescript"

class TypeScriptClientTarget < TypeScriptTarget
  def gen
    @io << <<-END
import * as moment from "moment";
import * as request from "request";
import * as fs from "fs";
import {version as nodeVersion} from "process";

const baseUrl = #{@ast.options.url.inspect};
let gDeviceId = null;
let gDevicePath = "/root/config/deviceId";

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

async function getDeviceId(){
  if(!gDeviceId){
    try{
      gDeviceId = await new Promise((resolve, reject) => fs.readFile(gDevicePath, "utf8",
            (err, data) => {
              if(err)
                reject(err);
              else
                resolve();
            })
      );
    } catch(e){
      return null;
    }
  }
  return gDeviceId;
}

async function setDeviceId(newDeviceId){
  if(newDeviceId !== gDeviceId){
    await new Promise((resolve, reject) => fs.writeFile(gDevicePath, newDeviceId, "utf8",
          (err) => {
            if(err)
              reject(err);
            else
              resolve();
          })
    );
    gDeviceId = newDeviceId;
  }
}

async function device() {
  const device: any = {
    type: "node",
    platform: {
      nodeVersion: nodeVersion,
    },
    screen: {
      width: 0,
      height: 0
    },
    version: "0.0.0",
    language: "en-US"
  };

  const deviceId = await getDeviceId();
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
  return new Promise<any>(async (resolve, reject) => {
    const body = {
      id: randomBytesHex(8),
      device: await device(),
      name: name,
      args: args
    };

    request.post(
      "https://" + baseUrl + "/" + name,
      {json: body},
      async (err, res, body) => {
        if(!err){
          try{
            if(body.ok){
              await setDeviceId(body.deviceId);
              resolve(body.result);
            } else {
              reject(body.error);
            }
          } catch(e){
            console.error(e);
            reject({type: "Fatal", message: e.toString()});
          }
        } else {
          console.error(err);
          reject({type: "Fatal", message: err.toString()});
        }
      }
    );
  });
}

END
  end
end

Target.register(TypeScriptClientTarget, target_name: "typescript_nodeclient")
