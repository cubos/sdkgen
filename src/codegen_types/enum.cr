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
      # String::Builder.build do |io|
      #   io << "export enum #{name} {\n"
      #   values.each do |value|
      #     io << ident "#{value} = \"#{value}\",\n"
      #   end
      #   io << "}"
      # end
    end

    def typescript_expect(expr)
      String.build do |io|
        io << "expect(#{expr}).toBeTypeOf(\"string\");\n"
        io << "expect(#{expr}).toMatch(/#{values.join("|")}/);\n"
      end
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"string\" || !#{values.inspect}.includes(#{expr})) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"string\" || !#{values.inspect}.includes(#{expr})) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    # KOTLIN
    def kt_decode(expr)
      "#{name}.valueOf(#{expr})"
    end 

    def kt_encode(expr)
      "#{expr}.name"
    end 

    def kt_definition
      String.build do |io|
      io << "enum class #{name} {\n"
        io << values.join(", \n")
        io << "\n}"
      end
    end

    def kt_native_type
      "#{name}"
    end

    def kt_return_type_name
      "#{name}"
    end 
    # KOTLIN
  end
end
