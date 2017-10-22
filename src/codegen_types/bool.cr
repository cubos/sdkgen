module AST
  class BoolPrimitiveType
    def typescript_decode(expr)
      "!!#{expr}"
    end

    def typescript_encode(expr)
      "!!#{expr}"
    end

    def typescript_native_type
      "boolean"
    end
  end
end
