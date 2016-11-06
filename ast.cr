
class Type
end

class StringPrimitiveType < Type
end

class IntPrimitiveType < Type
end

class UIntPrimitiveType < Type
end

class RealPrimitiveType < Type
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
end

class Field
  property! name : String
  property! type : Type
  property marks = [] of String
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


