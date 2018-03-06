
open class API {
    interface Calls {
       fun getFoo(var1: String, var2: String, var3: Boolean, var4: String, var5: String, flag: Int? = null, callback: (fooType: FooType) -> Unit) 
       fun getFooCnpj(var1: String, var2: String, var3: Boolean, var4: String, var5: String, flag: Int? = null, callback: (cnpj: String) -> Unit) 
       fun getFooCep(var1: String, var2: String, var3: Boolean, var4: String, flag: Int? = null, callback: (cep: String) -> Unit) 
       fun getFooCpf(var1: String, flag: Int? = null, callback: (cpf: String) -> Unit) 
       fun getFooDate(var2: Calendar, flag: Int? = null, callback: (date: Calendar) -> Unit) 
       fun ping(flag: Int? = null, callback: (string: String) -> Unit) 
       fun setPushToken(token: String, flag: Int? = null, callback: () -> Unit) 
    }

	companion object {
      var calls = object: Calls { 
         override fun getFoo(var1: String, var2: String, var3: Boolean, var4: String, var5: String, flag: Int? = null, callback: (fooType: FooType) -> Unit) {

         }
         override fun getFooCnpj(var1: String, var2: String, var3: Boolean, var4: String, var5: String, flag: Int? = null, callback: (cnpj: String) -> Unit) {

         }
         override fun getFooCep(var1: String, var2: String, var3: Boolean, var4: String, flag: Int? = null, callback: (cep: String) -> Unit) {

         }
         override fun getFooCpf(var1: String, flag: Int? = null, callback: (cpf: String) -> Unit) {

         }
         override fun getFooDate(var2: Calendar, flag: Int? = null, callback: (date: Calendar) -> Unit) {

         }
         override fun ping(flag: Int? = null, callback: (string: String) -> Unit) {

         }
         override fun setPushToken(token: String, flag: Int? = null, callback: () -> Unit) {

         }
      } 
    }
}


