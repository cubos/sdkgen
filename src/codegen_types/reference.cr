module AST
  class TypeReference
    def typescript_decode(expr)
      type.typescript_decode(expr)
    end

    def typescript_encode(expr)
      type.typescript_encode(expr)
    end

    def typescript_native_type
      type.typescript_native_type
    end

    def typescript_expect(expr)
      type.typescript_expect(expr)
    end

    def typescript_check_encoded(expr, descr)
      type.typescript_check_encoded(expr, descr)
    end

    def typescript_check_decoded(expr, descr)
      type.typescript_check_decoded(expr, descr)
    end
  end
end
