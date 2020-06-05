require "./target"
require "random/secure"

class KtJetPackAndroidTarget < Target
  def mangle(ident)
    if %w[
         in out as as? break class continue do else false
         for fun if in !in interface is !is null object
         package return super this throw true try typealias
         val var when while by catch constructor delegate
         dynamic field file finally get import init param
         property receiver set setparam in where actual abstract
         annotation companion const crossinline data enum expect
         external final infix inline inner internal lateinit noinline
         open operator out override private protected public reified
         sealed suspend tailrec vararg Double Float Long Int Short Byte
       ].includes? ident
      "_" + ident
    else
      ident
    end
  end

  def gen
    @io << <<-END

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
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.io.Serializable
import org.json.JSONArray
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.InvalidObjectException
import kotlin.concurrent.timerTask
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@Suppress("DeferredIsResult", "unused")
@SuppressLint("SimpleDateFormat", "StaticFieldLeak")
object API {
    
    interface Calls {\n
END
    @ast.operations.each do |op|
      args = op.args.map { |arg| "#{mangle arg.name}: #{arg.type.kt_native_type}" }
      returnType = if !op.return_type.is_a? AST::VoidPrimitiveType
        "#{op.return_type.kt_native_type}?"
      else
        "Boolean?"
      end
      @io << ident(String.build do |io|
        io << "   fun #{mangle op.pretty_name}(#{args.join(", ")}): Flow<Response<#{returnType}>> = flow<Response<#{returnType}>> { \n"
        io << "       emit(Response(Error(ErrorType.Fatal, \"Not Implemented\"), null)) \n"
        io << "   }.flowOn(IO)\n"
      end)
    end
    @io << <<-END
    }

    lateinit var context: Context
    private val gson = Gson()
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",Locale.US).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var isForcedBaseUrl = false    
    var DEFAULT_TIMEOUT_SECONDS = 15
    private var BASE_URL = #{@ast.options.url.inspect}
    var useStaging = false
    private val hexArray = "0123456789abcdef".toCharArray()

    fun init(appContext: Context, useStaging: Boolean, baseUrl: String? = null, timeoutSeconds: Int? = null) {
          API.useStaging = useStaging
          context = appContext 
          if (timeoutSeconds != null)
            DEFAULT_TIMEOUT_SECONDS = timeoutSeconds

          if (baseUrl != null) {
              BASE_URL = baseUrl  
              isForcedBaseUrl = true
          } 
    }
    
    var connectionPool = ConnectionPool(100, 80, TimeUnit.SECONDS)
    var client = OkHttpClient.Builder()
          .connectionPool(connectionPool)
          .dispatcher(Dispatcher().apply { maxRequests = 200 ; maxRequestsPerHost = 200 })
          .connectTimeout(80, TimeUnit.SECONDS)
          .readTimeout(80, TimeUnit.SECONDS)
          .retryOnConnectionFailure(false)
          .build()
    
    class Error(
        var type: ErrorType? = null,
        var message: String? = null
    )

    private data class InternalResponse(val error: Error?, val data: JSONObject?)
    
    data class Response<T>(val error: Error?, val data: T?)\n

END

    @ast.struct_types.each do |t|
      t.fields.each do |f|
        f.name = mangle f.name
      end
      @io << ident(t.kt_definition)
      @io << "\n\n"
    end

    @ast.enum_types.each do |e|
      @io << ident(e.kt_definition)
      @io << "\n\n"
    end

