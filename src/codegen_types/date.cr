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

    def typescript_expect(expr)
      String.build do |io|
        io << "expect(#{expr}).toBeInstanceOf(Date);\n"
        io << "expect(#{expr}.getHours()).toBe(0);\n"
        io << "expect(#{expr}.getMinutes()).toBe(0);\n"
        io << "expect(#{expr}.getSeconds()).toBe(0);\n"
        io << "expect(#{expr}.getMilliseconds()).toBe(0);\n"
      end
    end

    def typescript_check_encoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || typeof #{expr} !== \"string\" || !#{expr}.match(/^[0-9]{4}-[01][0-9]-[0123][0-9]$/)) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    def typescript_check_decoded(expr, descr)
      String.build do |io|
        io << "if (#{expr} === null || #{expr} === undefined || !(#{expr} instanceof Date)) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    def kt_decode(expr)      
      "SimpleDateFormat(\"yyyy-MM-dd\").format(#{expr})"
    end

    def kt_encode(expr)
      "Calendar.getInstance().apply { time = SimpleDateFormat(\"yyyy-MM-dd\").parse(#{expr}) }"
    end

    def kt_native_type
      "Calendar"
    end

    def kt_return_type_name
      "date"
    end
  end
end
