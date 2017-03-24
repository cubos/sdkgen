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

class FloatPrimitiveType
  def to_s
    "float"
  end
end

class DatePrimitiveType
  def to_s
    "date"
  end
end

class DateTimePrimitiveType
  def to_s
    "datetime"
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
    type_definitions.map(&.to_s).join("\n") + "\n" +
    operations.map(&.to_s).join("\n")
  end
end

class Field
  def to_s
    str = "#{name}: #{type.to_s}"
    str += " !secret" if secret
    str
  end
end

class TypeDefinition
  def to_s
    "type #{name} {\n" +
    fields.map{|f| "  #{f.to_s}\n" }.join +
    "}\n"
  end
end

class TypeReference
  def to_s
    name
  end
end

class GetOperation
  def to_s
    "get #{name}(#{args.map(&.to_s).join(", ")}): #{return_type.to_s}"
  end
end

class FunctionOperation
  def to_s
    "function #{name}(#{args.map(&.to_s).join(", ")}): #{return_type.to_s}"
  end
end

class SubscribeOperation
  def to_s
    "subscribe #{name}(#{args.map(&.to_s).join(", ")}): #{return_type.to_s}"
  end
end
