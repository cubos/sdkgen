require "./target"

abstract class SwiftTarget < Target
  def native_type(t : AST::PrimitiveType)
    case t
    when AST::StringPrimitiveType  ; "String"
    when AST::IntPrimitiveType     ; "Int"
    when AST::UIntPrimitiveType    ; "UInt"
    when AST::FloatPrimitiveType   ; "Double"
    when AST::DatePrimitiveType    ; "Date"
    when AST::DateTimePrimitiveType; "Date"
    when AST::BoolPrimitiveType    ; "Bool"
    when AST::BytesPrimitiveType   ; "Data"
    when AST::VoidPrimitiveType    ; "void"
    else
      raise "BUG! Should handle primitive #{t.class}"
    end
  end

  def native_type(t : AST::OptionalType)
    native_type(t.base) + "?"
  end

  def native_type(t : AST::ArrayType)
    "[#{native_type(t.base)}]"
  end

  def native_type(t : AST::StructType | AST::EnumType)
    t.name
  end

  def native_type(ref : AST::TypeReference)
    native_type ref.type
  end

  def generate_property_copy(t : AST::Type, path : String)
    case t
    when AST::StringPrimitiveType  ; path
    when AST::IntPrimitiveType     ; path
    when AST::UIntPrimitiveType    ; path
    when AST::FloatPrimitiveType   ; path
    when AST::DatePrimitiveType    ; path
    when AST::DateTimePrimitiveType; path
    when AST::BoolPrimitiveType    ; path
    when AST::BytesPrimitiveType   ; path
    when AST::ArrayType            ; "#{path}.map { #{generate_property_copy(t.base, "$0")} }"
    when AST::EnumType             ; "#{path}.copy"
    when AST::StructType           ; "#{path}.copy"
    when AST::OptionalType         ; "#{path} != nil ? #{generate_property_copy(t.base, path + "!")} : nil"
    when AST::TypeReference        ; generate_property_copy(t.type, path)
    else
      raise "BUG! Should handle primitive #{t.class}"
    end
  end


  def generate_struct_type(t)
    String.build do |io|
      io << "class #{t.name} {\n"
      t.fields.each do |field|
        io << ident "var #{field.name}: #{native_type field.type}\n"
      end
      io << ident "\nvar copy: #{t.name} {\n"
      io << ident ident "return #{t.name}(\n"
      io << ident ident ident t.fields.map { |field| "#{field.name}: #{generate_property_copy(field.type, field.name)}"}.join(",\n")
      io << ident ident "\n)\n"
      io << ident "}\n"
      io << ident <<-END


init() {

END
      t.fields.each do |field|
        io << ident ident "#{field.name} = #{default_value field.type}\n"
      end
      io << ident <<-END
}

init(#{t.fields.map { |f| "#{f.name}: #{native_type f.type}" }.join(", ")}) {

END
      t.fields.each do |field|
        io << ident ident "self.#{field.name} = #{field.name}\n"
      end
      io << ident <<-END
}

