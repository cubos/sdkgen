require "./visitor"

module Semantic
  class CheckMultipleDeclaration < Visitor
    @names = Set(String).new

    def visit(definition : AST::TypeDefinition)
      if @names.includes? definition.name
        raise "Type '#{definition.name}' is already defined"
      end
      @names << definition.name
      super
    end
  end
end
