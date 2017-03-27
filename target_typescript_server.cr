require "./target_typescript"

class TypeScriptServerTarget < TypeScriptTarget
  def gen
    @io << <<-END
import http from "http";
import crypto from "crypto";
import os from "os";
import moment from "moment";
import r from "../rethinkdb";


END

    @io << "export const fn: {\n"
    @ast.operations.each do |op|
      @io << "  " << op.pretty_name << ": " << operation_type(op) << ";\n"
    end
    @io << "} = {\n";
    @ast.operations.each do |op|
      @io << "  " << op.pretty_name << ": () => { throw \"not implemented\"; },\n"
    end
    @io << "};\n\n"

    @ast.type_definitions.each do |type_definition|
      @io << generate_type_definition_interface(type_definition)
      @io << "\n\n"
    end

    @io << "const fnExec: {[name: string]: (ctx: Context, args: any) => Promise<any>} = {\n"
    @ast.operations.each do |op|
      @io << "  " << op.pretty_name << ": async (ctx: Context, args: any) => {\n"
      op.args.each do |arg|
        @io << ident ident "const #{arg.name} = #{type_from_json(arg.type, "args.#{arg.name}")};"
        @io << "\n"
      end
      @io << "    const ret = await fn.#{op.pretty_name}(#{(["ctx"] + op.args.map(&.name)).join(", ")});\n"
      @io << ident ident "return " + type_to_json(op.return_type, "ret") + ";"
      @io << "\n"
      @io << "  },\n"
    end
    @io << "};\n\n"

    @io << "export const err = {\n"
    @ast.errors.each do |error|
      @io << "  #{error}: (message: string = \"\") => { throw {type: #{error.inspect}, message}; },\n"
    end
    @io << "};\n\n"

    @io << <<-END
//////////////////////////////////////////////////////

export interface Context {
  device: DBDevice;
  startTime: Date;
}

function sleep(ms: number) {
  return new Promise<void>(resolve => setTimeout(resolve, ms));
}

export function start(port: number) {
  const server = http.createServer((req, res) => {
    req.on("error", (err) => {
      console.error(err);
    });

    res.on("error", (err) => {
      console.error(err);
    });

    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    res.setHeader("Access-Control-Allow-Headers", "Content-Type");
    res.setHeader("Access-Control-Max-Age", "86400");

    switch (req.method) {
      case "GET": {
        res.writeHead(200);
        res.write(`{"ok": true}`);
        res.end();
        break;
      }
      case "POST": {
        let data = "";
        req.on("data", chunk => data += chunk.toString());
        req.on("end", () => {
          try {
            const request = JSON.parse(data);
            const context: Context = {
              device: request.device,
              startTime: new Date
            };
            const startTime = process.hrtime();

            (async () => {
              const {id, ...deviceInfo} = context.device;

              if (!context.device.id) {
                context.device.id = crypto.randomBytes(32).toString("hex");

                r.table("devices").insert({
                  id: context.device.id,
                  date: r.now(),
                  ...deviceInfo
                }).then();
              } else {
                r.table("devices").get(context.device.id).update(deviceInfo).then();
              }

              const executionId = crypto.randomBytes(32).toString("hex");

              let call: DBApiCall = {
                id: `${context.device.id}_${request.id}`,
                name: request.name,
                args: request.args,
                executionId: executionId,
                running: true,
                device: context.device,
                date: context.startTime,
                duration: 0,
                host: os.hostname(),
                ok: true,
                result: null as any,
                error: null as {type: string, message: string}|null
              };

              async function tryLock(): Promise<boolean> {
                const priorCall = await r.table("api_calls").get(call.id);
                if (priorCall === null) {
                  const res = await r.table("api_calls").insert(call);
                  return res.inserted > 0 ? true : await tryLock();
                }
                if (!priorCall.running) {
                  call = priorCall;
                  return true;
                }
                if (priorCall.executionId === executionId) {
                  return true;
                }
                return false;
              }

              for (let i = 0; i < 30; ++i) {
                if (tryLock()) break;
                await sleep(100);
              }

              if (call.running) {
                if (call.executionId !== executionId) {
                  call.ok = false;
                  call.error = {
                    type: "CallExecutionTimeout",
                    message: "Timeout while waiting for execution somewhere else"
                  };
                } else {
                  try {
                    call.result = await fnExec[call.name](context, call.args);
                  } catch (err) {
                    call.ok = false;
                    if (err.type) {
                      call.error = {
                        type: err.type,
                        message: err.message
                      };
                    } else {
                      call.error = {
                        type: "bug",
                        message: err.toString()
                      };
                    }
                  }
                }
              }

              const deltaTime = process.hrtime(startTime);
              call.duration = deltaTime[0] + deltaTime[1] * 1e-9;

              const response = {
                id: call.id,
                ok: call.ok,
                executed: call.executionId === executionId,
                deviceId: call.device.id,
                startTime: call.date,
                duration: call.duration,
                host: call.host,
                result: call.result,
                error: call.error
              };

              console.log({
                request,
                response
              });

              res.writeHead(200);
              res.write(JSON.stringify(response));
              res.end();

              await r.table("api_calls").get(call.id).update(call);
            })().catch(err => {
              console.error(err);
              res.writeHead(500);
              res.end();
            });
          } catch (err) {
            console.error(err);
            res.writeHead(400);
            res.end();
          }
        });
        break;
      }
      default: {
        res.writeHead(400);
        res.end();
      }
    }
  });

  server.listen(port, () => {
    console.log(`Listening on ${server.address().address}:${server.address().port}`);
  });
}


END
  end

  def operation_args(op : AST::Operation)
    args = ["ctx: Context"] + op.args.map {|arg| "#{arg.name}: #{native_type arg.type}" }
    if op.is_a? SubscribeOperation
      args << "callback: (result: #{native_type op.return_type}) => void"
    end

    "(#{args.join(", ")})"
  end
end

Target.register(TypeScriptServerTarget, language: "ts", is_server: true)
