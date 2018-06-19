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
      if @op_names.includes? op.name
        raise SemanticException.new("Function '#{op.name}' is declared multiples times")
      end
      @op_names << op.name
      super
    end
  end
end
