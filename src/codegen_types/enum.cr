module AST
  class EnumType
    def typescript_decode(expr)
      "#{expr}"
    end

    def typescript_encode(expr)
      "#{expr}"
    end

    def typescript_native_type
      name
    end

    def typescript_definition
      "export type #{name} = #{values.map(&.inspect).join(" | ")};"
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (typeof #{expr} !== \"string\" || !#{values.inspect}.includes(#{expr})) {\n"
        io << "    failTypeCheck(#{descr} + \", callId = \" + ctx.callId);\n"
        io << "}\n"
      end
    end
  end
end
