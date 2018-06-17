require "json"
require "./target"

class TypeScriptServerTarget < Target
  def gen
    if @ast.options.syntheticDefaultImports
      @io << <<-END
import http from "http";
import crypto from "crypto";
import os from "os";
import url from "url";
import Raven from "raven";

END
    else
      @io << <<-END
import * as http from "http";
import * as crypto from "crypto";
import * as os from "os";
import * as url from "url";
const Raven = require("raven");

END
    end

    unless @ast.options.useRethink
      @io << <<-END

interface DBDevice {
    id: string
    ip: string
    type: "android" | "ios" | "web"
    platform: any
    fingerprint: string
    screen: {width: number, height: number}
    version: string
    language: string
    lastActiveAt?: Date
    push?: string
}

interface DBApiCall {
    id: string
    name: string
    args: any
    executionId: string
    running: boolean
    device: DBDevice
    date: Date
    duration: number
    host: string
    ok: boolean
    result: any
    error: {type: string, message: string} | null
}

END
    end

    @io << <<-END

let captureError: (e: Error, req?: http.IncomingMessage, extra?: any) => void = () => {};
export function setCaptureErrorFn(fn: (e: Error, req?: http.IncomingMessage, extra?: any) => void) {
    captureError = fn;
}

let sentryUrl: string | null = null
export function setSentryUrl(url: string) {
    sentryUrl = url;
}

function typeCheckerError(e: Error, ctx: Context) {
    #{@ast.options.strict ? "throw e;" : "setTimeout(() => captureError(e, ctx.req, ctx.call), 1000);"}
}

function padNumber(value: number, length: number) {
    return value.toString().padStart(length, "0");
}

function toDateTimeString(date: Date) {
    return `${
        padNumber(date.getFullYear(), 4)
    }-${
        padNumber(date.getMonth() + 1, 2)
    }-${
        padNumber(date.getDate(), 2)
    } ${
        padNumber(date.getHours(), 2)
    }:${
        padNumber(date.getMinutes(), 2)
    }:${
        padNumber(date.getSeconds(), 2)
    }`;
}

END

    @io << "export const fn: {\n"
    @ast.operations.each do |op|
      args = ["ctx: Context"] + op.args.map { |arg| "#{arg.name}: #{arg.type.typescript_native_type}" }
      @io << "    " << op.pretty_name << ": (#{args.join(", ")}) => Promise<#{op.return_type.typescript_native_type}>;\n"
    end
    @io << "} = {\n"
    @ast.operations.each do |op|
      @io << "    " << op.pretty_name << ": () => { throw \"not implemented\"; },\n"
    end
    @io << "};\n\n"

    @ast.struct_types.each do |t|
      @io << t.typescript_definition
      @io << "\n\n"
    end

    @ast.enum_types.each do |t|
      @io << t.typescript_definition
      @io << "\n\n"
    end

    @io << "const fnExec: {[name: string]: (ctx: Context, args: any) => Promise<any>} = {\n"
    @ast.operations.each do |op|
      @io << "    " << op.pretty_name << ": async (ctx: Context, args: any) => {\n"
      op.args.each do |arg|
        @io << ident ident arg.type.typescript_check_encoded("args.#{arg.name}", "\"#{op.pretty_name}.args.#{arg.name}\"")
        @io << ident ident "const #{arg.name} = #{arg.type.typescript_decode("args.#{arg.name}")};"
        @io << "\n"
      end
      @io << ident ident "const ret = await fn.#{op.pretty_name}(#{(["ctx"] + op.args.map(&.name)).join(", ")});\n"
      @io << ident ident op.return_type.typescript_check_decoded("ret", "\"#{op.pretty_name}.ret\"")
      @io << ident ident "return " + op.return_type.typescript_encode("ret") + ";\n"
      @io << ident "},\n"
    end
    @io << "};\n\n"

    @io << "const clearForLogging: {[name: string]: (call: DBApiCall) => void} = {\n"
    @ast.operations.each do |op|
      cmds_args = String.build { |io| emit_clear_for_logging(io, op, "call.args") }

      if cmds_args != ""
        @io << "    " << op.pretty_name << ": async (call: DBApiCall) => {\n"
        @io << ident ident cmds_args
        @io << ident "},\n"
      end
    end
    @io << "};\n\n"

    @ast.struct_types.each do |t|
      @io << "export function transform#{t.name}ToJson(x: #{t.typescript_native_type}) {\n"
      @io << ident "return " + t.typescript_encode("x") + ";\n"
      @io << "}\n\n"
    end

    @ast.struct_types.each do |t|
      @io << "export function transformJsonTo#{t.name}(x: string) {\n"
      @io << ident "const y = JSON.parse(x);\n"
      @io << ident "return " + t.typescript_decode("y") + ";\n"
      @io << "}\n\n"
    end

    @ast.errors.each do |error|
      @io << "export class #{error} extends Error {\n"
      @io << ident "_type = #{error.inspect};\n"
      @io << ident "constructor(public _msg: string) {\n"
      @io << ident ident "super(_msg ? \"#{error}: \" + _msg : #{error.inspect});\n"
      @io << ident "}\n"
      @io << "}\n\n"
    end

    @io << "export const err = {\n"
    @ast.errors.each do |error|
      @io << ident "#{error}: (message: string = \"\") => { throw new #{error}(message); },\n"
    end
    @io << "};\n\n"

    @io << <<-END
