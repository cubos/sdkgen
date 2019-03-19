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
    when AST::VoidPrimitiveType    ; "NoReply"
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
    "API." + t.name
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
      io << "class #{t.name}: Codable {\n"
      t.fields.each do |field|
        io << ident "var #{field.name}: #{native_type field.type}\n"
      end
      io << ident "\nvar copy: #{t.name} {\n"
      io << ident ident "return #{t.name}(\n"
      io << ident ident ident t.fields.map { |field| "#{field.name}: #{generate_property_copy(field.type, field.name)}" }.join(",\n")
      io << ident ident "\n)\n"
      io << ident "}\n"
      t.spreads.map(&.type.as(AST::StructType)).map { |spread|
        io << ident "\nvar as#{spread.name}: #{spread.name} {\n"
        io << ident ident "return #{spread.name}(\n"
        io << ident ident ident spread.fields.map { |field| "#{field.name}: #{field.name}" }.join(",\n")
        io << ident ident "\n)\n"
        io << ident "}\n"
      }
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

init(json: [String: Any]) throws {
    let jsonData = try JSONSerialization.data(withJSONObject: json, options: .prettyPrinted)
    let decodedSelf = try decoder.decode(#{t.name}.self, from: jsonData)\n

END
      t.fields.each do |field|
        io << ident ident "#{field.name} = decodedSelf.#{field.name}\n"
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
        io << "enum #{t.name}: String, Error, Codable {\n"
      else
        io << "enum #{t.name}: String, CaseIterable, DisplayableValue, Codable {\n"
      end

      t.values.each do |value|
        io << ident "case #{value} = #{value.inspect}\n"
      end
      if t.name != "ErrorType"
        io << ident "\nvar copy: #{t.name} {\n"
        io << ident ident "return #{t.name}(rawValue: self.rawValue)!\n"
        io << ident "}\n"
        io << ident "\nstatic func valuesDictionary() -> [String: #{t.name}] {\n"
        io << ident ident "var dictionary: [String: #{t.name}] = [:]\n"
        io << ident ident "for enumCase in self.allCases {\n"
        io << ident ident ident "dictionary[enumCase.displayableValue] = enumCase\n"
        io << ident ident "}\n"
        io << ident ident "return dictionary\n"
        io << ident "}\n"

        io << ident "\nstatic func allDisplayableValues() -> [String] {\n"
        io << ident ident "var displayableValues: [String] = []\n"
        io << ident ident "for enumCase in self.allCases {\n"
        io << ident ident ident "displayableValues.append(enumCase.displayableValue)\n"
        io << ident ident "}\n"
        io << ident ident "return displayableValues.sorted()\n"
        io << ident "}\n"
      end
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
      "#{t.name}.#{t.values[0]}"
    when AST::TypeReference
      default_value(t.type)
    else
      raise "Unknown type"
    end
  end

  def type_to_json(t : AST::Type, src : String)
    case t
    when AST::StringPrimitiveType, AST::IntPrimitiveType, AST::UIntPrimitiveType, AST::FloatPrimitiveType, AST::BoolPrimitiveType
      "#{src}"
    when AST::DatePrimitiveType
      "apiInternal.encodeDate(date: #{src})"
    when AST::DateTimePrimitiveType
      "apiInternal.encodeDateTime(date: #{src})"
    when AST::BytesPrimitiveType
      "#{src}.base64EncodedString()"
    when AST::VoidPrimitiveType
      "nil"
    when AST::OptionalType
      "#{src} != nil ? #{type_to_json(t.base, src + "!")} : nil"
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