init(json: [String: Any]) {

END
      t.fields.each do |field|
        io << ident ident "#{field.name} = #{type_from_json field.type, "json[#{field.name.inspect}]"}\n"
      end
      io << ident <<-END
}

func toJSON() -> [String: Any] {
    var json = [String: Any]()

END
      t.fields.each do |field|
        io << ident ident "json[\"#{field.name}\"] = #{type_to_json field.type, field.name}\n"
      end
      io << ident <<-END
    return json
}

END
      io << "}"
    end
  end

  def generate_enum_type(t)
    String.build do |io|
      if t.name == "ErrorType"
        io << "enum #{t.name}: String,Error {\n"
      else
        io << "enum #{t.name}: String {\n"
      end

      t.values.each do |value|
        io << ident "case #{value} = #{value.inspect}\n"
      end
      io << ident "\nvar copy: #{t.name} {\n"
      io << ident ident "return #{t.name}(rawValue: self.rawValue)!\n"
      io << ident "}\n"
      io << "}"
    end
  end

  def generate_enum_type_extension(t)
    String.build do |io|
      io << "extension #{t.name}: EnumCollection {\n"
      io << ident "func displayableValue() -> String {\n"
      io << ident ident "return self.rawValue\n"
      io << ident "}\n"

      io << ident "\nstatic func valuesDictionary() -> [String : #{t.name}] {\n"
      io << ident ident "var dictionary: [String : #{t.name}] = [:]\n"
      io << ident ident "for enumCase in self.allValues {\n"
      io << ident ident ident "dictionary[enumCase.displayableValue()] = enumCase\n"
      io << ident ident "}\n"
      io << ident ident "return dictionary\n"
      io << ident "}\n"

      io << ident "\nstatic func allDisplayableValues() -> [String] {\n"
      io << ident ident "var displayableValues: [String] = []\n"
      io << ident ident "for value in self.allValues {\n"
      io << ident ident ident "displayableValues.append(value.displayableValue())\n"
      io << ident ident "}\n"
      io << ident ident "return displayableValues.sorted()\n"
      io << ident "}\n"

      io << "}"
    end
  end

  def default_value(t : AST::Type)
    case t
    when AST::StringPrimitiveType
      "\"\""
    when AST::IntPrimitiveType, AST::UIntPrimitiveType, AST::FloatPrimitiveType
      "0"
    when AST::BoolPrimitiveType
      "false"
    when AST::DatePrimitiveType, AST::DateTimePrimitiveType
      "Date()"
    when AST::BytesPrimitiveType
      "Data()"
    when AST::VoidPrimitiveType
      "nil"
    when AST::OptionalType
      "nil"
    when AST::ArrayType
      "[]"
    when AST::StructType
      "#{t.name}()"
    when AST::EnumType
      "#{t.name}.#{t.values[0]}" # (rawValue: \"\")"
    when AST::TypeReference
      default_value(t.type)
    else
      raise "Unknown type"
    end
  end

  def type_from_json(t : AST::Type, src : String)
    case t
    when AST::StringPrimitiveType
      "#{src} as! String"
    when AST::IntPrimitiveType
      "#{src} as! Int"
    when AST::UIntPrimitiveType
      "#{src} as! UInt"
    when AST::FloatPrimitiveType
      "#{src} as! Double"
    when AST::BoolPrimitiveType
      "#{src} as! Bool"
    when AST::DatePrimitiveType
      "APIInternal.decodeDate(str: #{src} as! String)"
    when AST::DateTimePrimitiveType
      "APIInternal.decodeDateTime(str: #{src} as! String)"
    when AST::BytesPrimitiveType
      "Data(base64Encoded: #{src} as! String)!"
    when AST::VoidPrimitiveType
      "nil"
    when AST::OptionalType
      base = t.base
      if base.is_a? AST::EnumType
        "APIInternal.isNull(value: #{src}) ? nil : #{base.name}(rawValue: #{src} as! String)"
      else
        "APIInternal.isNull(value: #{src}) ? nil : (#{type_from_json(t.base, src)})"
      end
    when AST::ArrayType
      "(#{src} as! [AnyObject]).map({ #{type_from_json t.base, "$0"} })"
    when AST::StructType
      "#{t.name}(json: #{src} as! [String: Any])"
    when AST::EnumType
      "#{t.name}(rawValue: #{src} as! String)!"
    when AST::TypeReference
      type_from_json(t.type, src)
    else
      raise "Unknown type"
    end
  end

  def type_to_json(t : AST::Type, src : String)
    case t
    when AST::StringPrimitiveType, AST::IntPrimitiveType, AST::UIntPrimitiveType, AST::FloatPrimitiveType, AST::BoolPrimitiveType
      "#{src}"
    when AST::DatePrimitiveType
      "APIInternal.encodeDate(date: #{src})"
    when AST::DateTimePrimitiveType
      "APIInternal.encodeDateTime(date: #{src})"
    when AST::BytesPrimitiveType
      "#{src}.base64EncodedString()"
    when AST::VoidPrimitiveType
      "nil"
    when AST::OptionalType
      "#{src} == nil ? nil : #{type_to_json(t.base, src + "!")}"
    when AST::ArrayType
      "#{src}.map({ return #{type_to_json t.base, "$0"} })"
    when AST::StructType
      "#{src}.toJSON()"
    when AST::EnumType
      "#{src}.rawValue"
    when AST::TypeReference
      type_to_json(t.type, src)
    else
      raise "Unknown type"
    end
  end
end
