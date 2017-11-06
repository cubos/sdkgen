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
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    setTimeout(() => captureError(err, ctx.req, ctx.call), 1);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} !== true && #{expr} !== false) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    setTimeout(() => captureError(err, ctx.req, ctx.call), 1);\n"
        io << "}\n"
      end
    end
  end
end
