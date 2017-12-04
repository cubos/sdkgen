module AST
  class LatLngPrimitiveType
    def typescript_decode(expr)
      "{lat: #{expr}.lat, lng: #{expr}.lng}"
    end

    def typescript_encode(expr)
      "{lat: #{expr}.lat, lng: #{expr}.lng}"
    end

    def typescript_native_type
      "{lat: number, lng: number}"
    end

    def typescript_expect(expr)
      String.build do |io|
        io << "expect(#{expr}).toBeTypeOf(\"object\");\n"
        io << "expect(#{expr}.lat).toBeTypeOf(\"number\");\n"
        io << "expect(#{expr}.lng).toBeTypeOf(\"number\");\n"
      end
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"object\" || typeof #{expr}.lat !== \"number\" || typeof #{expr}.lng !== \"number\") {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      typescript_check_encoded(expr, descr)
    end
  end
end
