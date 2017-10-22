require "./visitor"

module Semantic
  class CheckDontReturnSecret < Visitor
    @inreturn = false
    @path = [] of String

    def visit(op : AST::Operation)
      @inreturn = true
      @path.push op.name + "()"
      visit op.return_type
      @path.pop
      @inreturn = false
    end

    def visit(ref : AST::TypeReference)
      visit ref.type
    end

    def visit(field : AST::Field)
      @path.push field.name
      if @inreturn && field.secret
        raise "Can't return a secret value at #{@path.join(".")}"
      end
      super
      @path.pop
    end
  end
end
