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
  end
end
