module AST
  class HexPrimitiveType
    def typescript_decode(expr)
      "#{expr}"
    end

    def typescript_encode(expr)
      "#{expr}"
    end

    def typescript_native_type
      "string"
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (typeof #{expr} !== \"string\" || !#{expr}.match(/^([0-9a-f]{2})*$/)) {\n"
        io << "    failTypeCheck(#{descr}, ctx);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (typeof #{expr} !== \"string\" || !#{expr}.match(/^([0-9a-f]{2})*$/)) {\n"
        io << "    failTypeCheck(#{descr}, ctx);\n"
        io << "}\n"
      end
    end
  end
end