//////////////////////////////////////////////////////

const httpHandlers: {
    [signature: string]: (body: string, res: http.ServerResponse, req: http.IncomingMessage) => void
} = {}

export function handleHttp(method: "GET" | "POST" | "PUT" | "DELETE", path: string, func: (body: string, res: http.ServerResponse, req: http.IncomingMessage) => void) {
    httpHandlers[method + path] = func;
}

export function handleHttpPrefix(method: "GET" | "POST" | "PUT" | "DELETE", path: string, func: (body: string, res: http.ServerResponse, req: http.IncomingMessage) => void) {
    httpHandlers["prefix " + method + path] = func;
}

export interface Context {
    call: DBApiCall;
    device: DBDevice;
    req: http.IncomingMessage;
    startTime: Date;
    staging: boolean;
}

function sleep(ms: number) {
    return new Promise<void>(resolve => setTimeout(resolve, ms));
}

export let server: http.Server;

export const hook: {
    onHealthCheck: () => Promise<boolean>
    onDevice: (id: string, deviceInfo: any) => Promise<void>
    onReceiveCall: (call: DBApiCall) => Promise<DBApiCall | void>
    afterProcessCall: (call: DBApiCall) => Promise<void>
} = {
    onHealthCheck: async () => true,
    onDevice: async () => {},
    onReceiveCall: async () => {},
    afterProcessCall: async () => {}
};

