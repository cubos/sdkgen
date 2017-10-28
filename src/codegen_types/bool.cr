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

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} !== true && #{expr} !== false) {\n"
        io << "    failTypeCheck(#{descr}, ctx);\n"
        io << "}\n"
      end
    end
  end
end
