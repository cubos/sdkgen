module AST
  class VoidPrimitiveType
    def typescript_decode(expr)
      "undefined"
    end

    def typescript_encode(expr)
      "null"
    end

    def typescript_native_type
      "void"
    end

    def typescript_expect(expr)
      ""
    end

    def typescript_check_encoded(expr, descr)
      ""
    end

    def typescript_check_decoded(expr, descr)
      ""
    end

    def kt_encode(expr)
      "null"
    end 

    def kt_decode(expr)
      "null"
    end 

    def kt_native_type
      raise "BUG: Void should never be used in target kotlin"
    end 

    def kt_return_type_name
      "" 
    end 
  end
end
