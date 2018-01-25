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

  class MoneyPrimitiveType < PrimitiveType
  end

  class CpfPrimitiveType < PrimitiveType
  end

  class CnpjPrimitiveType < PrimitiveType
  end

  class EmailPrimitiveType < PrimitiveType
  end

  class PhonePrimitiveType < PrimitiveType
  end

  class CepPrimitiveType < PrimitiveType
  end

  class LatLngPrimitiveType < PrimitiveType
  end

  class UrlPrimitiveType < PrimitiveType
  end

  class UuidPrimitiveType < PrimitiveType
  end

  class HexPrimitiveType < PrimitiveType
  end

  class Base64PrimitiveType < PrimitiveType
  end

  class SafeHtmlPrimitiveType < PrimitiveType
  end

  class XmlPrimitiveType < PrimitiveType
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
    property spreads = [] of TypeReference
  end

  class TypeDefinition
    property! name : String
    property! type : Type
  end

  class TypeReference < Type
    property name

    def initialize(@name : String)
    end
  end

  class ApiDescription
    property type_definitions = [] of TypeDefinition
    property operations = [] of Operation
    property options = Options.new
    property errors = [] of String
  end

  class Options
    property url = ""
    property useRethink = true
    property strict = false
    property syntheticDefaultImports = true
  end

  class Field
    property! name : String
    property! type : Type
    property secret = false

    def check(ast)
      type.check(ast)
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
end
