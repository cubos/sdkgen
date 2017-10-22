require "./target"

abstract class TypeScriptTarget < Target
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
