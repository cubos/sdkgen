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
  
    def kt_encode(expr) 
			raise "todo: kt_encode in #{self.class.name}"
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

import android.location.Location
import android.util.Base64
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import android.view.WindowManager
import android.content.pm.PackageManager
import android.os.Build
import org.json.JSONException
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.provider.Settings
import okhttp3.*

open class API {
    private lateinit var context: Context
    
    interface Calls {\n
END
    @ast.operations.each do |op|
      args = op.args.map { |arg| "#{mangle arg.name}: #{arg.type.kt_native_type}" }
      args << "flag: Int? = null" # TODO make it something like API.DEFAULT and insert error parameter to callback
      args << if !op.return_type.is_a? AST::VoidPrimitiveType 
                "callback: (error: Error?, #{op.return_type.kt_return_type_name}: #{op.return_type.kt_native_type}?) -> Unit" 
              else
                "callback: (error: Error?, result: Boolean?) -> Unit"
              end
      @io << ident(String.build do |io|
        io << "   fun #{mangle op.pretty_name}(#{args.join(", ")}) \n"
      end)
    end
    @io << <<-END
    }

	companion object {
      var instance: API? = null
      fun init(context: Context, useStaging: Boolean) {
            API.useStaging = useStaging
            instance = API().apply { this.context = context }
      }
      
      const val BASE_URL = #{@ast.options.url.inspect}
      var useStaging = false
      private val hexArray = "0123456789abcdef".toCharArray()
      
      var connectionPool = ConnectionPool(100, 45, TimeUnit.SECONDS)
      var client = OkHttpClient.Builder()
            .connectionPool(connectionPool)
            .dispatcher(Dispatcher().apply { maxRequests = 200 ; maxRequestsPerHost = 200 })
            .connectTimeout(45, TimeUnit.SECONDS)
            .build()
      
      var calls = object: Calls { \n
END

    @ast.operations.each do |op|
      args = op.args.map { |arg| "#{mangle arg.name}: #{arg.type.kt_native_type}" }
      args << "flag: Int?" # TODO make it something like API.DEFAULT and insert error parameter to callback
      args << if !op.return_type.is_a? AST::VoidPrimitiveType 
                "callback: (error: Error?, #{op.return_type.kt_return_type_name}: #{op.return_type.kt_native_type}?) -> Unit" 
              else
                "callback: (error: Error?, result: Boolean?) -> Unit"
              end
      @io << ident(String.build do |io|
        io << "     override fun #{mangle op.pretty_name}(#{args.join(", ")}) {\n"
        puts = op.args.map { |arg| "put(\"#{arg.name}\", #{arg.type.kt_encode(arg.name)})"}.join("\n")
        io << if op.args.size > 0
                 "          var bodyArgs = JSONObject().apply { 
                                #{puts}
                            } 
                           makeRequest(\"#{mangle op.pretty_name}\", bodyArgs, callback) \n "
              else 
                  ""
              end
        io << "     }\n"
      end)
    end

    @io << <<-END
      } 

      class Error {
            var type: ErrorType? = null
            var message: String? = null
      }

      fun randomBytesHex(len: Int): String {
          val bytes = ByteArray(len)
          Random().nextBytes(bytes)
          return bytesToHex(bytes)
      }

      private fun bytesToHex(bytes: ByteArray): String {
          val hexChars = CharArray(bytes.size * 2)
          for (j in bytes.indices) {
              val v = bytes[j].toInt() and 0xFF
              hexChars[j * 2] = hexArray[v ushr 4 ]
              hexChars[j * 2 + 1] = hexArray[v and 0x0F]
          }
          return String(hexChars)
      }

      @SuppressLint("HardwareIds")
      @Throws(JSONException::class)
      fun device(): JSONObject =
          JSONObject().apply {
              put("type", "android")
              put("fingerprint", "" + Settings.Secure.getString(instance!!.context.contentResolver, Settings.Secure.ANDROID_ID))
              put("platform", object : JSONObject() {
                  init {
                      put("version", Build.VERSION.RELEASE)
                      put("sdkVersion", Build.VERSION.SDK_INT)
                      put("brand", Build.BRAND)
                      put("model", Build.MODEL)
                  }
              })
              try {
                  put("version", instance!!.context.packageManager.getPackageInfo(instance!!.context.packageName, 0).versionName)
              } catch (e: PackageManager.NameNotFoundException) {
                  put("version", "unknown")
              }

              put("language", language())
              put("screen", object : JSONObject() {
                  init {
                      val manager = instance!!.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                      val display = manager.defaultDisplay
                      val size = Point()
                      display.getSize(size)
                      put("width", size.x)
                      put("height", size.y)
                  }
              })
              val pref = instance!!.context.getSharedPreferences("api", Context.MODE_PRIVATE)
              if (pref.contains("deviceId")) put("id", pref.getString("deviceId", null))
          }


        private fun language(): String {
            val loc = Locale.getDefault()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return loc.toLanguageTag()
            }

            val sep = '-'
            var language = loc.language
            var region = loc.country
            var variant = loc.variant

            if (language == "no" && region == "NO" && variant == "NY") {
                language = "nn"
                region = "NO"
                variant = ""
            }

            if (language.isEmpty() || !language.matches("\\\\[a-zA-Z]{2,8}".toRegex())) {
                language = "und"
            } else if (language == "iw") {
                language = "he"
            } else if (language == "in") {
                language = "id"
            } else if (language == "ji") {
                language = "yi"
            }

            if (!region.matches("\\\\[a-zA-Z]{2}|\\\\[0-9]{3}".toRegex())) {
                region = ""
            }

            if (!variant.matches("\\\\[a-zA-Z0-9]{5,8}|\\\\[0-9]\\\\[a-zA-Z0-9]{3}".toRegex())) {
                variant = ""
            }

            val bcp47Tag = StringBuilder(language)
            if (!region.isEmpty()) {
                bcp47Tag.append(sep).append(region)
            }
            if (!variant.isEmpty()) {
                bcp47Tag.append(sep).append(variant)
            }

            return bcp47Tag.toString()
        }

        private fun <T> makeRequest(functionName: String, bodyArgs: JSONObject, callback: (error: Error?, result: T?) -> Unit, timeoutSeconds: Int = 15) {
            try {

                val body = JSONObject().apply {
                    put("id", randomBytesHex(8))
                    put("device", device())
                    put("name", functionName)
                    put("args", bodyArgs)
                    put("staging", API.useStaging)
                }

                val request = Request.Builder()
                        .url("https://$BASE_URL${if (useStaging) "-staging" else ""}")
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body.toString()))
                        .build()
                client.newCall(request)
            } catch (e: JSONException) {
                e.printStackTrace()
                callback(Error(), null)
            }
        }
    }
}
END
  end
end

Target.register(KtAndroidTarget, target_name: "kt_android")
