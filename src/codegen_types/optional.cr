module AST
  class OptionalType
    def typescript_decode(expr)
      "#{expr} === null || #{expr} === undefined ? null : #{base.typescript_decode(expr)}"
    end

    def typescript_encode(expr)
      "#{expr} === null || #{expr} === undefined ? null : #{base.typescript_encode(expr)}"
    end

    def typescript_native_type
      "#{base.typescript_native_type} | null"
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        x = random_var
        io << "const #{x} = #{expr};\n"
        io << "if (#{x}) {\n"
        io << ident base.typescript_check_encoded(x, descr)
        io << "}\n"
      end
    end
  end
end
