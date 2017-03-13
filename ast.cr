
module AST
  abstract class Type
  end

  abstract class PrimitiveType < Type
    def check(ast)
    end
  end

  class StringPrimitiveType < PrimitiveType
  end

  class IntPrimitiveType < PrimitiveType
  end

  class UIntPrimitiveType < PrimitiveType
  end

  class FloatPrimitiveType < PrimitiveType
  end

  class DatePrimitiveType < PrimitiveType
  end

  class DateTimePrimitiveType < PrimitiveType
  end

  class BoolPrimitiveType < PrimitiveType
  end

  class BytesPrimitiveType < PrimitiveType
  end

  class VoidPrimitiveType < PrimitiveType
  end

  class OptionalType < Type
    property base
    def initialize(@base : Type)
    end

    def check(ast)
      @base.check(ast)
    end
  end

  class ArrayType < Type
    property base
    def initialize(@base : Type)
    end

    def check(ast)
      @base.check(ast)
    end
  end

  class ApiDescription
    property custom_types = [] of CustomType
    property operations = [] of Operation
    property options = Options.new
    property errors = [] of String

    def check
      custom_types.each &.check(self)
      operations.each &.check(self)
    end
  end

  class Options
    property url = ""
  end

  class Field
    property! name : String
    property! type : Type
    property secret = false

    def check(ast)
      type.check(ast)
    end
  end

  class CustomType
    property! name : String
    property fields = [] of Field

    def check(ast)
      fields.each &.check(ast)
    end
  end

  class CustomTypeReference < Type
    property name
    def initialize(@name : String)
    end

    def check(ast)
      unless ast.custom_types.find {|t| t.name == name }
        raise "Could not find type '#{name}'"
      end
    end
  end

  abstract class Operation
    property! name : String
    property args = [] of Field
    property! return_type : Type

    def check(ast)
      args.each &.check(ast)
    end

    def fnName
      name
    end
  end

  class GetOperation < Operation
    def fnName
      return_type.is_a?(BoolPrimitiveType) ? name : "get" + name[0].upcase + name[1..-1]
    end
  end

  class FunctionOperation < Operation
  end

  class SubscribeOperation < Operation
  end
end