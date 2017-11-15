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

/* istanbul ignore next */
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
  end
end

Target.register(TypeScriptServerTestTarget, target_name: "typescript_servertest")
