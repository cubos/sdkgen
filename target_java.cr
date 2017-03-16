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
    when AST::DatePrimitiveType;     "Calendar"
    when AST::DateTimePrimitiveType; "Calendar"
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
    "ArrayList<#{native_type_not_primitive(t.base)}>"
  end

  def native_type(t : AST::CustomTypeReference)
    t.name
  end

  def generate_custom_type_interface(custom_type)
    String.build do |io|
      io << "public static class #{custom_type.name} {\n"
      custom_type.fields.each do |field|
        io << ident "public #{native_type field.type} #{field.name};\n"
      end
      io << ident <<-END

public JSONObject toJSON() {
    try {
        return new JSONObject() {{

END
      custom_type.fields.each do |field|
        io << ident ident ident ident "put(\"#{field.name}\", #{type_to_json field.type, field.name});\n"
      end
      io << ident <<-END
        }};
    } catch (JSONException e) {
        e.printStackTrace();
        return new JSONObject();
    }
}

public static #{custom_type.name} fromJSON(final JSONObject json) {
    try {
        return new #{custom_type.name}() {{

END
      custom_type.fields.each do |field|
        io << ident ident ident ident "#{field.name} = #{type_from_json field.type, get_field_from_json_object(field.type, "json", field.name.inspect)};\n"
      end
      io << ident <<-END

        }};
    } catch (JSONException e) {
        e.printStackTrace();
        return new #{custom_type.name}();
    }
}

END
      io << "}"
    end
  end

  def get_field_from_json_object(t : AST::Type, src : String, name : String)
    case t
    when AST::StringPrimitiveType, AST::DatePrimitiveType, AST::DateTimePrimitiveType, AST::BytesPrimitiveType
      "#{src}.getString(#{name})"
    when AST::IntPrimitiveType, AST::UIntPrimitiveType
      "#{src}.getInt(#{name})"
    when AST::FloatPrimitiveType
      "#{src}.getDouble(#{name})"
    when AST::BoolPrimitiveType
      "#{src}.getBoolean(#{name})"
    when AST::VoidPrimitiveType
      "#{src}.get(#{name})"
    when AST::OptionalType
      "#{src}.isNull(#{name}) ? null : #{get_field_from_json_object(t.base, src, name)}"
    when AST::ArrayType
      "#{src}.getJSONArray(#{name})"
    when AST::CustomTypeReference
      "#{src}.getJSONObject(#{name})"
    else
      raise "Unknown type"
    end
  end

  def type_from_json(t : AST::Type, src : String)
    case t
    when AST::StringPrimitiveType, AST::IntPrimitiveType, AST::UIntPrimitiveType, AST::FloatPrimitiveType, AST::BoolPrimitiveType
      "#{src}"
    when AST::DatePrimitiveType
      "Internal.decodeDate(#{src})"
    when AST::DateTimePrimitiveType
      "Internal.decodeDateTime(#{src})"
    when AST::BytesPrimitiveType
      "Base64.decode(#{src}, Base64.DEFAULT)"
    when AST::VoidPrimitiveType
      "null"
    when AST::OptionalType
      "#{src} == null ? null : #{type_from_json(t.base, src)}"
    when AST::ArrayType
      "new #{native_type t}() {{ JSONArray ary = #{src}; for (int i = 0; i < ary.length(); ++i) add(#{type_from_json(t.base, get_field_from_json_object(t.base, "ary", "i"))}); }}"
    when AST::CustomTypeReference
      ct = @ast.custom_types.find {|x| x.name == t.name }.not_nil!
      "#{ct.name}.fromJSON(#{src})"
    else
      raise "Unknown type"
    end
  end

  def type_to_json(t : AST::Type, src : String)
    case t
    when AST::StringPrimitiveType, AST::IntPrimitiveType, AST::UIntPrimitiveType, AST::FloatPrimitiveType, AST::BoolPrimitiveType
      "#{src}"
    when AST::DatePrimitiveType
      "Internal.encodeDate(#{src})"
    when AST::DateTimePrimitiveType
      "Internal.encodeDateTime(#{src})"
    when AST::BytesPrimitiveType
      "Base64.encodeToString(#{src}, Base64.DEFAULT)"
    when AST::VoidPrimitiveType
      "null"
    when AST::OptionalType
      "#{src} == null ? null : #{type_to_json(t.base, src)}"
    when AST::ArrayType
      "new JSONArray() {{ for (#{native_type t.base} el : #{src}) put(#{type_to_json t.base, "el"}); }}"
    when AST::CustomTypeReference
      "#{src}.toJSON()"
    else
      raise "Unknown type"
    end
  end
end