    @io << "var calls = object: Calls { \n"
    @ast.operations.each do |op|
      args = op.args.map { |arg| "#{mangle arg.name}: #{arg.type.kt_native_type}" }
      returnType = if !op.return_type.is_a? AST::VoidPrimitiveType
        "#{op.return_type.kt_native_type}?"
      else
        "Boolean?"
      end
      @io << ident(String.build do |io|
        io << "     override fun #{mangle op.pretty_name}(#{args.join(", ")}): Flow<Response<#{returnType}>> = flow<Response<#{returnType}>> { \n"
        puts = op.args.map { |arg| "put(\"#{arg.name}\", #{arg.type.kt_encode(mangle(arg.name), nil)})" }.join("\n")
        bodyParameter = "null"
        if op.args.size > 0
          bodyParameter = "bodyArgs"
          io << "          val #{bodyParameter} = JSONObject().apply {\n"
          io << ident ident ident puts
          io << "\n"
          io << "          }\n"
        else
          ""
        end
        io << "          val r = makeRequest(\"#{mangle op.pretty_name}\", #{bodyParameter})\n"
        io << "          val responseData = if (r.data != null) {\n"
        responseExpression = ""
        if op.return_type.is_a? AST::TypeReference
          responseExpression = "            #{op.return_type.kt_decode("r.data", nil)}\n"
        elsif op.return_type.is_a? AST::ArrayType
          responseExpression = "            #{op.return_type.kt_decode("r.data", nil)}\n"
        elsif op.return_type.is_a? AST::OptionalType
          responseExpression = "            #{op.return_type.kt_decode("r.data", "\"result\"")}\n"
        elsif op.return_type.is_a? AST::VoidPrimitiveType
          responseExpression = "            r.error == null\n"   
        else
          responseExpression = "            #{op.return_type.kt_decode("r.data", "\"result\"")}\n"
        end
        io << ident responseExpression
        io << "          } else null\n"
        io << "         emit(Response(r.error, responseData))\n"
        io << "     }.flowOn(IO)\n"
        io << "\n"
      end)
    end

    @io << <<-END
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
      private fun device(): JSONObject =
          JSONObject().apply {
              put("type", "android")
              put("fingerprint", "" + Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID))
              put("platform", JSONObject().apply {
                    put("version", Build.VERSION.RELEASE)
                    put("sdkVersion", Build.VERSION.SDK_INT)
                    put("brand", Build.BRAND)
                    put("model", Build.MODEL)
              })
              try {
                  put("version", context.packageManager.getPackageInfo(context.packageName, 0).versionName)
              } catch (e: PackageManager.NameNotFoundException) {
                  put("version", "unknown")
              }

              put("language", language())
              put("screen", JSONObject().apply {
                    val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    val display = manager.defaultDisplay
                    val size = Point()
                    display.getSize(size)
                    put("width", size.x)
                    put("height", size.y)
              })
              val pref = context.getSharedPreferences("api", Context.MODE_PRIVATE)
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

        private suspend fun makeRequest(functionName: String, bodyArgs: JSONObject?, timeoutSeconds: Int = DEFAULT_TIMEOUT_SECONDS): InternalResponse = suspendCoroutine { continuation ->
            try {
                val body = JSONObject().apply {
                    put("id", randomBytesHex(8))
                    put("device", device())
                    put("name", functionName)
                    put("args", bodyArgs ?: JSONObject())
                    put("staging", API.useStaging)
                }

                val request = Request.Builder()
                        .url(
                          if (isForcedBaseUrl)
                            "$BASE_URL/$functionName"
                          else    
                            "https://$BASE_URL${if (useStaging) "-staging" else ""}/$functionName"
                        )
                        .post(body.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()))
                        .build()

                val httpTimer = Timer()
                val call = client.newCall(request)
                call.enqueue(object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        httpTimer.cancel()
                        e.printStackTrace()
                        continuation.resume(InternalResponse(Error(ErrorType.Fatal, e.message ?: "Chamada falhou sem mensagem de erro!"), null))
                    }

                    override fun onResponse(call: Call, response: okhttp3.Response) {
                        httpTimer.cancel()
                        if (response.code == 502) {
                            continuation.resume(InternalResponse(Error(ErrorType.Fatal, "Erro Fatal (502) - Tente novamente"), null))
                            return
                        }

                        val responseBody = try {
                            val stringBody = response.body?.string() ?: throw InvalidObjectException("")
                            JSONObject(stringBody)
                        } catch (e: Exception) {
                            continuation.resume(InternalResponse(Error(ErrorType.Fatal, "502 - Tente novamente"), null))
                            return
                        }

                        val pref = context.getSharedPreferences("api", Context.MODE_PRIVATE)
                        pref.edit().putString("deviceId", responseBody.getString("deviceId")).apply()

                        if (!responseBody.getBoolean("ok")) {
                            val jsonError = responseBody.getJSONObject("error")
                            //TODO Fetch correct error type
                            val error = Error(ErrorType.valueOf(jsonError.getString("type")), jsonError.getString("message"))
                            Log.e("API Error", jsonError.getString("type") + " - " + error.message)
                            continuation.resume(InternalResponse(error, null))
                        } else {   
                            continuation.resume(InternalResponse(null, responseBody))
                        }
                    }
                })

                httpTimer.schedule(
                  timerTask {
                      call.cancel()
                      val error = Error(ErrorType.Connection, "Timeout")
                      continuation.resume(InternalResponse(error, null))
                  },
                  timeoutSeconds * 1000L
                )
            } catch (e: JSONException) {
                e.printStackTrace()
                continuation.resume(InternalResponse(Error(ErrorType.Fatal, e.message ?: "Erro ao parsear json"), null))
            }
        }
    }
END
  end
end

Target.register(KtJetPackAndroidTarget, target_name: "kt_jetpack_android")
