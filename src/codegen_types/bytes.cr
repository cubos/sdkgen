module AST
  class BytesPrimitiveType
    def typescript_decode(expr)
      "Buffer.from(#{expr}, \"base64\")"
    end

    def typescript_encode(expr)
      "#{expr}.toString(\"base64\")"
    end

    def typescript_native_type
      "Buffer"
    end

    def typescript_expect(expr)
      String.build do |io|
        io << "expect(#{expr}).toBeInstanceOf(Buffer);\n"
      end
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        # io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"string\" || !#{expr}.match(/^(?:[A-Za-z0-9+\/]{4}\\n?)*(?:[A-Za-z0-9+\/]{2}==|[A-Za-z0-9+\/]{3}=)$/)) {\n"
        # io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{class.name}, got '\" + #{expr} + \"'\");\n"
        # io << "    typeCheckerError(err, ctx);\n"
        # io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || !(#{expr} instanceof Buffer)) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end
  end
end
