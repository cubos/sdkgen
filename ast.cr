
class Type
end

class StringPrimitiveType < Type
end

class IntPrimitiveType < Type
end

class UIntPrimitiveType < Type
end

class FloatPrimitiveType < Type
end

class DatePrimitiveType < Type
end

class BoolPrimitiveType < Type
end

class BytesPrimitiveType < Type
end

class VoidPrimitiveType < Type
end

class OptionalType < Type
  property base
  def initialize(@base : Type)
  end
end

class ApiDescription
  property custom_types = [] of CustomType
  property operations = [] of Operation
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

class Operation
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
