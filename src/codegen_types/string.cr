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

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (typeof (#{expr}) !== \"string\") {\n"
        io << "    failedCheckTypeError(#{descr});\n"
        io << "}\n"
      end
    end
  end
end
