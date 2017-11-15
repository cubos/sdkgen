require "./target"

class TypeScriptServerTestTarget < Target
  def gen
    @io << <<-END
import http from "http";
import moment from "moment";

export interface Context {
    call: DBApiCall;
    device: DBDevice;
    req: http.IncomingMessage;
    startTime: Date;
    staging: boolean;
}

expect.extend({
	toBeTypeOf(received, argument) {
		const initialType = typeof received;
		const type = initialType === "object" ? Array.isArray(received) ? "array" : initialType : initialType;
		return type === argument ? {
			message: () => `expected ${received} to be type ${argument}`,
			pass: true
		} : {
			message: () => `expected ${received} to be type ${argument}`,
			pass: false
		};
	}
});

declare global {
    namespace jest {
        interface Matchers<R> {
            toBeTypeOf(type: string): void
        }
    }
}


END
@io << "type ApiFn = {\n"
@ast.operations.each do |op|
    args = ["ctx: Context"] + op.args.map { |arg| "#{arg.name}: #{arg.type.typescript_native_type}" }
    @io << "    " << op.pretty_name << ": (#{args.join(", ")}) => Promise<#{op.return_type.typescript_native_type}>;\n"
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

@io << <<-END
export function wrapApiFn(fn: ApiFn) {
    return {\n
END
@ast.operations.each do |op|
    args = ["ctx: Context"] + op.args.map { |arg| "#{arg.name}: #{arg.type.typescript_native_type}" }
    @io << "        " << op.pretty_name << ": async (#{args.join(", ")}) => {\n"
    op.args.each do |arg|
        @io << ident ident ident arg.type.typescript_expect(arg.name)
    end
    @io << "            "
    @io << "const ret = " unless op.return_type.is_a? AST::VoidPrimitiveType
    @io << "await fn.#{op.pretty_name}(#{(["ctx"] + op.args.map(&.name)).join(", ")});\n"
    unless op.return_type.is_a? AST::VoidPrimitiveType
        @io << ident ident ident op.return_type.typescript_expect("ret")
        @io << "            return ret;\n"
    end
    @io << "        },\n"
end
@io << <<-END
    };
}

END


    # @io << "const fnExec: {[name: string]: (ctx: Context, args: any) => Promise<any>} = {\n"
    # @ast.operations.each do |op|
    #   @io << "    " << op.pretty_name << ": async (ctx: Context, args: any) => {\n"
    #   op.args.each do |arg|
    #     @io << ident ident arg.type.typescript_check_encoded("args.#{arg.name}", "\"#{op.pretty_name}.args.#{arg.name}\"")
    #     @io << ident ident "const #{arg.name} = #{arg.type.typescript_decode("args.#{arg.name}")};"
    #     @io << "\n"
    #   end
    #   @io << ident ident "const ret = await fn.#{op.pretty_name}(#{(["ctx"] + op.args.map(&.name)).join(", ")});\n"
    #   @io << ident ident op.return_type.typescript_check_decoded("ret", "\"#{op.pretty_name}.ret\"")
    #   @io << ident ident "return " + op.return_type.typescript_encode("ret") + ";\n"
    #   @io << ident "},\n"
    # end
    # @io << "};\n\n"

    # @io << "const clearForLogging: {[name: string]: (call: DBApiCall) => void} = {\n"
    # @ast.operations.each do |op|
    #   cmds_args = String.build { |io| emit_clear_for_logging(io, op, "call.args") }

    #   if cmds_args != ""
    #     @io << "    " << op.pretty_name << ": async (call: DBApiCall) => {\n"
    #     @io << ident ident cmds_args
    #     @io << ident "},\n"
    #   end
    # end
    # @io << "};\n\n"

    # @ast.errors.each do |error|
    #   @io << "export class #{error} extends Error {\n"
    #   @io << ident "_type = #{error.inspect};\n"
    #   @io << ident "constructor(public _msg: string) {\n"
    #   @io << ident ident "super(_msg ? \"#{error}: \" + _msg : #{error.inspect});\n"
    #   @io << ident "}\n"
    #   @io << "}\n\n"
    # end

    # @io << "export const err = {\n"
    # @ast.errors.each do |error|
    #   @io << ident "#{error}: (message: string = \"\") => { throw new #{error}(message); },\n"
    # end
    # @io << "};\n\n"
  end
end

Target.register(TypeScriptServerTestTarget, target_name: "typescript_servertest")
