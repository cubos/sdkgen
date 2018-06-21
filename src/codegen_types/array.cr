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
    # TODO make this itearion call the fromJson method of the type if its a struct or fromJsonArray if its another array
    def kt_decode(expr, desc)
        String.build do |io|
          io << "ArrayList<#{base.kt_native_type}>().apply {\n" 
          io << "    for (i in 0 until #{expr}.length()) {\n"
          if base.is_a? AST::ArrayType
            io << "        add(#{base.kt_native_type}.fromJsonArray(#{expr}.getJSONArray(i)))\n"
          elsif base.is_a? AST::StructType
            io << "        add(#{base.kt_native_type}.fromJson(#{expr}.getJSONObject(i)))\n"
          else 
            io << "        add(#{base.kt_decode(expr, "i")})"
          end
          io << "    }\n" 
          io << ""
        end
    end 

    def kt_encode(expr)
      inner = base.kt_encode("item")
      "JSONArray().apply { 
        #{expr}.forEach { item -> put(#{inner}) }
      }"
    end 

    def kt_native_type
      "ArrayList<#{base.kt_native_type}>"
    end

    def kt_return_type_name
      ktType = base.kt_native_type
      ktType = ktType[0].upcase + ktType[1..-1]
      "#{ktType}"
    end 
    # KOTLIN
  end
end
