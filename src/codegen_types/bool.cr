module AST
  class BoolPrimitiveType
    def typescript_decode(expr)
      "!!#{expr}"
    end

    def typescript_encode(expr)
      "!!#{expr}"
    end

    def typescript_native_type
      "boolean"
    end

    def typescript_expect(expr)
      String.build do |io|
        io << "expect(#{expr}).toBeTypeOf(\"boolean\");\n"
      end
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} !== true && #{expr} !== false) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} !== true && #{expr} !== false) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    # KOTLIN
    def kt_decode(expr, desc)
      "#{expr}.getBoolean(#{desc})"
    end

    def kt_encode(expr, desc)
      "#{expr}"
    end

    def kt_native_type
      "Boolean"
    end

    def kt_return_type_name
      "boolean"
    end
    # KOTLIN

  end
end
