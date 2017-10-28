module AST
  class FloatPrimitiveType
    def typescript_decode(expr)
      "#{expr}"
    end

    def typescript_encode(expr)
      "#{expr}"
    end

    def typescript_native_type
      "number"
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (typeof #{expr} !== \"number\") {\n"
        io << "    failTypeCheck(#{descr} + \", callId = \" + ctx.callId);\n"
        io << "}\n"
      end
    end
  end
end
