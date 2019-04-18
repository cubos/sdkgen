
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
import android.os.Handler
import android.os.HandlerThread
import android.provider.Settings
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.io.Serializable
import org.json.JSONArray

open class API {
    
    interface Calls {
       fun getUser(user: User, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun getFooString(var1: String, flag: Int? = null, callback: (error: Error?, value: String?) -> Unit) 
       fun getFooBase64(var1: String, flag: Int? = null, callback: (error: Error?, String: String?) -> Unit) 
       fun getFooCnpj(var1: String, flag: Int? = null, callback: (error: Error?, cnpj: String?) -> Unit) 
       fun getFooCep(var1: String, flag: Int? = null, callback: (error: Error?, cep: String?) -> Unit) 
       fun getFooCpf(var1: String, flag: Int? = null, callback: (error: Error?, cpf: String?) -> Unit) 
       fun getFooDate(var1: Calendar, flag: Int? = null, callback: (error: Error?, datetime: Calendar?) -> Unit) 
       fun getFooEmail(var1: String, flag: Int? = null, callback: (error: Error?, email: String?) -> Unit) 
       fun getFooFloat(var1: Float, flag: Int? = null, callback: (error: Error?, value: Float?) -> Unit) 
       fun getFooInt(var1: Int, flag: Int? = null, callback: (error: Error?, value: Int?) -> Unit) 
       fun getFooUInt(var1: Int, flag: Int? = null, callback: (error: Error?, value: Int?) -> Unit) 
       fun getFooBytes(var1: ByteArray, flag: Int? = null, callback: (error: Error?, value: ByteArray?) -> Unit) 
       fun getFooDateTime(var1: Calendar, flag: Int? = null, callback: (error: Error?, datetime: Calendar?) -> Unit) 
       fun getFooMoney(var1: Int, flag: Int? = null, callback: (error: Error?, money: Int?) -> Unit) 
       fun getFooPhone(var1: String, flag: Int? = null, callback: (error: Error?, phone: String?) -> Unit) 
       fun getFooUrl(var1: String, flag: Int? = null, callback: (error: Error?, url: String?) -> Unit) 
       fun getFooUuid(var1: String, flag: Int? = null, callback: (error: Error?, uuid: String?) -> Unit) 
       fun getFooXml(var1: String, flag: Int? = null, callback: (error: Error?, xml: String?) -> Unit) 
       fun ping(flag: Int? = null, callback: (error: Error?, value: String?) -> Unit) 
       fun setPushToken(token: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
    }

	companion object {
      lateinit var context: Context
      fun init(appContext: Context, useStaging: Boolean) {
            API.useStaging = useStaging
            context = appContext 
      }
      
      const val BASE_URL = ""
      var useStaging = false
      private val hexArray = "0123456789abcdef".toCharArray()
      
      var connectionPool = ConnectionPool(100, 45, TimeUnit.SECONDS)
      var client = OkHttpClient.Builder()
            .connectionPool(connectionPool)
            .dispatcher(Dispatcher().apply { maxRequests = 200 ; maxRequestsPerHost = 200 })
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
      
      class Error(
          var type: ErrorType? = null,
          var message: String? = null
      )
    }

    data class FooType(
        var fooStr: String, 
        var fooInt: Int, 
        var fooFloat: Float, 
        var fooEnum: FooTypeFooEnum 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FooType {
                return Gson().fromJson(jsonToParse.toString(), FooType::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FooType> {
                return ArrayList(Gson().fromJson(jsonArrayToParse.toString(), Array<FooType>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(Gson().toJson(this))    }}

    data class FooName(
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FooName {
                return Gson().fromJson(jsonToParse.toString(), FooName::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FooName> {
                return ArrayList(Gson().fromJson(jsonArrayToParse.toString(), Array<FooName>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(Gson().toJson(this))    }}

    data class User(
        var password: String?, 
        var avatar: ByteArray?, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): User {
                return Gson().fromJson(jsonToParse.toString(), User::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<User> {
                return ArrayList(Gson().fromJson(jsonArrayToParse.toString(), Array<User>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(Gson().toJson(this))    }}

    enum class FooTypeFooEnum {
    eu, 
    voce
    }

    enum class ErrorType {
    Fatal, 
    Connection, 
    Serialization
    }

var calls = object: Calls { 
         override fun getUser(user: User, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("user", JSONObject().apply {
        put("password", user?.password)
        put("avatar", user?.Base64.encodeToString(avatar, Base64.DEFAULT))
        put("name", user)
    }
    )
              }
              makeRequest("getUser", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = User(
        password = json?..getString("password"),
        avatar = json?.Base64.decode(.getString("avatar"), Base64.DEFAULT),
        name = json?..getString("name")
    )
                   callback(null, response)              }
              })
         }
         override fun getFooString(var1: String, flag: Int?, callback: (error: Error?, value: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooString", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getString("result")
                   callback(null, response)              }
              })
         }
         override fun getFooBase64(var1: String, flag: Int?, callback: (error: Error?, String: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooBase64", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = String(Base64.decode(json?.getString("result"), Base64.DEFAULT))
                   callback(null, response)              }
              })
         }
         override fun getFooCnpj(var1: String, flag: Int?, callback: (error: Error?, cnpj: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1.replace(Regex("/[^0-9]/g"), "").padStart(14, 0.toChar()))
              }
              makeRequest("getFooCnpj", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getString("result")?.replace(Regex("/(..)(...)(...)(....)(..)/"), "$1.$2.$3/$4-$5")
                   callback(null, response)              }
              })
         }
         override fun getFooCep(var1: String, flag: Int?, callback: (error: Error?, cep: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1.replace(Regex("/[^0-9]/g"), "").padStart(8, 0.toChar()))
              }
              makeRequest("getFooCep", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getString("result")?.replace(Regex("/(..)(...)(...)/"), "$1.$2-$3")
                   callback(null, response)              }
              })
         }
         override fun getFooCpf(var1: String, flag: Int?, callback: (error: Error?, cpf: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1.replace(Regex("/[^0-9]/g"), "").padStart(11, 0.toChar()))
              }
              makeRequest("getFooCpf", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getString("result")?.replace(Regex("/(...)(...)(...)(..)/"), "$1.$2.$3-$4")
                   callback(null, response)              }
              })
         }
         override fun getFooDate(var1: Calendar, flag: Int?, callback: (error: Error?, datetime: Calendar?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(var1))
              }
              makeRequest("getFooDate", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = Calendar.getInstance().apply { time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json?.getString("result")) }
                   callback(null, response)              }
              })
         }
         override fun getFooEmail(var1: String, flag: Int?, callback: (error: Error?, email: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooEmail", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getString("result")
                   callback(null, response)              }
              })
         }
         override fun getFooFloat(var1: Float, flag: Int?, callback: (error: Error?, value: Float?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooFloat", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getDouble("result")?.toFloat()
                   callback(null, response)              }
              })
         }
         override fun getFooInt(var1: Int, flag: Int?, callback: (error: Error?, value: Int?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooInt", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getInt("result")
                   callback(null, response)              }
              })
         }
         override fun getFooUInt(var1: Int, flag: Int?, callback: (error: Error?, value: Int?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooUInt", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getInt("result")
                   callback(null, response)              }
              })
         }
         override fun getFooBytes(var1: ByteArray, flag: Int?, callback: (error: Error?, value: ByteArray?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", Base64.encodeToString(var1, Base64.DEFAULT))
              }
              makeRequest("getFooBytes", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = Base64.decode(json?.getString("result"), Base64.DEFAULT)
                   callback(null, response)              }
              })
         }
         override fun getFooDateTime(var1: Calendar, flag: Int?, callback: (error: Error?, datetime: Calendar?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(var1))
              }
              makeRequest("getFooDateTime", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = Calendar.getInstance().apply { time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(json?.getString("result")) }
                   callback(null, response)              }
              })
         }
         override fun getFooMoney(var1: Int, flag: Int?, callback: (error: Error?, money: Int?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooMoney", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getInt("result")
                   callback(null, response)              }
              })
         }
         override fun getFooPhone(var1: String, flag: Int?, callback: (error: Error?, phone: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooPhone", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getString("result")
                   callback(null, response)              }
              })
         }
         override fun getFooUrl(var1: String, flag: Int?, callback: (error: Error?, url: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooUrl", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getString("result")
                   callback(null, response)              }
              })
         }
         override fun getFooUuid(var1: String, flag: Int?, callback: (error: Error?, uuid: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooUuid", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getString("result")
                   callback(null, response)              }
              })
         }
         override fun getFooXml(var1: String, flag: Int?, callback: (error: Error?, xml: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("var1", var1)
              }
              makeRequest("getFooXml", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = json?.getString("result")
                   callback(null, response)              }
              })
         }
         override fun ping(flag: Int?, callback: (error: Error?, value: String?) -> Unit) {
         }
         override fun setPushToken(token: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("token", token)
              }
              makeRequest("setPushToken", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
                   val response = null
                   callback(null, response)              }
              })
         }
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

            if (language.isEmpty() || !language.matches("\\[a-zA-Z]{2,8}".toRegex())) {
                language = "und"
            } else if (language == "iw") {
                language = "he"
            } else if (language == "in") {
                language = "id"
            } else if (language == "ji") {
                language = "yi"
            }

            if (!region.matches("\\[a-zA-Z]{2}|\\[0-9]{3}".toRegex())) {
                region = ""
            }

            if (!variant.matches("\\[a-zA-Z0-9]{5,8}|\\[0-9]\\[a-zA-Z0-9]{3}".toRegex())) {
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

        private inline fun makeRequest(functionName: String, bodyArgs: JSONObject, crossinline callback: (error: Error?, result: JSONObject?) -> Unit, timeoutSeconds: Int = 15) {
            try {
                val body = JSONObject().apply {
                    put("id", randomBytesHex(8))
                    put("device", device())
                    put("name", functionName)
                    put("args", bodyArgs)
                    put("staging", API.useStaging)
                }

                val request = Request.Builder()
                        .url("https://$BASE_URL${if (useStaging) "-staging" else ""}/$functionName")
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body.toString()))
                        .build()
                 client.newCall(request).enqueue(object: Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        e?.printStackTrace()
                        callback(Error(ErrorType.Fatal, e?.message ?: "Chamada falhou sem mensagem de erro!"), null)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response == null || response.code() == 502) {
                            callback(Error(ErrorType.Fatal, "Erro Fatal (502) - Tente novamente"), null)
                        }

                        val stringBody = response?.body()?.string()
                        //TODO Use kotlin coroutine instead
                        val handlerThread = HandlerThread("JsonHandleThread")
                        Handler(handlerThread.looper).post {
                            val responseBody = JSONObject(stringBody)

                            val pref = context.getSharedPreferences("api", Context.MODE_PRIVATE)
                            pref.edit().putString("deviceId", responseBody.getString("deviceId")).apply()

                            if (!responseBody.getBoolean("ok")) {
                                val jsonError = responseBody.getJSONObject("error")
                                //TODO Fetch correct error type
                                val error = Error(ErrorType.valueOf(jsonError.getString("type")), jsonError.getString("message"))
                                Log.e("API Error", jsonError.getString("type") + " - " + error.message);
                                callback(error, null)
                            } else {
                                callback(null, responseBody)
                            }
                        }
                    }
                })
            } catch (e: JSONException) {
                e.printStackTrace()
                callback(Error(ErrorType.Fatal, e.message ?: "Erro ao parsear json"), null)
            }
        }
    }