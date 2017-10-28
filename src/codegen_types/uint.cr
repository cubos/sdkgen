module AST
  class UIntPrimitiveType
    def typescript_decode(expr)
      "#{expr}|0"
    end

    def typescript_encode(expr)
      "#{expr}|0"
    end

    def typescript_native_type
      "number"
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (typeof #{expr} !== \"number\" || (#{expr} | 0) !== #{expr} || #{expr} < 0) {\n"
        io << "    failTypeCheck(#{descr} + \", callId = \" + ctx.callId);\n"
        io << "}\n"
      end
    end
  end
end
