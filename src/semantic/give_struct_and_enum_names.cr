require "./visitor"

module Semantic
  class GiveStructAndEnumNames < Visitor
    @path = [] of String
    @names = Hash(String, Array(String)).new

    def visit(definition : AST::TypeDefinition)
      @path = [definition.name]
      super
    end

    def visit(operation : AST::Operation)
      @path = [operation.name]
      super
    end

    def visit(field : AST::Field)
      @path.push field.name
      super
      @path.pop
    end

    def visit(t : AST::StructType | AST::EnumType)
      t.name = @path.map { |s| s[0].upcase + s[1..-1] }.join("")
      if @names.has_key? t.name
        raise SemanticException.new("The name of the type '#{@path.join(".")}' will conflict with '#{@names[t.name].join(".")}'")
      end
      @names[t.name] = @path.dup
      super
    end
  end
end
