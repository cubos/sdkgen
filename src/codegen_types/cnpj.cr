module AST
  class CnpjPrimitiveType
    def typescript_decode(expr)
      "#{expr}.replace(/(..)(...)(...)(....)(..)/, \"$1.$2.$3/$4-$5\")"
    end

    def typescript_encode(expr)
      "#{expr}.replace(/[^0-9]/g, \"\").padStart(14, \"0\")"
    end

    def typescript_native_type
      "string"
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"string\" || #{expr}.replace(/[^0-9]/g, \"\").length !== 14) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    setTimeout(() => captureError(err, ctx.req, ctx.call), 1000);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"string\" || #{expr}.replace(/[^0-9]/g, \"\").length !== 14) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    setTimeout(() => captureError(err, ctx.req, ctx.call), 1000);\n"
        io << "}\n"
      end
    end
  end
end
