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

    def typescript_expect(expr)
      String.build do |io|
        io << "expect(#{expr}).toBeTypeOf(\"array\");\n"
        i = random_var
        io << "for (let #{i} = 0; #{i} < #{expr}.length; ++#{i}) {\n"
        io << ident base.typescript_expect("#{expr}[#{i}]")
        io << "}\n"
      end
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || !(#{expr} instanceof Array)) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "} else {\n"
        i = random_var
        io << ident "for (let #{i} = 0; #{i} < #{expr}.length; ++#{i}) {\n"
        io << ident ident base.typescript_check_encoded("#{expr}[#{i}]", "#{descr} + \"[\" + #{i} + \"]\"")
        io << ident "}\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || !(#{expr} instanceof Array)) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "} else {\n"
        i = random_var
        io << ident "for (let #{i} = 0; #{i} < #{expr}.length; ++#{i}) {\n"
        io << ident ident base.typescript_check_decoded("#{expr}[#{i}]", "#{descr} + \"[\" + #{i} + \"]\"")
        io << ident "}\n"
        io << "}\n"
      end
    end

    # KOTLIN
    def kt_decode(expr)
      "arrayListOf<#{base.kt_native_type}>(#{expr.join(",")})"
    end 

    def kt_encode(expr)
      raise expr
      # String.build do |io|
      #   io << "["
      #   elements = expr.map { |element| element.kt_encode + "," }   
      #   io << elements.join(",")
      #   io << "]"
      # end 
    end 

    def kt_native_type
      "ArrayList<#{base.kt_native_type}>"
    end

    def kt_return_type_name
      return "#{base.kt_native_type.camelcase}"
    end 
    # KOTLIN
  end
end
