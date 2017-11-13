module AST
  class MoneyPrimitiveType
    def typescript_decode(expr)
      "#{expr}|0"
    end

    def typescript_encode(expr)
      "#{expr}|0"
    end

    def typescript_native_type
      "number"
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"number\" || (#{expr} | 0) !== #{expr} || #{expr} < 0) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    setTimeout(() => captureError(err, ctx.req, ctx.call), 1000);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"number\" || (#{expr} | 0) !== #{expr} || #{expr} < 0) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    setTimeout(() => captureError(err, ctx.req, ctx.call), 1000);\n"
        io << "}\n"
      end
    end
  end
end
