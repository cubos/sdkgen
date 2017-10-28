module AST
  class FloatPrimitiveType
    def typescript_decode(expr)
      "#{expr}"
    end

    def typescript_encode(expr)
      "#{expr}"
    end

    def typescript_native_type
      "number"
    end

    def typescript_check_decoded(expr, descr)
      ""
    end
  end
end
