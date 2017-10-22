module AST
  class VoidPrimitiveType
    def typescript_decode(expr)
      "undefined"
    end

    def typescript_encode(expr)
      "null"
    end

    def typescript_native_type
      "null"
    end
  end
end
