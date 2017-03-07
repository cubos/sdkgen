require "./target_typescript"

class JavaAndroidTarget < TypeScriptTarget
  def gen
    @io << <<-END

public class API {
    private static final String baseUrl = #{@ast.options.url.inspect};


END

    # @ast.custom_types.each do |custom_type|
    #   generate_custom_type_interface(@io, custom_type)
    #   @io << "\n"
    # end

    # @ast.operations.each do |op|
    #   @io << "export async function #{operation_name op}#{operation_args(op)}: Promise<#{operation_ret(op)}> {\n"
    #   if op.args.size > 0
    #     @io << "  const args = {\n"
    #     op.args.each do |arg|
    #       @io << ident ident "#{arg.name}: #{type_to_json(arg.type, arg.name)},"
    #       @io << "\n"
    #     end
    #     @io << "  };\n"
    #   end

    #   @io << "  "
    #   @io << "const ret = " unless op.return_type.is_a? AST::VoidPrimitiveType
    #   @io << "await makeRequest({name: #{operation_name(op).inspect}, #{op.args.size > 0 ? "args" : "args: {}"}});\n"
    #   @io << ident "return " + type_from_json(op.return_type, "ret") + ";"
    #   @io << "\n"
    #   @io << "}\n\n"
    # end

    @io << <<-END
}
END
  end
end

Target.register(JavaAndroidTarget, language: "java", is_server: false)
