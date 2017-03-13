require "./target"

abstract class JavaTarget < Target
  def ident(code)
    super super code
  end

  def native_type_not_primitive(t : AST::PrimitiveType)
    case t
    when AST::StringPrimitiveType;   "String"
    when AST::IntPrimitiveType;      "Integer"
    when AST::UIntPrimitiveType;     "Integer"
    when AST::FloatPrimitiveType;    "Double"
    when AST::DatePrimitiveType;     "Date"
    when AST::DateTimePrimitiveType; "Date"
    when AST::BoolPrimitiveType;     "Boolean"
    when AST::BytesPrimitiveType;    "byte[]"
    when AST::VoidPrimitiveType;     "void"
    else
      raise "BUG! Should handle primitive #{t.class}"
    end
  end

  def native_type_not_primitive(t : AST::Type)
    native_type(t)
  end

  def native_type(t : AST::PrimitiveType)
    case t
    when AST::StringPrimitiveType;   "String"
    when AST::IntPrimitiveType;      "int"
    when AST::UIntPrimitiveType;     "int"
    when AST::FloatPrimitiveType;    "double"
    when AST::DatePrimitiveType;     "Date"
    when AST::DateTimePrimitiveType; "Date"
    when AST::BoolPrimitiveType;     "boolean"
    when AST::BytesPrimitiveType;    "byte[]"
    when AST::VoidPrimitiveType;     "void"
    else
      raise "BUG! Should handle primitive #{t.class}"
    end
  end

  def native_type(t : AST::OptionalType)
    native_type_not_primitive(t.base)
  end

  def native_type(t : AST::ArrayType)
    native_type(t.base) + "[]"
  end

  def native_type(t : AST::CustomTypeReference)
    t.name
  end

  def generate_custom_type_interface(custom_type)
    String.build do |io|
      io << "public static class #{custom_type.name} {\n"
      custom_type.fields.each do |field|
        io << ident "#{native_type field.type} #{field.name};\n"
      end
      io << "}"
    end
  end


  def operation_type(op : AST::Operation)
    "#{operation_args(op)} => Promise<#{operation_ret(op)}>"
  end

  def type_from_json(t : AST::Type, src : String)
    case t
    when AST::StringPrimitiveType, AST::IntPrimitiveType, AST::UIntPrimitiveType, AST::FloatPrimitiveType, AST::BoolPrimitiveType
      "#{src}"
    when AST::DatePrimitiveType
      "moment.utc(#{src}, \"YYYY-MM-DD\").toDate()"
    when AST::DateTimePrimitiveType
      "moment.utc(#{src}, \"YYYY-MM-DDTHH:mm:ss.SSS\").toDate()"
    when AST::BytesPrimitiveType
      "Buffer.from(#{src}, \"base64\")"
    when AST::VoidPrimitiveType
      "undefined"
    when AST::OptionalType
      "#{src} === null || #{src} === undefined ? null : #{type_from_json(t.base, src)}"
    when AST::ArrayType
      t.base.is_a?(AST::CustomTypeReference) ? "#{src}.map(e => (#{type_from_json(t.base, "e")}))" : "#{src}.map(e => #{type_from_json(t.base, "e")})"
    when AST::CustomTypeReference
      String::Builder.build do |io|
        io << "{\n"
        ct = @ast.custom_types.find {|x| x.name == t.name }.not_nil!
        ct.fields.each do |field|
          io << ident "#{field.name}: #{type_from_json(field.type, "#{src}.#{field.name}")},"
          io << "\n"
        end
        io << "}"
      end
    else
      raise "Unknown type"
    end
  end

  def type_to_json(t : AST::Type, src : String)
    case t
    when AST::StringPrimitiveType, AST::IntPrimitiveType, AST::UIntPrimitiveType, AST::FloatPrimitiveType, AST::BoolPrimitiveType
      "#{src}"
    when AST::DatePrimitiveType
      "moment(#{src}).format(\"YYYY-MM-DD\")"
    when AST::DateTimePrimitiveType
      "moment(#{src}).format(\"YYYY-MM-DDTHH:mm:ss.SSS\")"
    when AST::BytesPrimitiveType
      "#{src}.toString(\"base64\")"
    when AST::VoidPrimitiveType
      "null"
    when AST::OptionalType
      "#{src} === null || #{src} === undefined ? null : #{type_to_json(t.base, src)}"
    when AST::ArrayType
      t.base.is_a?(AST::CustomTypeReference) ? "#{src}.map(e => (#{type_to_json(t.base, "e")}))" : "#{src}.map(e => #{type_to_json(t.base, "e")})"
    when AST::CustomTypeReference
      String::Builder.build do |io|
        io << "{\n"
        ct = @ast.custom_types.find {|x| x.name == t.name }.not_nil!
        ct.fields.each do |field|
          io << ident "#{field.name}: #{type_to_json(field.type, "#{src}.#{field.name}")},"
          io << "\n"
        end
        io << "}"
      end
    else
      raise "Unknown type"
    end
  end
end
