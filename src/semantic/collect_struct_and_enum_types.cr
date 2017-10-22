require "./visitor"

module Semantic
  class CollectStructAndEnumTypes < Visitor
    def visit(t : AST::StructType)
      @ast.struct_types << t
      super
    end

    def visit(t : AST::EnumType)
      @ast.enum_types << t
      super
    end
  end
end
