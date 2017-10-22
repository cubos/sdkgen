require "./visitor"

module Semantic
  class CheckNoRecursiveTypes < Visitor
    @path = [] of String
    @root_type : AST::Type?

    def visit(definition : AST::TypeDefinition)
      @path = [definition.name]
      @root_type = definition.type
      super
      @root_type = nil
    end

    def visit(field : AST::Field)
      @path.push field.name
      super
      @path.pop
    end

    def visit(ref : AST::TypeReference)
      if ref.type == @root_type
        raise "Detected type recursion: #{@path.join(".")}"
      end
      visit ref.type
      super
    end
  end
end
