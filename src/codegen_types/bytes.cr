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

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (typeof #{expr} !== \"string\" || !#{expr}.match(/^([0-9a-f]{2})*$/)) {\n"
        io << "    failTypeCheck(#{descr}, ctx);\n"
        io << "}\n"
      end
    end
  end
end
