
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

open class API {
    interface Calls {
       fun getFooString(var1: String, flag: Int? = null, callback: (value: String?) -> Unit) 
       fun ping(flag: Int? = null, callback: (value: String?) -> Unit) 
       fun setPushToken(token: String, flag: Int? = null, callback: () -> Unit) 
    }

	companion object {
      var calls = object: Calls { 
         override fun getFooString(var1: String, flag: Int?, callback: (value: String?) -> Unit) {
             var json = JSONObject().apply { put("vra", var1) }  
         }
         override fun ping(flag: Int?, callback: (value: String?) -> Unit) {
         }
         override fun setPushToken(token: String, flag: Int?, callback: () -> Unit) {
             var json = JSONObject().apply { put("vra", token) }  
         }
      } 
    }
}