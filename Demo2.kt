
import android.location.Location
import android.util.Base64
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit

open class API {
    interface Calls {
       fun getFooString(var1: String, flag: Int? = null, callback: (value: String?) -> Unit) 
       fun getFooBase64(var1: String, flag: Int? = null, callback: (value: String?) -> Unit) 
       fun getFooCnpj(var1: String, flag: Int? = null, callback: (cnpj: String?) -> Unit) 
       fun getFooCep(var1: String, flag: Int? = null, callback: (cep: String?) -> Unit) 
       fun getFooCpf(var1: String, flag: Int? = null, callback: (cpf: String?) -> Unit) 
       fun getFooDate(var1: Calendar, flag: Int? = null, callback: (date: Calendar?) -> Unit) 
       fun getFooEmail(var1: String, flag: Int? = null, callback: (email: String?) -> Unit) 
       fun getFooFloat(var1: Float, flag: Int? = null, callback: (value: Float?) -> Unit) 
       fun getFooInt(var1: Int, flag: Int? = null, callback: (value: Int?) -> Unit) 
       fun getFooUInt(var1: Int, flag: Int? = null, callback: (value: Int?) -> Unit) 
       fun getFooLocation(var1: Location, flag: Int? = null, callback: (latlng: Location?) -> Unit) 
       fun getFooBytes(var1: ByteArray, flag: Int? = null, callback: (value: ByteArray?) -> Unit) 
       fun getFooDateTime(var1: Calendar, flag: Int? = null, callback: (datetime: Calendar?) -> Unit) 
       fun getFooMoney(var1: Int, flag: Int? = null, callback: (money: Int?) -> Unit) 
       fun getFooPhone(var1: String, flag: Int? = null, callback: (phone: String?) -> Unit) 
       fun getFooUrl(var1: String, flag: Int? = null, callback: (url: String?) -> Unit) 
       fun getFooUuid(var1: String, flag: Int? = null, callback: (uuid: String?) -> Unit) 
       fun getFooXml(var1: String, flag: Int? = null, callback: (xml: String?) -> Unit) 
       fun ping(flag: Int? = null, callback: (value: String?) -> Unit) 
       fun setPushToken(token: String, flag: Int? = null, callback: () -> Unit) 
    }

	companion object {
      const val BASE_URL = ""

      var connectionPool = ConnectionPool(100, 45, TimeUnit.SECONDS)
      var client = OkHttpClient.Builder()
            .connectionPool(connectionPool)
            .dispatcher(Dispatcher().apply { maxRequests = 200 ; maxRequestsPerHost = 200 })
            .connectTimeout(45, TimeUnit.SECONDS)
            .build()
      
      var calls = object: Calls { 
         override fun getFooString(var1: String, flag: Int?, callback: (value: String?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun getFooBase64(var1: String, flag: Int?, callback: (value: String?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun getFooCnpj(var1: String, flag: Int?, callback: (cnpj: String?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1.replace(Regex("/[^0-9]/g"), "").padStart(14, 0.toChar()))
                                }  

                               var1.replace(Regex("/(..)(...)(...)(....)(..)/"), "$1.$2.$3/$4-$5")  
         }
         override fun getFooCep(var1: String, flag: Int?, callback: (cep: String?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1.replace(Regex("/[^0-9]/g"), "").padStart(8, 0.toChar()))
                                }  

                               var1.replace(Regex("/(..)(...)(...)/"), "$1.$2-$3")  
         }
         override fun getFooCpf(var1: String, flag: Int?, callback: (cpf: String?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1.replace(Regex("/[^0-9]/g"), "").padStart(11, 0.toChar()))
                                }  

                               var1.replace(Regex("/(...)(...)(...)(..)/"), "$1.$2.$3-$4")  
         }
         override fun getFooDate(var1: Calendar, flag: Int?, callback: (date: Calendar?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", Calendar.getInstance().apply { time = SimpleDateFormat("yyyy-MM-dd").parse(var1) })
                                }  

                               SimpleDateFormat("yyyy-MM-dd").format(var1)  
         }
         override fun getFooEmail(var1: String, flag: Int?, callback: (email: String?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun getFooFloat(var1: Float, flag: Int?, callback: (value: Float?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun getFooInt(var1: Int, flag: Int?, callback: (value: Int?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun getFooUInt(var1: Int, flag: Int?, callback: (value: Int?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun getFooLocation(var1: Location, flag: Int?, callback: (latlng: Location?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", { lat: var1.latitude, lng: var1.longitude })
                                }  

                               Location("").apply { latitude = var1.  ; longitude = var1.lng }  
         }
         override fun getFooBytes(var1: ByteArray, flag: Int?, callback: (value: ByteArray?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", Base64.encodeToString(var1, Base64.DEFAULT))
                                }  

                               Base64.decode(var1, Base64.DEFAULT)  
         }
         override fun getFooDateTime(var1: Calendar, flag: Int?, callback: (datetime: Calendar?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", Calendar.getInstance().apply { time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(var1) })
                                }  

                               SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(var1)  
         }
         override fun getFooMoney(var1: Int, flag: Int?, callback: (money: Int?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun getFooPhone(var1: String, flag: Int?, callback: (phone: String?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun getFooUrl(var1: String, flag: Int?, callback: (url: String?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun getFooUuid(var1: String, flag: Int?, callback: (uuid: String?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun getFooXml(var1: String, flag: Int?, callback: (xml: String?) -> Unit) {
              var json = JSONObject().apply { 
                                    put("var1", var1)
                                }  

                               var1  
         }
         override fun ping(flag: Int?, callback: (value: String?) -> Unit) {
         }
         override fun setPushToken(token: String, flag: Int?, callback: () -> Unit) {
              var json = JSONObject().apply { 
                                    put("token", token)
                                }  

                               token  
         }
      } 
    }
}