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
    "[#{native_type(t.base)}]"
  end

  def native_type(t : AST::TypeReference)
    t.name
  end

  def generate_custom_type_interface(custom_type)
    String.build do |io|
      io << "class #{custom_type.name} {\n"
      custom_type.fields.each do |field|
        io << ident "var #{field.name}: #{native_type field.type}\n"
      end
      io << ident <<-END

init() {

END
      custom_type.fields.each do |field|
        io << ident ident "#{field.name} = #{default_value field.type}\n"
      end
      io << ident <<-END
}

init(#{custom_type.fields.map{|f| "#{f.name}: #{native_type f.type}"}.join(", ")}) {

END
      custom_type.fields.each do |field|
        io << ident ident "self.#{field.name} = #{field.name}\n"
      end
      io << ident <<-END
}

init(json: [String: Any]) {

END
      custom_type.fields.each do |field|
        io << ident ident "#{field.name} = #{type_from_json field.type, "json[#{field.name.inspect}]"}\n"
      end
      io << ident <<-END
}

func toJSON() -> [String: Any] {
    var json = [String: Any]()

END
      custom_type.fields.each do |field|
        io << ident ident "json[\"#{field.name}\"] = #{type_to_json field.type, field.name}\n"
      end
      io << ident <<-END
    return json
}

END
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
    when AST::TypeReference
      ct = @ast.custom_types.find {|x| x.name == t.name }.not_nil!
      "#{ct.name}()"
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
      "#{src} is NSNull ? nil : (#{type_from_json(t.base, src)})"
    when AST::ArrayType
      "(#{src} as! [AnyObject]).map({(el) -> #{native_type t.base} in return #{type_from_json t.base, "el"}})"
    when AST::TypeReference
      ct = @ast.custom_types.find {|x| x.name == t.name }.not_nil!
      "#{ct.name}(json: #{src} as! [String: Any])"
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
    when AST::TypeReference
      "#{src}.toJSON()"
    else
      raise "Unknown type"
    end
  end
end
