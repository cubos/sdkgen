
module AST
  abstract class Type
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

  class ApiDescription
    property custom_types = [] of CustomType
    property operations = [] of Operation
    property options = Options.new
    property errors = [] of String
  end

  class Options
    property url = ""
  end

  class Field
    property! name : String
    property! type : Type
    property secret = false
  end

  class CustomType
    property! name : String
    property fields = [] of Field
  end

  class CustomTypeReference < Type
    property name
    def initialize(@name : String)
    end
  end

  abstract class Operation
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