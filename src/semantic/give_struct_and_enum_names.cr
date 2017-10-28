require "./visitor"

module Semantic
  class GiveStructAndEnumNames < Visitor
    @path = [] of String

    def visit(definition : AST::TypeDefinition)
      @path = [definition.name]
      super
    end

    def visit(operation : AST::Operation)
      @path = [operation.name[0].upcase + operation.name[1..-1]]
      super
    end

    def visit(field : AST::Field)
      @path.push field.name[0].upcase + field.name[1..-1]
      super
      @path.pop
    end

    def visit(t : AST::StructType | AST::EnumType)
      t.name = @path.join("")
      super
    end
  end
end
