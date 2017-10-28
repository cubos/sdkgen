module AST
  class BytesPrimitiveType
    def typescript_decode(expr)
      "Buffer.from(#{expr}, \"base64\")"
    end

    def typescript_encode(expr)
      "#{expr}.toString(\"base64\")"
    end

    def typescript_native_type
      "Buffer"
    end

    def typescript_check_decoded(expr, descr)
      ""
    end
  end
end
