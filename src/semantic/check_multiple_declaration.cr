require "./visitor"

module Semantic
  class CheckMultipleDeclaration < Visitor
    @names = Set(String).new
    @op_names = Set(String).new

    def visit(definition : AST::TypeDefinition)
      if @names.includes? definition.name
        raise SemanticException.new("Type '#{definition.name}' is defined multiple times")
      end
      @names << definition.name
      super
    end

    def visit(op : AST::Operation)
      if @op_names.includes? op.pretty_name
        raise SemanticException.new("Function '#{op.pretty_name}' is declared multiples times")
      end
      @op_names << op.pretty_name
      super
    end
  end
end
