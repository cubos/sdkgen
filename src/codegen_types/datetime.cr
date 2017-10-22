module AST
  class DateTimePrimitiveType
    def typescript_decode(expr)
      "moment.utc(#{expr}, \"YYYY-MM-DDTHH:mm:ss.SSS\").toDate()"
    end

    def typescript_encode(expr)
      "moment.utc(#{expr}).format(\"YYYY-MM-DDTHH:mm:ss.SSS\")"
    end

    def typescript_native_type
      "Date"
    end
  end
end
