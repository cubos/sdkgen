require "./visitor"

module Semantic
  class CheckNoRecursiveTypes < Visitor
    @path = [] of String

    def visit(definition : AST::TypeDefinition)
      @path = [definition.name]
      super
    end

    def visit(field : AST::Field)
      @path.push field.name
      super
      @path.pop
    end

    def visit(ref : AST::TypeReference)
      if ref.name == @path[0]
        raise "Detected type recursion: #{@path.join(".")}"
      end
      visit ref.type
      super
    end
  end
end
