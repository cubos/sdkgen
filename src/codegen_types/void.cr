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
  end
end
