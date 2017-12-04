require "../ast"

module AST
  class StringPrimitiveType
    def to_s(io)
      io << "string"
    end
  end

  class IntPrimitiveType
    def to_s(io)
      io << "int"
    end
  end

  class UIntPrimitiveType
    def to_s(io)
      io << "uint"
    end
  end

  class FloatPrimitiveType
    def to_s(io)
      io << "float"
    end
  end

  class DatePrimitiveType
    def to_s(io)
      io << "date"
    end
  end

  class DateTimePrimitiveType
    def to_s(io)
      io << "datetime"
    end
  end

  class BoolPrimitiveType
    def to_s(io)
      io << "bool"
    end
  end

  class BytesPrimitiveType
    def to_s(io)
      io << "bytes"
    end
  end

  class VoidPrimitiveType
    def to_s(io)
      io << "void"
    end
  end

  class MoneyPrimitiveType
    def to_s(io)
      io << "money"
    end
  end

  class CpfPrimitiveType
    def to_s(io)
      io << "cpf"
    end
  end

  class CnpjPrimitiveType
    def to_s(io)
      io << "cnpj"
    end
  end

  class EmailPrimitiveType
    def to_s(io)
      io << "email"
    end
  end

  class PhonePrimitiveType
    def to_s(io)
      io << "phone"
    end
  end

  class CepPrimitiveType
    def to_s(io)
      io << "cep"
    end
  end

  class LatLngPrimitiveType
    def to_s(io)
      io << "latlng"
    end
  end

  class UrlPrimitiveType
    def to_s(io)
      io << "url"
    end
  end

  class UuidPrimitiveType
    def to_s(io)
      io << "uuid"
    end
  end

  class HexPrimitiveType
    def to_s(io)
      io << "hex"
    end
  end

  class Base64PrimitiveType
    def to_s(io)
      io << "base64"
    end
  end

  class SafeHtmlPrimitiveType
    def to_s(io)
      io << "safehtml"
    end
  end

  class XmlPrimitiveType
    def to_s(io)
      io << "xml"
    end
  end

  class OptionalType
    def to_s(io)
      @base.to_s(io)
      io << "?"
    end
  end

  class ArrayType
    def to_s(io)
      @base.to_s(io)
      io << "[]"
    end
  end

  class ApiDescription
    def to_s(io)
      anyopt = false
      if options.url != ""
        io << "$url = "
        options.url.inspect(io)
        io << "\n"
        anyop = true
      end
      if options.useRethink != true
        io << "$useRethink = "
        options.useRethink.inspect(io)
        io << "\n"
        anyop = true
      end
      if options.strict != false
        io << "$strict = "
        options.strict.inspect(io)
        io << "\n"
        anyop = true
      end
      io << "\n" if anyop && errors.size != 0
      errors.each do |err|
        io << "error " << err << "\n"
      end
      io << "\n" if errors.size != 0 && type_definitions.size != 0
      type_definitions.each_with_index do |tdef, i|
        tdef.to_s(io)
        io << "\n"
      end
      io << "\n" if type_definitions.size != 0 && operations.size != 0
      operations.each do |op|
        op.to_s(io)
        io << "\n"
      end
    end
  end

  class Field
    def to_s(io)
      io << name << ": "
      type.to_s(io)
      io << " !secret" if secret
    end
  end

  class TypeDefinition
    def to_s(io)
      io << "type " << name << " "
      type.to_s(io)
    end
  end

  class StructType
    def to_s(io)
      io << "{\n"
      spreads.each do |ref|
        io << "  ..." << ref.name << "\n"
      end
      fields.each do |field|
        io << "  "
        field.to_s(io)
        io << "\n"
      end
      io << "}"
    end
  end

  class TypeReference
    def to_s(io)
      io << name
    end
  end

  class GetOperation
    def to_s(io)
      io << "get " << name << "("
      args.each_with_index do |arg, i|
        arg.to_s(io)
        io << ", " if i != args.size - 1
      end
      io << ")"
      unless return_type.is_a? VoidPrimitiveType
        io << ": "
        return_type.to_s(io)
      end
    end
  end

  class FunctionOperation
    def to_s
      io << "function " << name << "("
      args.each_with_index do |arg, i|
        arg.to_s(io)
        io << ", " if i != args.size - 1
      end
      io << ")"
      unless return_type.is_a? VoidPrimitiveType
        io << ": "
        return_type.to_s(io)
      end
    end
  end
end
