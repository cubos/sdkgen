
module Semantic
  class Visitor
    def initialize(@ast : AST::ApiDescription)
    end

    def visit(node : AST::ApiDescription)
      node.type_definitions.each {|e| visit e }
      node.operations.each {|e| visit e }
    end

    def visit(op : AST::Operation)
      op.args.each {|arg| visit arg }
      visit op.return_type
    end

    def visit(field : AST::Field)
      visit field.type
    end

    def visit(definition : AST::TypeDefinition)
      visit definition.type
    end

    def visit(t : AST::StructType)
      t.fields.each {|field| visit field }
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

  class CheckEveryTypeDefined < Visitor
    def visit(ref : AST::TypeReference)
      definition = @ast.type_definitions.find {|t| t.name == ref.name }
      unless definition
        raise "Could not find type '#{ref.name}'"
      end
      ref.type = definition.type
      super
    end
  end

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
        @path.push(@path[0])
        raise "Detected recursive type #{@path.join(" -> ")}"
      end
      visit ref.type
      super
    end
  end

  class CheckDefineOnlyStructOrEnum < Visitor
    def visit(definition : AST::TypeDefinition)
      t = definition.type
      unless t.is_a? AST::StructType || t.is_a? AST::EnumType
        raise "Can't define 'definition.name' to be something other than a struct or an enum"
      end
      super
    end
  end

  class GiveStructAndEnumTypeNames < Visitor
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

    def visit(t : AST::StructType)
      t.name = @path.join("_")
      super
    end
  end

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

module AST
  class ApiDescription
    property struct_types = [] of AST::StructType
    property enum_types = [] of AST::EnumType

    def semantic
      Semantic::CheckEveryTypeDefined.new(self).visit(self)
      Semantic::CheckNoRecursiveTypes.new(self).visit(self)
      Semantic::CheckDefineOnlyStructOrEnum.new(self).visit(self)
      Semantic::GiveStructAndEnumTypeNames.new(self).visit(self)
      Semantic::CollectStructAndEnumTypes.new(self).visit(self)
    end
  end

  class TypeReference
    property! type : Type
  end

  class StructType
    property! name : String
  end

  class EnumType
    property! name : String
  end

  abstract class Operation
    def pretty_name
      name
    end
  end

  class GetOperation < Operation
    def pretty_name
      return_type.is_a?(BoolPrimitiveType) ? name : "get" + name[0].upcase + name[1..-1]
    end
  end
end
