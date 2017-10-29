module AST
  class CpfPrimitiveType
    def typescript_decode(expr)
      "#{expr}.replace(/(...)(...)(...)(..)/, \"$1.$2.$3-$4\")"
    end

    def typescript_encode(expr)
      "#{expr}.replace(/[^0-9]/g, \"\").padStart(11, \"0\")"
    end

    def typescript_native_type
      "string"
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (typeof #{expr} !== \"string\" || #{expr}.replace(/[^0-9]/g, \"\").length !== 11) {\n"
        io << "    failTypeCheck(#{descr}, ctx);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (typeof #{expr} !== \"string\" || #{expr}.replace(/[^0-9]/g, \"\").length !== 11) {\n"
        io << "    failTypeCheck(#{descr}, ctx);\n"
        io << "}\n"
      end
    end
  end
end
