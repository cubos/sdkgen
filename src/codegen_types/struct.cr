module AST
  class StructType
    def typescript_decode(expr)
      String::Builder.build do |io|
        io << "{\n"
        fields.each do |field|
          io << ident "#{field.name}: #{field.type.typescript_decode("#{expr}.#{field.name}")},\n"
        end
        io << "}"
      end
    end

    def typescript_encode(expr)
      String::Builder.build do |io|
        io << "{\n"
        fields.each do |field|
          io << ident "#{field.name}: #{field.type.typescript_encode("#{expr}.#{field.name}")},\n"
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

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    setTimeout(() => captureError(err, ctx.req, ctx.call), 1000);\n"
        io << "}\n"
        fields.each do |field|
          io << field.type.typescript_check_encoded("#{expr}.#{field.name}", "#{descr} + \".#{field.name}\"")
        end
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    setTimeout(() => captureError(err, ctx.req, ctx.call), 1000);\n"
        io << "}\n"
        fields.each do |field|
          io << field.type.typescript_check_decoded("#{expr}.#{field.name}", "#{descr} + \".#{field.name}\"")
        end
      end
    end
  end
end