export function start(port: number = 8000) {
    if (server) return;
    server = http.createServer((req, res) => {
        req.on("error", (err) => {
            console.error(err);
        });

        res.on("error", (err) => {
            console.error(err);
        });

        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setHeader("Access-Control-Max-Age", "86400");
        res.setHeader("Content-Type", "application/json");

        let body = "";
        req.on("data", (chunk: any) => body += chunk.toString());
        req.on("end", () => {
            if (req.method === "OPTIONS") {
                res.writeHead(200);
                res.end();
                return;
            }
            const ip = req.headers["x-real-ip"] as string || "";
            const signature = req.method! + url.parse(req.url || "").pathname;
            if (httpHandlers[signature]) {
                console.log(`${toDateTimeString(new Date())} http ${signature}`);
                httpHandlers[signature](body, res, req);
                return;
            }
            for (let target in httpHandlers) {
                if (("prefix " + signature).startsWith(target)) {
                    console.log(`${toDateTimeString(new Date())} http ${target}`);
                    httpHandlers[target](body, res, req);
                    return;
                }
            }

            switch (req.method) {
                case "HEAD": {
                    res.writeHead(200);
                    res.end();
                    break;
                }
                case "GET": {
                    hook.onHealthCheck().then(ok => {
                        res.writeHead(ok ? 200 : 500);
                        res.write(JSON.stringify({ok}));
                        res.end();
                    }, error => {
                        console.error(error);
                        res.writeHead(500);
                        res.write(JSON.stringify({ok: false}));
                        res.end();
                    });
                    break;
                }
                case "POST": {
                    (async () => {
                        const request = JSON.parse(body);
                        request.device.ip = ip;
                        request.device.lastActiveAt = new Date();
                        const context: Context = {
                            call: null as any,
                            req: req,
                            device: request.device,
                            startTime: new Date,
                            staging: request.staging || false
                        };
                        const startTime = process.hrtime();

                        const {id, ...deviceInfo} = context.device;

                        if (!context.device.id)
                            context.device.id = crypto.randomBytes(20).toString("hex");

                        await hook.onDevice(context.device.id, deviceInfo);

                        const executionId = crypto.randomBytes(20).toString("hex");

                        let call: DBApiCall = {
                            id: `${request.id}-${context.device.id}`,
                            name: request.name,
                            args: JSON.parse(JSON.stringify(request.args)),
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

                        context.call = call;

                        if (clearForLogging[call.name])
                            clearForLogging[call.name](call);

                        try {
                            call = await hook.onReceiveCall(call) || call;
                        } catch (e) {
                            call.ok = false;
                            call.error = {
                                type: "Fatal",
                                message: e.toString()
                            };
                            call.running = false;
                        }

                        if (call.running) {
                            try {
                                const func = fnExec[request.name];
                                if (func) {
                                    call.result = await func(context, request.args);
                                } else {
                                    console.error(JSON.stringify(Object.keys(fnExec)));
                                    throw "Function does not exist: " + request.name;
                                }
                            } catch (err) {
                                console.error(err);
                                call.ok = false;
                                if (#{@ast.errors.to_json}.includes(err._type)) {
                                    call.error = {
                                        type: err._type,
                                        message: err._msg
                                    };
                                } else {
                                    call.error = {
                                        type: "Fatal",
                                        message: err.toString()
                                    };
                                    setTimeout(() => captureError(err, req, {
                                        call
                                    }), 1);
                                }
                            }
                            call.running = false;
                            const deltaTime = process.hrtime(startTime);
                            call.duration = deltaTime[0] + deltaTime[1] * 1e-9;

                            await hook.afterProcessCall(call);
                        }

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

                        // res.writeHead(!response.error ? 200 : response.error.type === "Fatal" ? 500 : 400);
                        res.writeHead(200);
                        res.write(JSON.stringify(response));
                        res.end();

                        console.log(
                            `${toDateTimeString(new Date())} ` +
                            `${call.id} [${call.duration.toFixed(6)}s] ` +
                            `${call.name}() -> ${call.ok ? "OK" : call.error ? call.error.type : "???"}`
                        );
                    })().catch(err => {
                        console.error(err);
                        if (!res.headersSent)
                            res.writeHead(500);
                        res.end();
                    });
                    break;
                }
                default: {
                    res.writeHead(500);
                    res.end();
                }
            }
        });
    });

    if ((server as any).keepAliveTimeout)
        (server as any).keepAliveTimeout = 0;

    if (!process.env.TEST && !process.env.DEBUGGING && sentryUrl) {
        Raven.config(sentryUrl).install();
        captureError = (e, req, extra) => Raven.captureException(e, {
            req,
            extra,
            fingerprint: [(e.message || e.toString()).replace(/[0-9]+/g, "X").replace(/"[^"]*"/g, "X")]
        });
    }

    if (process.env.DEBUGGING) {
        port = (Math.random() * 50000 + 10000) | 0;
    }

    if (!process.env.TEST) {
        server.listen(port, () => {
            const addr = server.address();
            const addrString = typeof addr === "string" ? addr : `${addr.address}:${addr.port}`;
            console.log(`Listening on ${addrString}`);
        });
    }

    if (process.env.DEBUGGING) {
        const subdomain = require("crypto").createHash("md5").update(process.argv[1]).digest("hex").substr(0, 8);
        require("localtunnel")(port, {subdomain}, (err: Error | null, tunnel: any) => {
            if (err) throw err;
            console.log("Tunnel URL:", tunnel.url);
        });
    }
}

fn.ping = async (ctx: Context) => "pong";
END

    if @ast.options.useRethink
      @io << <<-END
fn.setPushToken = async (ctx: Context, token: string) => {
    await r.table("devices").get(ctx.device.id).update({push: token});
};
END
    end

    if @ast.options.useRethink
      @io << <<-END


import r from "../rethinkdb";

hook.onHealthCheck = async () => {
    return await r.expr(true);
};

hook.onDevice = async (id, deviceInfo) => {
    if (await r.table("devices").get(id).eq(null)) {
        await r.table("devices").insert({
            id: id,
            date: r.now(),
            ...deviceInfo
        });
    } else {
        r.table("devices").get(id).update(deviceInfo).run();
    }
};

hook.onReceiveCall = async (call) => {
    for (let i = 0; i < 1500; ++i) {
        const priorCall = await r.table("api_calls").get(call.id);
        if (priorCall === null) {
            const res = await r.table("api_calls").insert(call);
            if (res.inserted > 0)
                return;
            else
                continue;
        }
        if (!priorCall.running) {
            return priorCall;
        }
        if (priorCall.executionId === call.executionId) {
            return;
        }

        await sleep(100);
    }

    throw "CallExecutionTimeout: Timeout while waiting for execution somewhere else (is the original container that received this request dead?)";
};

hook.afterProcessCall = async (call) => {
    r.table("api_calls").get(call.id).update(call).run();
};

END
    end

  end

  @i = 0

  def emit_clear_for_logging(io : IO, t : AST::Type | AST::Operation | AST::Field, path : String)
    case t
    when AST::Operation
      t.args.each do |field|
        emit_clear_for_logging(io, field, "#{path}.#{field.name}")
      end
    when AST::StructType
      t.fields.each do |field|
        emit_clear_for_logging(io, field, "#{path}.#{field.name}")
      end
    when AST::Field
      if t.secret
        io << "#{path} = \"<secret>\";\n"
      else
        emit_clear_for_logging(io, t.type, path)
      end
    when AST::TypeReference
      emit_clear_for_logging(io, t.type, path)
    when AST::OptionalType
      cmd = String.build { |io| emit_clear_for_logging(io, t.base, path) }
      if cmd != ""
        io << "if (#{path}) {\n" << ident(cmd) << "}\n"
      end
    when AST::ArrayType
      var = ('i' + @i).to_s
      @i += 1
      cmd = String.build { |io| emit_clear_for_logging(io, t.base, "#{path}[#{var}]") }
      @i -= 1
      if cmd != ""
        io << "for (let #{var} = 0; #{var} < #{path}.length; ++#{var}) {\n" << ident(cmd) << "}\n"
      end
    when AST::BytesPrimitiveType
      io << "#{path} = `<${#{path}.length} bytes>`;\n"
    end
  end
end

Target.register(TypeScriptServerTarget, target_name: "typescript_nodeserver")
