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

    def typescript_expect(expr)
      String.build do |io|
        x = random_var
        io << "const #{x} = #{expr};\n"
        io << "if (#{x} !== null && #{x} !== undefined) {\n"
        io << ident base.typescript_expect(x)
        io << "}\n"
      end
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        x = random_var
        io << "const #{x} = #{expr};\n"
        io << "if (#{x} !== null && #{x} !== undefined) {\n"
        io << ident base.typescript_check_encoded(x, descr)
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        x = random_var
        io << "const #{x} = #{expr};\n"
        io << "if (#{x} !== null && #{x} !== undefined) {\n"
        io << ident base.typescript_check_decoded(x, descr)
        io << "}\n"
      end
    end
    
    # KOTLIN
    def kt_decode(expr, desc)
      decodeExpr = ""
      # if base.is_a? AST::ArrayType
          # decodeExpr = "try { #{base.kt_native_type}.fromJsonArray(#{expr}) } catch(e: Exception) { null } "
      # elsif base.is_a? AST::StructType
      #     decodeExpr = "try { #{base.kt_native_type}.fromJson(#{expr}) } catch(e: Exception) { null } "
      # else
          decodeExpr = "#{base.kt_decode(expr, desc)}"
      # end
      "#{decodeExpr}"
    end 

    def kt_encode(expr, desc)
      "#{expr}?.let { #{base.kt_encode(expr, desc)} }"
    end 

    def kt_native_type
      "#{base.kt_native_type}?" 
    end

    def kt_return_type_name
      "#{base.kt_return_type_name}"
    end 
    # KOTLIN 
  end
end
