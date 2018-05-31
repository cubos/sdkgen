module AST
  class UIntPrimitiveType
    def typescript_decode(expr)
      "#{expr} | 0"
    end

    def typescript_encode(expr)
      "#{expr} | 0"
    end

    def typescript_native_type
      "number"
    end

    def typescript_expect(expr)
      String.build do |io|
        io << "expect(#{expr}).toBeTypeOf(\"number\");\n"
        io << "expect(#{expr} - (#{expr} | 0)).toBe(0);\n"
        io << "expect(#{expr}).toBeGreaterThanOrEqual(0);\n"
      end
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"number\" || (#{expr} | 0) !== #{expr} || #{expr} < 0) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"number\" || (#{expr} | 0) !== #{expr} || #{expr} < 0) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end
    
    #Kotlin
    def kt_decode(expr)
      "#{expr}"
    end

    def kt_encode(expr)
      "#{expr}"
    end

    def kt_native_type
      "Int"
    end

    def kt_return_type_name
      "value"
    end
    #Kotlin
  end
end
