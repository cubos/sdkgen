module AST
  class DatePrimitiveType
    def typescript_decode(expr)
      "new Date(parseInt(#{expr}.split(\"-\")[0], 10), parseInt(#{expr}.split(\"-\")[1], 10) - 1, parseInt(#{expr}.split(\"-\")[2], 10))"
    end

    def typescript_encode(expr)
      "typeof(#{expr}) === \"string\" ? new Date(new Date(#{expr}).getTime() - new Date(#{expr}).getTimezoneOffset() * 60000).toISOString().split(\"T\")[0] : new Date(#{expr}.getTime() - #{expr}.getTimezoneOffset() * 60000).toISOString().split(\"T\")[0]"
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
        io << "if (#{expr} === null || #{expr} === undefined || !(#{expr} instanceof Date || ((#{expr} as any).match && (#{expr} as any).match(/^[0-9]{4}-[01][0-9]-[0123][0-9]/)))) {\n"
        io << "    const err = new Error(\"Invalid Type at '\" + #{descr} + \"', expected #{self.class.name}, got '\" + #{expr} + \"'\");\n"
        io << "    typeCheckerError(err, ctx);\n"
        io << "}\n"
      end
    end

    # KOTLIN
    def kt_decode(expr, desc)      
      "Calendar.getInstance().apply { time = SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(#{expr}.getString(#{desc})) }"
    end

    def kt_encode(expr)
      "SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").format(#{expr})"
    end

    def kt_native_type
      "Calendar"
    end

    def kt_return_type_name
      "datetime"
    end
    # KOTLIN
  end
end
