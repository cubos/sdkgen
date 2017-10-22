module AST
  class StructType
    def typescript_decode(expr)
      String::Builder.build do |io|
        io << "{\n"
        fields.each do |field|
          io << "    #{field.name}: #{field.type.typescript_decode("#{expr}.#{field.name}")},\n"
        end
        io << "}"
      end
    end

    def typescript_encode(expr)
      String::Builder.build do |io|
        io << "{\n"
        fields.each do |field|
          io << "    #{field.name}: #{field.type.typescript_encode("#{expr}.#{field.name}")},\n"
        end
        io << "}"
      end
    end

    def typescript_native_type
      name
    end

    def typescript_definition
      String.build do |io|
        io << "export interface #{name} {\n"
        fields.each do |field|
          io << "    #{field.name}: #{field.type.typescript_native_type};\n"
        end
        io << "}"
      end
    end
  end
end
