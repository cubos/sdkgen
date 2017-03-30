
module AST
  abstract class Type
    include AST
  end

  abstract class PrimitiveType < Type
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
  end

  class ArrayType < Type
    property base
    def initialize(@base : Type)
    end
  end

  class EnumType < Type
    property values = [] of String
  end

  class StructType < Type
    property fields = [] of Field
  end

  class TypeDefinition
    include AST
    property! name : String
    property! type : Type
  end

  class TypeReference < Type
    property name
    def initialize(@name : String)
    end
  end

  class ApiDescription
    include AST
    property type_definitions = [] of TypeDefinition
    property operations = [] of Operation
    property options = Options.new
    property errors = [] of String
  end

  class Options
    property url = ""
  end

  class Field
    include AST
    property! name : String
    property! type : Type
    property secret = false

    def check(ast)
      type.check(ast)
    end
  end

  abstract class Operation
    include AST
    property! name : String
    property args = [] of Field
    property! return_type : Type
  end

  class GetOperation < Operation
  end

  class FunctionOperation < Operation
  end

  class SubscribeOperation < Operation
  end
end