require "./ast"

class StringPrimitiveType
  def to_s
    "string"
  end
end

class IntPrimitiveType
  def to_s
    "int"
  end
end

class UIntPrimitiveType
  def to_s
    "uint"
  end
end

class RealPrimitiveType
  def to_s
    "real"
  end
end

class DatePrimitiveType
  def to_s
    "date"
  end
end

class BoolPrimitiveType
  def to_s
    "bool"
  end
end

class BytesPrimitiveType
  def to_s
    "bytes"
  end
end

class VoidPrimitiveType
  def to_s
    "void"
  end
end

class OptionalType
  def to_s
    "#{@base.to_s}?"
  end
end

class ApiDescription
  def to_s
    custom_types.map(&.to_s).join("\n")
  end
end

class Field
  def to_s
    "#{name}: #{type.to_s}#{marks.map{|m| " !#{m}" }.join}"
  end
end

class CustomType
  def to_s
    "type #{name} {\n" +
    fields.map{|f| "  #{f.to_s}\n" }.join +
    "}\n"
  end
end

class CustomTypeReference < Type
  def to_s
    name
  end
end


