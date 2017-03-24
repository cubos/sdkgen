
module AST
  abstract class PrimitiveType < Type
    def semantic(ast)
    end

    def check_recursive_type(ast, stack)
    end
  end

  class OptionalType < Type
    def semantic(ast)
      @base.semantic(ast)
    end

    def check_recursive_type(ast, stack)
      @base.check_recursive_type(ast, stack)
    end
  end

  class ArrayType < Type
    def semantic(ast)
      @base.semantic(ast)
    end

    def check_recursive_type(ast, stack)
      @base.check_recursive_type(ast, stack)
    end
  end

  class ApiDescription
    def semantic
      type_definitions.each &.semantic(self)
      operations.each &.semantic(self)
    end
  end

  class Field
    def semantic(ast)
      type.semantic(ast)
    end
  end

  class TypeDefinition
    def semantic(ast)
      fields.each &.semantic(ast)
      check_recursive_type(ast, [] of TypeDefinition)
    end

    def check_recursive_type(ast, stack)
      raise "Cannot allow recursive type #{stack.map(&.name).join(" -> ")} -> #{name}" if stack.find &.== self
      stack << self
      fields.each {|field| field.type.check_recursive_type(ast, stack) }
      stack.pop
    end
  end

  class TypeReference < Type
    def semantic(ast)
      unless ast.type_definitions.find {|t| t.name == name } || ast.enum_definitions.find {|t| t.name == name }
        raise "Could not find type '#{name}'"
      end
    end

    def check_recursive_type(ast, stack)
      ref = ast.type_definitions.find {|t| t.name == name }
      ref.check_recursive_type(ast, stack) if ref
    end
  end

  abstract class Operation
    def semantic(ast)
      args.each &.semantic(ast)
    end

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