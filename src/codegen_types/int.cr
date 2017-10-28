module AST
  class IntPrimitiveType
    def typescript_decode(expr)
      "#{expr}|0"
    end

    def typescript_encode(expr)
      "#{expr}|0"
    end

    def typescript_native_type
      "number"
    end

    def typescript_check_decoded(expr, descr)
      ""
    end
  end
end
