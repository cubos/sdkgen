require "./visitor"

module Semantic
  class CheckMultipleDeclaration < Visitor
    @names = Set(String).new

    def visit(definition : AST::TypeDefinition)
      if @names.includes? definition.name
        raise SemanticException.new("Type '#{definition.name}' is defined multiple times")
      end
      @names << definition.name
      super
    end
  end
end
