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

    def typescript_check_decoded(expr, descr)
      ""
    end
  end
end
