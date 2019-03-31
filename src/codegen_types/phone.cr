module AST
  class PhonePrimitiveType
    def typescript_decode(expr)
      "#{expr}"
    end

    def typescript_encode(expr)
      "#{expr}.replace(/[^0-9+]/g, \"\")"
    end

    def typescript_native_type
      "string"
    end

    def typescript_expect(expr)
      String.build do |io|
        io << "expect(#{expr}).toBeTypeOf(\"string\");\n"
        io << "expect(#{expr}.replace(/[^0-9+]/g, \"\").length).toBeGreaterThanOrEqual(5);\n"
        io << "expect(#{expr}[0]).toBe(\"+\");\n"
      end
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"string\" || #{expr}.replace(/[^0-9+]/g, \"\").length < 5 || #{expr}[0] !== \"+\") {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"string\" || #{expr}.replace(/[^0-9+]/g, \"\").length < 5 || #{expr}[0] !== \"+\") {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    # KOTLIN
    def kt_decode(expr, desc)
      "#{expr}.getString(#{desc})"
    end 

    def kt_encode(expr)
      "#{expr}"
    end 

    def kt_native_type
      "String"
    end

    def kt_return_type_name
      "phone"
    end 
    # KOTLIN

  end
end
