module AST
  class EnumType
    def typescript_decode(expr)
      "#{expr}"
    end

    def typescript_encode(expr)
      "#{expr}"
    end

    def typescript_native_type
      name
    end

    def typescript_definition
      "export type #{name} = #{values.map(&.inspect).join(" | ")};"
    end

    def typescript_check_decoded(expr, descr)
      ""
    end
  end
end
