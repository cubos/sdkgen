module AST
  class StringPrimitiveType
    def typescript_decode(expr)
      "#{expr}"
      # "#{expr}.normalize()"
    end

    def typescript_encode(expr)
      "#{expr}"
      # "#{expr}.normalize()"
    end

    def typescript_native_type
      "string"
    end
  end
end
