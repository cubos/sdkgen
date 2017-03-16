require "./target"

abstract class SwiftTarget < Target
  def ident(code)
    super super code
  end

  def native_type(t : AST::PrimitiveType)
    case t
    when AST::StringPrimitiveType;   "String"
    when AST::IntPrimitiveType;      "Int"
    when AST::UIntPrimitiveType;     "UInt"
    when AST::FloatPrimitiveType;    "Double"
    when AST::DatePrimitiveType;     "Date"
    when AST::DateTimePrimitiveType; "Date"
    when AST::BoolPrimitiveType;     "Bool"
    when AST::BytesPrimitiveType;    "Data"
    when AST::VoidPrimitiveType;     "void"
    else
      raise "BUG! Should handle primitive #{t.class}"
    end
  end

  def native_type(t : AST::OptionalType)
    native_type(t.base) + "?"
  end

  def native_type(t : AST::ArrayType)
    native_type(t.base) + "[]"
  end

  def native_type(t : AST::CustomTypeReference)
    t.name
  end

  def generate_custom_type_interface(custom_type)
    String.build do |io|
      io << "class #{custom_type.name} {\n"
      custom_type.fields.each do |field|
        io << ident "var #{field.name}: #{native_type field.type}\n"
      end
      io << ident <<-END

NSDictionary toJSON() {
    let json: NSMutableDictionary = [:]

END
      custom_type.fields.each do |field|
        io << ident ident "json[\"#{field.name}\"] = #{type_to_json field.type, field.name}\n"
      end
      io << ident <<-END
    return json
}

static #{custom_type.name} fromJSON(final NSDictionary json) {
    let obj = #{custom_type.name}()

END
      custom_type.fields.each do |field|
        io << ident ident "obj.#{field.name} = #{type_from_json field.type, "json[#{field.name.inspect}]"}\n"
      end
      io << ident <<-END
    return obj;
}

END
      io << "}"
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
      "APIInternal.decodeDate(#{src})"
    when AST::DateTimePrimitiveType
      "APIInternal.decodeDateTime(#{src})"
    when AST::BytesPrimitiveType
      "Data(base64EncodedString: ${src} as! String, options: NSDataBase64DecodingOptions(rawValue: 0))!"
    when AST::VoidPrimitiveType
      "nil"
    when AST::OptionalType
      "#{src} == nil ? nil : #{type_from_json(t.base, src)}"
    when AST::ArrayType
      "(#{src} as! [AnyObject]).map({(el) -> #{native_type t.base} in return #{type_from_json t.base, "el"}})"
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
      "APIInternal.encodeDate(#{src})"
    when AST::DateTimePrimitiveType
      "APIInternal.encodeDateTime(#{src})"
    when AST::BytesPrimitiveType
      "#{src}.base64EncodedStringWithOptions(NSDataBase64EncodingOptions(rawValue: 0))"
    when AST::VoidPrimitiveType
      "nil"
    when AST::OptionalType
      "#{src} == nil ? nil : #{type_to_json(t.base, src + "!")}"
    when AST::ArrayType
      "#{src}.map({ return #{type_to_json t.base, "$0"} })"
    when AST::CustomTypeReference
      "#{src}.toJSON()"
    else
      raise "Unknown type"
    end
  end
end
