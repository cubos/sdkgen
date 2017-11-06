module AST
  class DatePrimitiveType
    def typescript_decode(expr)
      "moment(#{expr}, \"YYYY-MM-DD\").toDate()"
    end

    def typescript_encode(expr)
      "moment(#{expr}).format(\"YYYY-MM-DD\")"
    end

    def typescript_native_type
      "Date"
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (typeof #{expr} !== \"string\" || !#{expr}.match(/^[0-9]{4}-[01][0-9]-[0123][0-9]$/)) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    setTimeout(() => captureError(err, ctx.req, ctx.call), 1);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (!(#{expr} instanceof Date)) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"'\");\n"
        io << "    setTimeout(() => captureError(err, ctx.req, ctx.call), 1);\n"
        io << "}\n"
      end
    end
  end
end
