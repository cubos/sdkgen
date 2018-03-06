require "./target"
require "random/secure"
module AST
	class Type
		def kt_native_type 
			raise "todo: kt_native_type in #{self.class.name}"
		end

    def kt_return_type_name 
			raise "todo: kt_return_type_name in #{self.class.name}"
		end
	end
end 

class KtAndroidTarget < Target
  def mangle(ident)
    if %w[ 
      as as? break class continue do else false
      for fun if in !in interface is !is null object 
      package return super this throw true try typealias
      val var when while by catch constructor delegate 
      dynamic field file finally get import init param 
      property receiver set setparam where actual abstract  
      annotation companion const crossinline data enum expect
      external final infix inline inner internal lateinit noinline 
      open operator out override private protected public reified 
      sealed suspend tailrec vararg Double Float Long	Int	Short	Byte
       ].includes? ident
      "_" + ident
    else
      ident
    end
  end

  def gen
    @io << <<-END

open class API {
    interface Calls {\n
END
    @ast.operations.each do |op|
      args = op.args.map { |arg| "#{mangle arg.name}: #{arg.type.kt_native_type}" }
      args << "flag: Int? = null" # TODO make it something like API.DEFAULT and insert error parameter to callback
      args << if !op.return_type.is_a? AST::VoidPrimitiveType 
                "callback: (#{op.return_type.kt_return_type_name}: #{op.return_type.kt_native_type}) -> Unit" 
              else 
                "callback: () -> Unit"
              end
      @io << ident(String.build do |io|
        io << "   fun #{mangle op.pretty_name}(#{args.join(", ")}) \n"
      end)
    end
    @io << <<-END
    }

	companion object {
      var calls = object: Calls { \n
END

    @ast.operations.each do |op|
      args = op.args.map { |arg| "#{mangle arg.name}: #{arg.type.kt_native_type}" }
      args << "flag: Int? = null" # TODO make it something like API.DEFAULT and insert error parameter to callback
      args << if !op.return_type.is_a? AST::VoidPrimitiveType 
                "callback: (#{op.return_type.kt_return_type_name}: #{op.return_type.kt_native_type}) -> Unit" 
              else
                "callback: () -> Unit"
              end
      @io << ident(String.build do |io|
        io << "     override fun #{mangle op.pretty_name}(#{args.join(", ")}) {\n"
        io << "\n"
        io << "     }\n"
      end)
    end

    @io << <<-END
      } 
    }
}



END
  end
end

Target.register(KtAndroidTarget, target_name: "kt_android")
