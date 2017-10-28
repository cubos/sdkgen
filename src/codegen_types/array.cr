require "../utils"

module AST
  class ArrayType
    def typescript_decode(expr)
      inner = base.typescript_decode("e")
      inner = "(#{inner})" if inner[0] == '{'
      "#{expr}.map((e: any) => #{inner})"
    end

    def typescript_encode(expr)
      inner = base.typescript_encode("e")
      inner = "(#{inner})" if inner[0] == '{'
      "#{expr}.map(e => #{inner})"
    end

    def typescript_native_type
      if base.is_a? AST::OptionalType
        "(#{base.typescript_native_type})[]"
      else
        base.typescript_native_type + "[]"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (!(#{expr} instanceof Array)) {\n"
        io << "    failTypeCheck(#{descr} + \", callId = \" + ctx.callId);\n"
        io << "} else {\n"
        i = random_var
        io << ident "for (let #{i} = 0; #{i} < #{expr}.length; ++#{i}) {\n"
        io << ident ident base.typescript_check_decoded("#{expr}[#{i}]", "#{descr} + \"[\" + #{i} + \"]\"")
        io << ident "}\n"
        io << "}\n"
      end
    end
  end
end
