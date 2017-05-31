require "./target"

abstract class JavaTarget < Target
  def ident(code)
    super super code
  end

  def mangle(ident)
    if %w[
      boolean class if int byte do for while void float double long char synchronized
      instanceof extends implements interface abstract static public private protected
      final import package throw throws catch finally try new null else return continue
      break goto switch default case
    ].includes? ident
      "_" + ident
    else
      ident
    end
  end

  def native_type_not_primitive(t : AST::PrimitiveType)
    case t
    when AST::StringPrimitiveType;   "String"
    when AST::IntPrimitiveType;      "Integer"
    when AST::UIntPrimitiveType;     "Integer"
    when AST::FloatPrimitiveType;    "Double"
    when AST::DatePrimitiveType;     "Calendar"
    when AST::DateTimePrimitiveType; "Calendar"
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

  def native_type(t : AST::StructType | AST::EnumType)
    t.name
  end

  def native_type(ref : AST::TypeReference)
    native_type ref.type
  end

  def generate_struct_type(t)
    String.build do |io|
      io << "public static class #{mangle t.name} implements Parcelable, Comparable<#{t.name}> {\n"
      t.fields.each do |field|
        io << ident "public #{native_type field.type} #{mangle field.name};\n"
      end
      io << ident <<-END

public int	compareTo(#{t.name} other) {
    return toJSON().toString().compareTo(other.toJSON().toString());
}

public JSONObject toJSON() {
    try {
        return new JSONObject() {{

END
      t.fields.each do |field|
        io << ident ident ident ident "put(\"#{field.name}\", #{type_to_json field.type, field.name});\n"
      end
      io << ident <<-END
        }};
    } catch (JSONException e) {
        e.printStackTrace();
        return new JSONObject();
    }
}

public static #{t.name} fromJSON(final JSONObject json) {
    return new #{t.name}(json);
}

public #{t.name}() {
}

protected #{t.name}(final JSONObject json) {
    try {

END
      t.fields.each do |field|
        io << ident ident ident "#{field.name} = #{type_from_json field.type, "json", field.name.inspect};\n"
      end
      io << ident <<-END

    } catch (JSONException e) {
        e.printStackTrace();
    }
}

protected #{t.name}(Parcel in) {
    try {
        final JSONObject json = new JSONObject(in.readString());

END
      t.fields.each do |field|
        io << ident ident ident "#{field.name} = #{type_from_json field.type, "json", field.name.inspect};\n"
      end
      io << ident <<-END
    } catch (JSONException e) {
        e.printStackTrace();
    }
}

@Override
public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(toJSON().toString());
}

@Override
public int describeContents() {
    return 0;
}

public static final Parcelable.Creator<#{mangle t.name}> CREATOR = new Parcelable.Creator<#{mangle t.name}>() {
    @Override
    public #{mangle t.name} createFromParcel(Parcel in) {
        return new #{mangle t.name}(in);
    }

    @Override
    public #{mangle t.name}[] newArray(int size) {
        return new #{mangle t.name}[size];
    }
};

END
      io << "}"
    end
  end

  def generate_enum_type(t)
    String.build do |io|
      io << "public enum #{mangle t.name} {\n"
      t.values.each do |value|
        io << ident "#{mangle value},\n"
      end
      io << "}"
    end
  end

  def type_from_json(t : AST::Type, obj : String, name : String)
    case t
    when AST::StringPrimitiveType
      "#{obj}.getString(#{name})"
    when AST::IntPrimitiveType, AST::UIntPrimitiveType
      "#{obj}.getInt(#{name})"
    when AST::FloatPrimitiveType
      "#{obj}.getDouble(#{name})"
    when AST::BoolPrimitiveType
      "#{obj}.getBoolean(#{name})"
    when AST::DatePrimitiveType
      "Internal.decodeDate(#{obj}.getString(#{name}))"
    when AST::DateTimePrimitiveType
      "Internal.decodeDateTime(#{obj}.getString(#{name}))"
    when AST::BytesPrimitiveType
      "Base64.decode(#{obj}.getString(#{name}), Base64.DEFAULT)"
    when AST::VoidPrimitiveType
      "null"
    when AST::OptionalType
      "#{obj}.isNull(#{name}) ? null : #{type_from_json(t.base, obj, name)}"
    when AST::ArrayType
      "new #{native_type t}() {{ JSONArray ary = #{obj}.getJSONArray(#{name}); for (int i = 0; i < ary.length(); ++i) add(#{type_from_json(t.base, "ary", "i")}); }}"
    when AST::StructType
      "#{mangle t.name}.fromJSON(#{obj}.getJSONObject(#{name}))"
    when AST::EnumType
      "#{t.values.map {|v| "#{obj}.getString(#{name}).equals(#{v.inspect}) ? #{mangle t.name}.#{mangle v} : " }.join}null"
    when AST::TypeReference
      type_from_json(t.type, obj, name)
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
    when AST::StructType
      "#{src}.toJSON()"
    when AST::EnumType
      "#{t.values.map {|v| "#{src} == #{mangle t.name}.#{mangle v} ? #{v.inspect} : " }.join}\"\""
    when AST::TypeReference
      type_to_json(t.type, src)
    else
      raise "Unknown type"
    end
  end
end
