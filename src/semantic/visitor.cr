require "../ast"

module Semantic
  class Visitor
    def self.visit(ast : AST::ApiDescription)
      new(ast).visit(ast)
    end

    def initialize(@ast : AST::ApiDescription)
    end

    def visit(node : AST::ApiDescription)
      node.type_definitions.each { |e| visit e }
      node.operations.each { |e| visit e }
    end

    def visit(op : AST::Operation)
      op.args.each { |arg| visit arg }
      visit op.return_type
    end

    def visit(field : AST::Field)
      visit field.type
    end

    def visit(definition : AST::TypeDefinition)
      visit definition.type
    end

    def visit(t : AST::StructType)
      t.fields.each { |field| visit field }
    end

    def visit(t : AST::ArrayType)
      visit t.base
    end

    def visit(t : AST::OptionalType)
      visit t.base
    end

    def visit(t : AST::PrimitiveType | AST::EnumType | AST::TypeReference)
    end
  end
end
