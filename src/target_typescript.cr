require "./target"

abstract class TypeScriptTarget < Target
  def generate_struct_type(t)
    String.build do |io|
      io << "export interface #{t.name} {\n"
      t.fields.each do |field|
        io << ident "#{field.name}: #{field.type.typescript_native_type};\n"
      end
      io << "}"
    end
  end

  def generate_enum_type(t)
    "export type #{t.name} = #{t.values.map(&.inspect).join(" | ")};"
  end

  def operation_ret(op : AST::GetOperation | AST::FunctionOperation)
    op.return_type.is_a?(AST::VoidPrimitiveType) ? "void" : op.return_type.typescript_native_type
  end

  def operation_args(op : AST::Operation)
    args = op.args.map {|arg| "#{arg.name}: #{arg.type.typescript_native_type}" }
    "(#{args.join(", ")})"
  end

  def operation_type(op : AST::Operation)
    "#{operation_args(op)} => Promise<#{operation_ret(op)}>"
  end
end
