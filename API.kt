
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
import android.provider.Settings
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.io.Serializable
import org.json.JSONArray
import com.google.gson.reflect.TypeToken
import android.os.Looper
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull

@ExperimentalCoroutinesApi
fun <T> Deferred<T>.result(callback: (error: Throwable?, response: T?) -> Unit) {
    try {
        this.invokeOnCompletion { cause ->
            if (this.getCompletionExceptionOrNull() != null)
                throw this.getCompletionExceptionOrNull()!!

            callback(cause, if (this.isCompleted) this.getCompleted() else null)
        }
    } catch (e: Throwable) {
        callback(e, if (this.isCompleted) this.getCompleted() else null)
    }
}

@SuppressLint("SimpleDateFormat", "StaticFieldLeak")
object API {
    
    interface Calls {
       fun getAllPermissions(): Deferred<LiveData<Response<ArrayList<Permission>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Permission>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAllRoles(): Deferred<LiveData<Response<ArrayList<Role>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Role>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteRole(slug: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createRole(name: String, permissions: ArrayList<String>): Deferred<LiveData<Response<Role?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Role?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun updateRole(slug: String, name: String, permissions: ArrayList<String>): Deferred<LiveData<Response<Role?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Role?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAlerts(placeId: String, skip: Int, limit: Int): Deferred<LiveData<Response<ArrayList<DashboardAlert>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<DashboardAlert>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun markAlertAsRead(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAnnounces(eventId: String): Deferred<LiveData<Response<ArrayList<Announce>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Announce>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createAnnounce(announce: Announce, eventId: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editAnnounce(announce: Announce): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun removeAnnounce(announceId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeConfig(placeId: String): Deferred<LiveData<Response<GetBackofficeConfig?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<GetBackofficeConfig?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeAccount(id: String, placeId: String): Deferred<LiveData<Response<BackofficeAccount?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeAccount?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeAccounts(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeAccount>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeAccount>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeAccount(placeId: String, account: BackofficeNewAccount): Deferred<LiveData<Response<BackofficeAccount?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeAccount?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeAccount(placeId: String, id: String, account: BackofficeNewAccount): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeAccount(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBill(id: String, placeId: String): Deferred<LiveData<Response<BackofficeBill?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeBill?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBills(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<ArrayList<BackofficeBill>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeBill>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeBill(placeId: String, bill: BackofficeNewBill, attachment: BackofficeAttachment?): Deferred<LiveData<Response<BackofficeBill?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeBill?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeBill(placeId: String, id: String, bill: BackofficeNewBill): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun payBackofficeBill(placeId: String, id: String, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeBillAttachment(placeId: String, id: String, attachment: BackofficeAttachment?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeBill(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun exportBackofficeBills(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun parseBackofficeBillsToImport(placeId: String, _file: ByteArray): Deferred<LiveData<Response<ArrayList<BackofficeNewBill>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeNewBill>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun importBackofficeBills(placeId: String, _file: ByteArray): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeBillPlanCategory(name: String, parentId: String?): Deferred<LiveData<Response<BackofficeBillPlanCategoryBase?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeBillPlanCategoryBase?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeBillPlanCategory(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillPlanCategory(id: String): Deferred<LiveData<Response<BackofficeBillPlanCategoryBase?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeBillPlanCategoryBase?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillPlans(): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillPlansTreeLeafs(): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategoryBase>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeBillPlanCategoryBase>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillPlansTreeLeafsByLevel(level: Int): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategoryBase>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeBillPlanCategoryBase>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeBillPlanCategoryName(id: String, name: String): Deferred<LiveData<Response<BackofficeBillPlanCategoryBase?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeBillPlanCategoryBase?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeBillPlanCategoryParent(id: String, newParentId: String?): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeSuggestedBillPlan(): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun exportBackofficeBillPlan(): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun importBackofficeBillPlan(_file: ByteArray): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeBillPlan(): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillType(id: String, placeId: String): Deferred<LiveData<Response<BackofficeBillType?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeBillType?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillTypes(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeBillType>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeBillType>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeBillType(placeId: String, billType: BackofficeNewBillType): Deferred<LiveData<Response<BackofficeBillType?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeBillType?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeBillType(placeId: String, id: String, billType: BackofficeNewBillType): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeBillType(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeCategory(id: String, placeId: String): Deferred<LiveData<Response<BackofficeCategory?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeCategory?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeCategories(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeCategory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeCategory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeCategory(placeId: String, category: BackofficeNewCategory): Deferred<LiveData<Response<BackofficeCategory?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeCategory?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeCategory(placeId: String, id: String, category: BackofficeNewCategory): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeCategory(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductsCmv(placeId: String, productIds: ArrayList<String>, since: Calendar, until: Calendar, shouldUseLastInvoice: Boolean): Deferred<LiveData<Response<ArrayList<BackofficeProductCmv>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeProductCmv>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductsCmvAvg(placeId: String, productIds: ArrayList<String>, since: Calendar, until: Calendar, shouldUseLastInvoice: Boolean): Deferred<LiveData<Response<ArrayList<BackofficeProductCmv>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeProductCmv>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeCurrentProductsCmv(placeId: String, productIds: ArrayList<String>, shouldUseLastInvoice: Boolean): Deferred<LiveData<Response<ArrayList<BackofficeProductCmv>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeProductCmv>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductsSimpleCurrentCmv(placeId: String, productIds: ArrayList<String>, shouldUseLastInvoice: Boolean): Deferred<LiveData<Response<ArrayList<BackofficeProductSimpleCmv>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeProductSimpleCmv>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductsSimpleCmv(placeId: String, productIds: ArrayList<String>, since: Calendar, until: Calendar, shouldUseLastInvoice: Boolean): Deferred<LiveData<Response<ArrayList<BackofficeProductSimpleCmv>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeProductSimpleCmv>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeCostCenter(id: String, placeId: String): Deferred<LiveData<Response<BackofficeCostCenter?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeCostCenter?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeCostCenters(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeCostCenter>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeCostCenter>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeCostCenter(placeId: String, costCenter: BackofficeNewCostCenter): Deferred<LiveData<Response<BackofficeCostCenter?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeCostCenter?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeCostCenter(placeId: String, id: String, costCenter: BackofficeNewCostCenter): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeCostCenter(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun insertBackofficeFocusCompany(placeId: String, cnpj: String, token: String, lastInvoiceVersion: Int?): Deferred<LiveData<Response<BackofficeFocusCompany?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeFocusCompany?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun updateBackofficeFocusCompany(placeId: String, id: String, token: String): Deferred<LiveData<Response<BackofficeFocusCompany?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeFocusCompany?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeFocusCompany(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeFocusCompanies(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeFocusCompany>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeFocusCompany>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeFocusCompany(id: String, placeId: String): Deferred<LiveData<Response<BackofficeFocusCompany?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeFocusCompany?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeFocusInvoices(placeId: String, focusCompanyId: String): Deferred<LiveData<Response<ArrayList<BackofficeFocusInvoice>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeFocusInvoice>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeInventory(placeId: String, inventory: BackofficeNewInventory, shouldOverlapOldInventory: Boolean): Deferred<LiveData<Response<BackofficeInventory?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeInventory?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeInventories(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<BackofficeInventory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeInventory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeOpenedInventories(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeInventory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeInventory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun addBackofficeStorageToInventory(placeId: String, inventoryId: String, storageIds: ArrayList<String>): Deferred<LiveData<Response<BackofficeInventory?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeInventory?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeInventoryProductsAtStorage(placeId: String, inventoryId: String, storageId: String): Deferred<LiveData<Response<ArrayList<BackofficeInventoryProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeInventoryProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeInventoryProducts(placeId: String, inventoryId: String): Deferred<LiveData<Response<ArrayList<BackofficeInventoryProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeInventoryProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun updateBackofficeInventoryProduct(placeId: String, inventoryId: String, authorId: String, products: ArrayList<BackofficeInventoryProduct>): Deferred<LiveData<Response<ArrayList<BackofficeInventoryProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeInventoryProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun closeBackofficeInventory(placeId: String, authorId: String, inventoryId: String, alterUnitValue: Boolean): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun cancelBackofficeInventory(placeId: String, authorId: String, inventoryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeImportedInvoices(placeId: String, since: Calendar?, until: Calendar?): Deferred<LiveData<Response<ArrayList<BackofficeImportedInvoice>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeImportedInvoice>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun importBackofficeInvoice(placeId: String, storageId: String?, xml: ByteArray): Deferred<LiveData<Response<BackofficeImportedInvoice?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeImportedInvoice?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeManualInvoice(placeId: String, manualInvoice: BackofficeNewManualInvoice): Deferred<LiveData<Response<BackofficeImportedInvoice?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeImportedInvoice?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeManualInvoiceDetails(placeId: String, invoiceId: String, invoiceInfo: BackofficeNewManualInvoice): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun invalidateBackofficeInvoiceProduct(placeId: String, invoiceId: String, entryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductLastInvoiceDetails(placeId: String, associatedProductId: String): Deferred<LiveData<Response<BackofficeProductLastInvoiceDetails??>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeProductLastInvoiceDetails??>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeInvoiceProducts(placeId: String, invoiceId: String): Deferred<LiveData<Response<ArrayList<BackofficeImportedInvoiceProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeImportedInvoiceProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeInvoiceBill(placeId: String, invoiceId: String, createBill: Boolean, accountId: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeInvoiceProduct(placeId: String, invoiceId: String, entryId: String, skipped: Boolean, productId: String?, unitValue: Int?, unitMultiplier: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeInvoiceSupplier(placeId: String, invoiceId: String, skipped: Boolean, supplierId: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun upsertBackofficeManualInvoiceProduct(placeId: String, invoiceId: String, products: ArrayList<BackofficeUpsertManualProduct>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun finishBackofficeInvoiceImport(invoiceId: String, skipPendings: Boolean, productsBillPlanCategories: ArrayList<BackofficeBillBillPlanCategory>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun upsertBackofficeInvoiceDuplicates(placeId: String, invoiceId: String, accountId: String, duplicates: ArrayList<BackofficeInvoiceDuplicate>): Deferred<LiveData<Response<ArrayList<BackofficeInvoiceDuplicate>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeInvoiceDuplicate>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeInvoiceDetailsBillPlanCategories(invoiceId: String, invoiceDetailsField: BackofficeImportedInvoiceDetailsField): Deferred<LiveData<Response<ArrayList<BackofficeImportedInvoiceDetailsBillPlanCategory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeImportedInvoiceDetailsBillPlanCategory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun saveBackofficeDetailsBillPlanCategories(invoiceId: String, objBillPlanCategories: ArrayList<BackofficeImportedInvoiceDetailsObjBillPlanCategory>, invoiceDetailsField: BackofficeImportedInvoiceDetailsField): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeReceivedInvoices(placeId: String, focusCompanyId: String, versao: Int?): Deferred<LiveData<Response<ArrayList<BackofficeReceivedInvoiceHeader>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeReceivedInvoiceHeader>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeReceivedInvoice(placeId: String, focusCompanyId: String, accessKey: String): Deferred<LiveData<Response<BackofficeReceivedInvoiceHeader?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeReceivedInvoiceHeader?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeInvoiceXmlUrl(placeId: String, id: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun postBackofficeManifest(placeId: String, id: String, manifestType: BackofficeManifestType, reason: String?): Deferred<LiveData<Response<BackofficeResponseInvoiceManifest?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeResponseInvoiceManifest?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeIncomeStatement(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeIncomeStatement?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeIncomeStatement?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeIncomeByCategory(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<ArrayList<BackofficeIncomeByCategory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeIncomeByCategory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillHistory(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeBillHistory?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeBillHistory?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeExtract(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeExtract?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeExtract?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeCashFlow(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeCashFlow?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeCashFlow?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeDescriptionReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeDescriptionReport?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeDescriptionReport?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeDayReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeDayReport?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeDayReport?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillTypeReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeDescriptionReport?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeDescriptionReport?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillCategoryReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeDescriptionReport?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeDescriptionReport?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeCostCenterReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeCostCenterReport?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeCostCenterReport?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeCategoryHistoryReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeCategoryHistory?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeCategoryHistory?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillSupplierTrackerReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeBillSupplierTrackerReport?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeBillSupplierTrackerReport?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeResume(placeId: String): Deferred<LiveData<Response<BackofficeResume?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeResume?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeResumeInPeriod(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<BackofficePeriodResume?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficePeriodResume?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeBillPlanCategoriesReport(billPlanCategoryIds: ArrayList<String>, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategoriesReport>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeBillPlanCategoriesReport>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeStorage(id: String, placeId: String): Deferred<LiveData<Response<BackofficeStorage?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeStorage?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeStorages(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeStorage>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeStorage>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeStorage(placeId: String, storage: BackofficeNewStorage): Deferred<LiveData<Response<BackofficeStorage?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeStorage?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeStorage(placeId: String, id: String, storage: BackofficeNewStorage): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeStorage(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductsAtStorage(placeId: String, storageId: String): Deferred<LiveData<Response<ArrayList<BackofficeStorageProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeStorageProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductStorageHistory(placeId: String, storageId: String, productId: String, since: Calendar, until: Calendar, page: Int?, itemPerPage: Int?): Deferred<LiveData<Response<BackofficeStorageHistory?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeStorageHistory?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductStorageHistoryXls(placeId: String, storageId: String, productId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductsStorageHistory(placeId: String, storageId: String, productIds: ArrayList<String>, since: Calendar, until: Calendar, page: Int?, itemPerPage: Int?): Deferred<LiveData<Response<ArrayList<BackofficeStorageProductsHistory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeStorageProductsHistory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun sellBackofficeProductAtStorage(placeId: String, storageId: String, productId: String, count: Int, date: Calendar, shouldApplyProductionRule: Boolean): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun refundBackofficeSellProductAtStorage(placeId: String, storageId: String, productId: String, count: Int, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun cancelBackofficeSellProductAtStorage(placeId: String, storageId: String, productId: String, count: Int, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun lostBackofficeProductAtStorage(placeId: String, storageId: String, productId: String, count: Int, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun manualBackofficeAdjustProductAtStorage(placeId: String, storageId: String, productId: String, count: Int, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun transferBackofficeProduct(placeId: String, productId: String, fromId: String, toId: String, count: Int, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun transferBackofficeProducts(placeId: String, fromId: String, toId: String, date: Calendar, products: ArrayList<BackofficeMultipleStorageTransferProduct>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeProductAtStorage(placeId: String, storageId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeProductsAtStorage(placeId: String, storageId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<BackofficeDeleteProductsResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeDeleteProductsResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun zeroBackofficeAllProductsAtStorage(placeId: String, storageId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun inputBackofficeProduct(placeId: String, input: BackofficeInput, transferType: BackofficeStorageTransferType?, referenceCode: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun inputBackofficeProducts(placeId: String, inputs: ArrayList<BackofficeInput>, transferType: BackofficeStorageTransferType?, referenceCode: String?): Deferred<LiveData<Response<BackofficeInputsResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeInputsResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeProductionRule(placeId: String, rule: BackofficeNewProductionRule): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeProductionRuleInputs(placeId: String, productionRuleId: String, inputs: ArrayList<BackofficeProductionInput>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeProductionRule(placeId: String, ruleId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun applyBackofficeProductionRule(placeId: String, storageId: String, ruleId: String, inputs: ArrayList<BackofficeProductionInput>, outputs: ArrayList<BackofficeProductionOutput>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductionRules(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeProductionRule>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeProductionRule>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeProductsStatusAtStorageAndDate(placeId: String, productIds: ArrayList<String>, storageId: String, date: Calendar): Deferred<LiveData<Response<ArrayList<BackofficeProductStatusAtStorage>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeProductStatusAtStorage>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeSupplier(id: String, placeId: String): Deferred<LiveData<Response<BackofficeSupplier?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeSupplier?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeSuppliers(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeSupplier>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeSupplier>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createBackofficeSupplier(placeId: String, supplier: BackofficeNewSupplier): Deferred<LiveData<Response<BackofficeSupplier?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeSupplier?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeSupplier(placeId: String, id: String, supplier: BackofficeNewSupplier): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeSupplier(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBackofficeTransfers(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<BackofficeTransfer>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BackofficeTransfer>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun doBackofficeTransfer(placeId: String, transfer: BackofficeNewTransfer): Deferred<LiveData<Response<BackofficeTransfer?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeTransfer?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBackofficeTransfer(placeId: String, id: String, transfer: BackofficeNewTransfer): Deferred<LiveData<Response<BackofficeTransfer?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeTransfer?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBackofficeTransfer(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editBar(bar: Bar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteBar(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun addBar(placeId: String, barName: String, storageId: String, internalIp: String?): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBars(placeId: String): Deferred<LiveData<Response<ArrayList<Bar>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Bar>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun activateProductAtBar(placeId: String, barId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deactivateProductAtBar(placeId: String, barId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun activateAllProductsAtBar(placeId: String, barId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deactivateAllProductsAtBar(placeId: String, barId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun activateProductsAtBar(placeId: String, barId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deactivateProductsAtBar(placeId: String, barId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun productsSoldAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deliveredProductsByBarAtEvent(eventId: String, barId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun productsSoldAtPlace(since: Calendar, until: Calendar, placeId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun productsSoldAtOrganization(since: Calendar, until: Calendar, organizationId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deliveredProductsByBarAtPlace(since: Calendar, until: Calendar, placeId: String, barId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSellerReports(eventId: String): Deferred<LiveData<Response<ArrayList<SellerReport>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<SellerReport>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSellerReportForPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<SellerReport>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<SellerReport>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getNotDeliveredProductsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<WaiterDelivery>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<WaiterDelivery>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getConsolidatedBarSellingReport(eventId: String): Deferred<LiveData<Response<ArrayList<BarReport>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BarReport>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSellerDetails(eventId: String, sellerId: String): Deferred<LiveData<Response<ArrayList<SellerTransaction>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<SellerTransaction>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSellerDetailsPlace(placeId: String, sellerId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<SellerTransaction>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<SellerTransaction>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSellerProductDetails(eventId: String, sellerId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSellerProductDetailsPlace(placeId: String, sellerId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun exportAllTransactionProductsXlsx(eventId: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSoldBaseProductsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<BaseProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BaseProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSoldBaseProductsAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<BaseProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BaseProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCashierClosingDetails(eventId: String, cashierId: String): Deferred<LiveData<Response<CashierClosingDetails?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<CashierClosingDetails?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun bleedCashier(eventId: String, cashierId: String, value: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun updateCashierClosing(eventId: String, cashierId: String, values: CashierClosingValues): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlainCategories(): Deferred<LiveData<Response<ArrayList<BaseCategory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BaseCategory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCategories(): Deferred<LiveData<Response<ArrayList<Category>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Category>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createCategory(category: NewCategory, _data: ByteArray?): Deferred<LiveData<Response<Category?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Category?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editCategory(id: String, category: NewCategory, _data: ByteArray?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteCategory(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSellablesFromCategory(categoryId: String): Deferred<LiveData<Response<SellableList?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<SellableList?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun filterSellablesCategories(name: String): Deferred<LiveData<Response<ArrayList<Category>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Category>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCostOfGoodsSoldByProductList(placeId: String, productIds: ArrayList<String>, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<CostOfGoodsSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<CostOfGoodsSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCurrentCostOfGoodsSoldByProductList(placeId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<ArrayList<CostOfGoodsSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<CostOfGoodsSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getTotalCmvAtEvent(eventId: String): Deferred<LiveData<Response<Int?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Int?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getTotalCmvAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<Int?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Int?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun addNewCombo(placeId: String, combo: NewPlaceCombo, _data: ByteArray?): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editCombo(comboId: String, combo: NewPlaceCombo): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editComboImage(comboId: String, _data: ByteArray): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteCombo(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCoupon(id: String): Deferred<LiveData<Response<Coupon?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Coupon?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCoupons(placeId: String): Deferred<LiveData<Response<ArrayList<Coupon>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Coupon>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createCoupon(coupon: NewCoupon): Deferred<LiveData<Response<Coupon?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Coupon?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteCoupon(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun addDeviceToOrganization(smallId: String): Deferred<LiveData<Response<Device?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Device?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getDevices(): Deferred<LiveData<Response<ArrayList<Device>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Device>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun removeDeviceFromOrganization(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getDeviceStatusAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<DeviceStatus>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<DeviceStatus>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun removeDeviceFromEvent(deviceId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun logOutAndRemoveAllDevices(): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getDeviceEventStatus(eventId: String): Deferred<LiveData<Response<ArrayList<DeviceEventStatus>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<DeviceEventStatus>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEmployees(): Deferred<LiveData<Response<ArrayList<Employee>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Employee>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEmployee(employeeId: String): Deferred<LiveData<Response<Employee?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Employee?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun addEmployee(employee: NewEmployee, password: String, image: ByteArray?): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editEmployee(id: String, employee: NewEmployee): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editEmployeePassword(id: String, password: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editEmployeeImage(id: String, _data: ByteArray): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editMultipleEmployees(employeeIds: ArrayList<String>, edition: EditMultipleEmployees): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun removeMultipleEmployees(employeeIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun removeEmployee(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun addZigTagToWaiter(zigCode: String, employeeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun removeZigTagFromWaiter(employeeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createMultipleEmployees(placeId: String, bar: String?, quantity: Int, role: String?, permissions: ArrayList<String>, username: String, password: String, initial: Int, _final: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createEntrance(placeId: String, entrance: Entrance, _data: ByteArray): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteEntrance(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAllEntrances(placeId: String): Deferred<LiveData<Response<ArrayList<Entrance>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Entrance>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editEntrance(placeId: String, entrance: Entrance): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editEntranceImage(entranceId: String, _data: ByteArray): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun entrancesSoldAtPlace(since: Calendar, until: Calendar, placeId: String): Deferred<LiveData<Response<ArrayList<EntranceSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<EntranceSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun entrancesSoldAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<EntranceSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<EntranceSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun entrancesSoldByEmployee(eventId: String): Deferred<LiveData<Response<ArrayList<Bilheteiro>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Bilheteiro>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEntrancesSoldToUserAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<EntranceUser>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<EntranceUser>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEntrancesSoldToUserAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<EntranceUser>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<EntranceUser>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAllEvents(since: Calendar, until: Calendar, placeId: String): Deferred<LiveData<Response<ArrayList<Event>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Event>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createEvent(details: EventDetails, image: ByteArray?, cover: ByteArray?): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun openEvent(id: String, actualizingdatetime: Boolean): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun closeEvent(id: String): Deferred<LiveData<Response<ArrayList<OpenedReports>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<OpenedReports>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteEvent(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEvent(id: String): Deferred<LiveData<Response<Event?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Event?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEvents(placeId: String, month: Int, year: Int): Deferred<LiveData<Response<EventList?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<EventList?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun extract(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<DayResume>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<DayResume>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun extractResume(placeId: String): Deferred<LiveData<Response<ExtractResume?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ExtractResume?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getExtractDayResumeInSections(placeId: String, date: Calendar): Deferred<LiveData<Response<ArrayList<ExtractDayResumeSection>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ExtractDayResumeSection>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getRechargeExpireResume(placeId: String): Deferred<LiveData<Response<RechargeExpireResume?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<RechargeExpireResume?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getRechargeExpireExtract(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<RechargeExpireDayResume>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<RechargeExpireDayResume>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun askForAnticipation(placeId: String, value: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun withdraw(placeId: String, value: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun askForAnticipationWithObservation(placeId: String, value: Int, obs: String?, otherBankAccount: WithdrawBankAccount?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun withdrawWithObservation(placeId: String, value: Int, obs: String?, otherBankAccount: WithdrawBankAccount?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getWithdrawsResume(placeId: String, from: Calendar): Deferred<LiveData<Response<ArrayList<Withdraw>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Withdraw>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getWithdraws(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<Withdraw>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Withdraw>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getExtractAdjusts(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ExtractAdjust>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ExtractAdjust>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getConsolidatedExtractInSections(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ConsolidatedExtract?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ConsolidatedExtract?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun cashierDetailsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<Cashier>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Cashier>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun clientsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<Client>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Client>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun clientsAtPlace(since: Calendar, until: Calendar, placeId: String): Deferred<LiveData<Response<ArrayList<Client>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Client>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun clientAtPlace(userId: String, placeId: String): Deferred<LiveData<Response<Client?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Client?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun clientTransactionsAtEvent(userId: String, eventId: String): Deferred<LiveData<Response<ArrayList<UserTransaction>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<UserTransaction>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun resumeForOrganization(since: Calendar, until: Calendar, organizationId: String): Deferred<LiveData<Response<ReceiptResume?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ReceiptResume?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun cashierDetailsAtPlace(since: Calendar, until: Calendar, placeId: String): Deferred<LiveData<Response<ArrayList<Cashier>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Cashier>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun debtorsInEvent(eventId: String): Deferred<LiveData<Response<ArrayList<Debtor>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Debtor>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun debtorsInPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<Debtor>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Debtor>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBonusReport(eventId: String): Deferred<LiveData<Response<ArrayList<UserBonusReport>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<UserBonusReport>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBonusReportForPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<UserBonusReport>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<UserBonusReport>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getMinimumConsumptionReport(eventId: String): Deferred<LiveData<Response<ArrayList<UserBonusReport>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<UserBonusReport>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCashierRechargeDetails(eventId: String, cashierId: String): Deferred<LiveData<Response<ArrayList<RechargeDetails>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<RechargeDetails>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCashierPostDetails(eventId: String, cashierId: String): Deferred<LiveData<Response<ArrayList<PostDetails>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<PostDetails>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getClientDetails(clientId: String): Deferred<LiveData<Response<ClientDetails?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ClientDetails?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editClient(clientId: String, name: String, phone: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBonusByProduct(eventId: String): Deferred<LiveData<Response<ArrayList<BonusProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BonusProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBonusByProductAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<BonusProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<BonusProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getRechargeConsumptions(eventId: String): Deferred<LiveData<Response<ArrayList<RechargeConsumption>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<RechargeConsumption>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun payBillAtEventWithMultiplePayments(userId: String, eventId: String, payments: ArrayList<BillPayment>, reason: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun payBillAtPlaceInPeriodWithMultiplePayments(userId: String, placeId: String, payments: ArrayList<BillPayment>, since: Calendar, until: Calendar, reason: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun payBillsAtEventWithMultiplePayments(userIds: ArrayList<String>, eventId: String, method: PaymentMethod, isBonus: Boolean): Deferred<LiveData<Response<MultipleUserIdsResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<MultipleUserIdsResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun payBillsAtPlaceInPeriodWithMultiplePayments(userIds: ArrayList<String>, placeId: String, since: Calendar, until: Calendar, method: PaymentMethod, isBonus: Boolean): Deferred<LiveData<Response<MultipleUserIdsResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<MultipleUserIdsResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getRefundedRechargesAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<RefundedRecharge>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<RefundedRecharge>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCardActivationReturnReportAtEvent(eventId: String): Deferred<LiveData<Response<ActivationReturnReport?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ActivationReturnReport?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getResumeForEventInSections(eventId: String): Deferred<LiveData<Response<ArrayList<EventResumeSection>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<EventResumeSection>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getResumeForPlaceInSections(placeId: String, from: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<EventResumeSection>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<EventResumeSection>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPromotionsUsedAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<UsedPromotion>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<UsedPromotion>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPromotionsUsedAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<UsedPromotion>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<UsedPromotion>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEntrancesSoldByEmployeeAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<SoldEntranceWithEmployee>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<SoldEntranceWithEmployee>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEntrancesSoldByEmployeeAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<SoldEntranceWithEmployee>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<SoldEntranceWithEmployee>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getRefundedProductsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<RefundedProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<RefundedProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getRefundedProductsAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<RefundedProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<RefundedProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getTipsRemovedAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<TipRemovedList>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<TipRemovedList>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getTipsRemovedAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<TipRemovedList>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<TipRemovedList>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCardActivationReturnReportByEmployeeAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<CardReturnEmployee>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<CardReturnEmployee>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getDiscountsAtEventByEmployee(eventId: String): Deferred<LiveData<Response<ArrayList<DiscountEmployee>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<DiscountEmployee>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getDiscountsAtPlaceByEmployee(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<DiscountEmployee>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<DiscountEmployee>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getTransactionTransfersByEmployee(eventId: String): Deferred<LiveData<Response<ArrayList<TransferEmployee>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<TransferEmployee>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getManualInvoices(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ManualInvoice>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ManualInvoice>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPostLimitChangesAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<PostLimitChange>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<PostLimitChange>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPostLimitChanges(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<PostLimitChange>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<PostLimitChange>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun emitInnovaroGeneralInvoices(eventId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getOpenedBillPayments(eventId: String): Deferred<LiveData<Response<ArrayList<OpenedBillPayment>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<OpenedBillPayment>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getOpenedBillPaymentsAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<OpenedBillPayment>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<OpenedBillPayment>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getRappiDiscountsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<RappiDiscount>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<RappiDiscount>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getRappiDiscountsAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<RappiDiscount>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<RappiDiscount>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun setCouvertFiscalData(placeId: String, _data: ProductFiscalData): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCouvertFiscalData(placeId: String): Deferred<LiveData<Response<ProductFiscalData??>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ProductFiscalData??>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun searchFiscalProducts(query: String): Deferred<LiveData<Response<ArrayList<FiscalProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<FiscalProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun isNcmValid(ncm: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getInvoicePrintout(invoiceId: String): Deferred<LiveData<Response<ArrayList<ByteArray>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ByteArray>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun issueInvoicesForUser(eventId: String, userId: String): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<IssueResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun emitAllInvoicesInMonth(placeId: String): Deferred<LiveData<Response<SimpleIssueResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<SimpleIssueResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun issueInvoicesForCnpj(eventId: String, userId: String, cnpj: String): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<IssueResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun issueInvoicesForCpf(eventId: String, userId: String, cpf: String): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<IssueResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun issueInvoicesOfTransactions(eventId: String, transactionIds: ArrayList<String>, cpf: String?, cnpj: String?): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<IssueResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getInvoices(eventId: String): Deferred<LiveData<Response<ArrayList<Invoice>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Invoice>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getInvoicesFromUser(eventId: String, userId: String): Deferred<LiveData<Response<ArrayList<Invoice>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Invoice>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getInvoicesFromUserAtPlace(placeId: String, userId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<InvoiceWithEvent>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<InvoiceWithEvent>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun hasPendingInvoices(eventId: String, userId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun sendInvoicesByEmail(invoiceIds: ArrayList<String>, email: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun printInvoice(invoiceId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun hasPrinter(invoiceId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getFiscalProfiles(): Deferred<LiveData<Response<ArrayList<FiscalProfile>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<FiscalProfile>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getFiscalProductGroups(): Deferred<LiveData<Response<ArrayList<FiscalProductGroup>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<FiscalProductGroup>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createFiscalProfile(fiscalProfile: FiscalProfile): Deferred<LiveData<Response<FiscalProfile?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<FiscalProfile?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun updateFiscalProfile(fiscalProfile: FiscalProfile): Deferred<LiveData<Response<FiscalProfile?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<FiscalProfile?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteFiscalProfile(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createFiscalProductGroup(fiscalProductGroup: FiscalProductGroup): Deferred<LiveData<Response<FiscalProductGroup?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<FiscalProductGroup?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun updateFiscalProductGroup(fiscalProductGroup: FiscalProductGroup): Deferred<LiveData<Response<FiscalProductGroup?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<FiscalProductGroup?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteFiscalProductGroup(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getFiscalInvoices(placeId: String, fiscalProfileId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<Invoice>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Invoice>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPercentOfSalesIssuedInPeriod(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<SalesIssuedInPeriod?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<SalesIssuedInPeriod?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getInvoice(id: String): Deferred<LiveData<Response<Invoice?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Invoice?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getZippedInvoices(placeId: String, fiscalProfileId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getNotEmmitedTransactionsFromEvent(eventId: String, userId: String): Deferred<LiveData<Response<ArrayList<NotEmmitedTransaction>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<NotEmmitedTransaction>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun issueManualInvoice(eventId: String, manualRequest: ManualRequest, cpf: String?, cnpj: String?): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<IssueResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun issueManualInvoiceForReserve(eventId: String, manualRequest: ManualRequest, cpf: String?, cnpj: String?, reserveId: String): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<IssueResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun emitAllInnovaroInvoices(eventId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getNotEmittedProductsInEvent(eventId: String): Deferred<LiveData<Response<ArrayList<NotEmittedProductOrCombo>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<NotEmittedProductOrCombo>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getNotEmittedProducts(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<NotEmittedProductOrCombo>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<NotEmittedProductOrCombo>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getInvoicePrintData(invoiceId: String): Deferred<LiveData<Response<InvoicePrintData?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<InvoicePrintData?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createInventory(placeId: String, storageIds: ArrayList<String>, newInventory: NewInventory): Deferred<LiveData<Response<Inventory?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Inventory?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun cancelInventory(inventoryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun addStorageToInventory(inventoryId: String, storageIds: ArrayList<String>): Deferred<LiveData<Response<Inventory?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Inventory?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getInventories(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<Inventory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Inventory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getOpenedInventories(placeId: String): Deferred<LiveData<Response<ArrayList<Inventory>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Inventory>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun closeInventory(inventoryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getInventoryProductsAtStorage(inventoryId: String, storageId: String): Deferred<LiveData<Response<ArrayList<InventoryProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<InventoryProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getInventoryProducts(inventoryId: String): Deferred<LiveData<Response<ArrayList<InventoryProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<InventoryProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun updateInventoryProduct(inventoryId: String, products: ArrayList<NewInventoryProduct>): Deferred<LiveData<Response<ArrayList<InventoryProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<InventoryProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getLiveResume(eventId: String): Deferred<LiveData<Response<LiveResume?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<LiveResume?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPublic(eventId: String): Deferred<LiveData<Response<Public?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Public?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getNewPublic(eventId: String): Deferred<LiveData<Response<NewPublic?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<NewPublic?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getTopSellingProducts(eventId: String): Deferred<LiveData<Response<TopSellingProducts?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<TopSellingProducts?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSelledProducts(eventId: String): Deferred<LiveData<Response<ArrayList<SelledProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<SelledProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getTotalReceipt(eventId: String): Deferred<LiveData<Response<TotalReceipt?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<TotalReceipt?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getRechargeTimeline(eventId: String): Deferred<LiveData<Response<ArrayList<ConsumptionTimeline>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ConsumptionTimeline>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getConsumptionTimeline(eventId: String): Deferred<LiveData<Response<ArrayList<ConsumptionTimeline>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ConsumptionTimeline>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getTopClients(eventId: String): Deferred<LiveData<Response<ArrayList<TopSelling>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<TopSelling>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getBarConsumptionResume(eventId: String): Deferred<LiveData<Response<ConsumptionResume?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ConsumptionResume?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEntrancesConsumptionResume(eventId: String): Deferred<LiveData<Response<ConsumptionResume?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ConsumptionResume?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun logIn(username: String, password: String, organizationUsername: String): Deferred<LiveData<Response<Employee?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Employee?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun logOut(): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCurrentEmployee(): Deferred<LiveData<Response<Employee?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Employee?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getCurrentOrganization(): Deferred<LiveData<Response<Organization?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Organization?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlaceDetails(placeId: String): Deferred<LiveData<Response<PlaceContract?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<PlaceContract?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlaces(): Deferred<LiveData<Response<ArrayList<Place>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Place>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlace(placeId: String): Deferred<LiveData<Response<PlaceDetails?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<PlaceDetails?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editPlace(place: NewPlace, image: ByteArray?): Deferred<LiveData<Response<Place?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Place?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlaceFeatures(placeId: String): Deferred<LiveData<Response<ArrayList<PlaceFeature>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<PlaceFeature>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun activePlaceFeature(placeId: String, featureId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deactivePlaceFeature(placeId: String, featureId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun setZigPlaceFeatures(placeId: String, featureIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editPlacePassword(placeId: String, pass: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlacePassword(placeId: String): Deferred<LiveData<Response<String??>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String??>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun overridePlaceAccountId(placeId: String, accountId: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getOverridenPlaceAccount(placeId: String): Deferred<LiveData<Response<BackofficeAccount??>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<BackofficeAccount??>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editPlaceEverestConfig(placeId: String, config: EverestConfig?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlaceEverestConfig(placeId: String): Deferred<LiveData<Response<EverestConfig??>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<EverestConfig??>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAllPlaces(): Deferred<LiveData<Response<ArrayList<Place>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Place>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPosMachineTransactions(placeId: String, dates: ArrayList<Calendar>, page: Int, searchTerm: String?, itemsPerPage: Int?): Deferred<LiveData<Response<PaginationPosTransaction?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<PaginationPosTransaction?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getConsolidatedPosMachineTransactions(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<PosMachineDayResume>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<PosMachineDayResume>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun activateProductInPlace(placeId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deactivateProductInPlace(placeId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun updatePriceAtPlace(placeId: String, productId: String, value: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun updateProportionalValueAtPlace(placeId: String, productId: String, proportionalValue: ProportionalValue?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun activateAllProductsInPlace(placeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deactivateAllProductsInPlace(placeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun activateProductsInPlace(placeId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deactivateProductsInPlace(placeId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun activateAllProductsInPlaceFromCategory(placeId: String, categoryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deactivateAllProductsInPlaceFromCategory(placeId: String, categoryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun addProductToPlace(placeId: String, productId: String, fiscalData: PlaceProductFiscalData?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun removeProductFromPlace(placeId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getProductsByPlace(place: String): Deferred<LiveData<Response<ArrayList<Product>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Product>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun addNewProductToOrganization(product: NewProduct, image: EditProductImage?): Deferred<LiveData<Response<Product?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Product?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun addNewProduct(product: NewPlaceProduct, placeId: String, image: EditProductImage?): Deferred<LiveData<Response<PlaceProduct?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<PlaceProduct?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editProduct(id: String, product: NewProduct): Deferred<LiveData<Response<Product?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Product?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editProducts(products: ArrayList<EditedProduct>): Deferred<LiveData<Response<EditedResults?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<EditedResults?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editPlaceProduct(id: String, product: NewPlaceProduct, placeId: String): Deferred<LiveData<Response<PlaceProduct?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<PlaceProduct?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editProductImage(id: String, image: EditProductImage): Deferred<LiveData<Response<String??>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String??>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteProduct(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteMultipleProducts(productIds: ArrayList<String>, placeId: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createSupply(name: String, placeId: String, image: ByteArray?): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSupplyProducts(placeId: String): Deferred<LiveData<Response<ArrayList<Supply>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Supply>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAllProductCategories(placeId: String): Deferred<LiveData<Response<ArrayList<String>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<String>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlaceProduct(placeId: String, productId: String): Deferred<LiveData<Response<PlaceProduct?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<PlaceProduct?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlaceProducts(placeId: String): Deferred<LiveData<Response<ArrayList<PlaceProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<PlaceProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAllProducts(): Deferred<LiveData<Response<ArrayList<OrganizationProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<OrganizationProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getProduct(id: String): Deferred<LiveData<Response<OrganizationProduct?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<OrganizationProduct?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun inputProductAtStorage(placeId: String, input: InputProduct): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun attachPlaceProductToProductionRule(productId: String, placeId: String, productionRuleId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun removePlaceProductProductionRule(productId: String, placeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getProductionRuleFromProduct(productId: String, placeId: String): Deferred<LiveData<Response<ProductRule?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ProductRule?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getProductKinds(): Deferred<LiveData<Response<ArrayList<ProductKind>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ProductKind>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSuggestedImageFor(product: ProductImageSearch): Deferred<LiveData<Response<ArrayList<SuggestedImages>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<SuggestedImages>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun applySuggestedImageForProduct(imageId: String, productId: String, type: ApplySuggestedImageForProductType): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getProductsFromPlaceReportXls(placeId: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlaceProductsByType(placeId: String, productType: String): Deferred<LiveData<Response<ArrayList<PlaceProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<PlaceProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createPromotion(promotion: NewPromotionInfo): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createPromotionForPlace(promotion: NewPromotionInfo, placeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deletePromotion(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAvailablePromotions(placeId: String): Deferred<LiveData<Response<ArrayList<PromotionInfo>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<PromotionInfo>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun givePromotion(promotionId: String, userId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun givePromotionToManyUsers(promotionId: String, cpfPhones: ArrayList<CpfOrPhone>): Deferred<LiveData<Response<MultipleUserPromotionResult?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<MultipleUserPromotionResult?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPromotionUsers(promotionId: String): Deferred<LiveData<Response<ArrayList<PromotionUser>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<PromotionUser>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun removeUserFromPromotion(promotionId: String, userId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun editPromotion(promotionId: String, promotion: NewPromotionInfo): Deferred<LiveData<Response<PromotionInfo?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<PromotionInfo?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAllRelationsFromEvent(eventId: String): Deferred<LiveData<Response<ArrayList<Relation>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Relation>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAllRelationsFromPlace(placeId: String): Deferred<LiveData<Response<ArrayList<Relation>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Relation>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteRelation(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createEventRelation(relation: Relation, eventId: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createPlaceRelation(relation: Relation, placeId: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getPlaceEmployeeReport(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<EmployeeReport?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<EmployeeReport?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getFinanceBiReport(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<FinanceBiReport?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<FinanceBiReport?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getClientsBiReport(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ClientByReport?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ClientByReport?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getProductsBiReport(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ProductBiReport>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ProductBiReport>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun createReserve(eventId: String, reserve: NewReserve): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun deleteReserve(reserveId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun updateReserve(reserveId: String, reserve: NewReserve): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getReserves(eventId: String): Deferred<LiveData<Response<ArrayList<Reserve>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<Reserve>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getReserveDetail(reserveId: String): Deferred<LiveData<Response<ReserveDetail?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ReserveDetail?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getReservesReport(eventId: String): Deferred<LiveData<Response<ArrayList<ReserveReportRow>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ReserveReportRow>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getReserveDiscountDetails(reserveId: String): Deferred<LiveData<Response<ArrayList<ReserveDiscountDetail>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ReserveDiscountDetail>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getProductsAtStorage(placeId: String, storageId: String): Deferred<LiveData<Response<ArrayList<StorageProduct>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<StorageProduct>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEntrancesSoldAtEventInPeriod(eventId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<EntranceSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<EntranceSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getProductsSoldAtEventInPeriod(eventId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getUser(placeId: String, cpf: String?, phone: String?): Deferred<LiveData<Response<User?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<User?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getUserAtEvent(eventId: String, filter: UserFilter): Deferred<LiveData<Response<User?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<User?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getUserDetailsAtEvent(eventId: String, userId: String): Deferred<LiveData<Response<UserDetails?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<UserDetails?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getUserDetailsAtPlace(placeId: String, userId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<UserDetails?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<UserDetails?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getUserRechargesInEvent(userId: String, eventId: String): Deferred<LiveData<Response<ArrayList<UserRecharge>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<UserRecharge>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun giveRechargeToUsers(eventId: String, recharges: ArrayList<NewUserRecharge>): Deferred<LiveData<Response<ArrayList<GiveRechargeError>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<GiveRechargeError>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getAllUsersAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<EventUser>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<EventUser>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getEventUserAtEvent(eventId: String, userId: String): Deferred<LiveData<Response<EventUser?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<EventUser?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getClientsAtPlaceWithFilter(placeId: String, filters: SearchFilters, offset: Int): Deferred<LiveData<Response<ArrayList<SearchResult>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<SearchResult>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getClientsAtPlaceWithFilterXls(placeId: String, filters: SearchFilters): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getUserByCpf(placeId: String, cpf: String): Deferred<LiveData<Response<SearchResult??>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<SearchResult??>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getUserDetailsAtEventsAndPlace(placeId: String, userId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<UserDetailsByEvents>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<UserDetailsByEvents>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getSuggestedImages(keyword: String): Deferred<LiveData<Response<ArrayList<String>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<String>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getZigTagBlockConfirmsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<ZigTagBlockConfirm>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ZigTagBlockConfirm>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getZigTagBlockConfirmsAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ZigTagBlockConfirm>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ZigTagBlockConfirm>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getZigTagSyncForcesAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<ZigTagSyncForce>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ZigTagSyncForce>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getZigTagSyncForcesAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ZigTagSyncForce>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<ZigTagSyncForce>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getNotOpenedCashiers(eventId: String): Deferred<LiveData<Response<ArrayList<EmployeeBase>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<EmployeeBase>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getStockTransferReport(eventId: String): Deferred<LiveData<Response<ArrayList<StockTransfer>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<StockTransfer>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getTransactionHistory(placeId: String, userId: String): Deferred<LiveData<Response<ArrayList<TimelineEvent>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<TimelineEvent>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getGuinnessResume(eventId: String): Deferred<LiveData<Response<Int?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Int?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun getGuinnessLog(eventId: String): Deferred<LiveData<Response<ArrayList<GuinnessLog>?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<ArrayList<GuinnessLog>?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun ping(): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<String?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
       fun setPushToken(token: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
           MutableLiveData<Response<Boolean?>>().apply { 
              postValue(Response(Error(ErrorType.Fatal, "Not Implemented"), null)) 
           }
       }
    }

    lateinit var context: Context
    private val gson = Gson()
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",Locale.US).apply {
        setTimeZone(TimeZone.getTimeZone("GMT"))
    }
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    fun init(appContext: Context, useStaging: Boolean) {
          API.useStaging = useStaging
          context = appContext 
    }
    
    var BASE_URL = "api.zigcore.com.br/enterprise"
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

    private data class InternalResponse(val error: Error?, val data: JSONObject?)
    
    data class Response<T>(val error: Error?, val data: T?)

    data class MinimalPromotionInfo(
        var id: String, 
        var name: String, 
        var description: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MinimalPromotionInfo {
                return gson.fromJson(jsonToParse.toString(), MinimalPromotionInfo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MinimalPromotionInfo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MinimalPromotionInfo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Permission(
        var name: String, 
        var slug: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Permission {
                return gson.fromJson(jsonToParse.toString(), Permission::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Permission> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Permission>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ChildPermission(
        var parent: String?, 
        var name: String, 
        var slug: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ChildPermission {
                return gson.fromJson(jsonToParse.toString(), ChildPermission::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ChildPermission> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ChildPermission>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Role(
        var permissions: ArrayList<ChildPermission>, 
        var name: String, 
        var slug: String, 
        var isBase: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Role {
                return gson.fromJson(jsonToParse.toString(), Role::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Role> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Role>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SimplifiedRole(
        var permissions: ArrayList<Permission>, 
        var name: String, 
        var slug: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SimplifiedRole {
                return gson.fromJson(jsonToParse.toString(), SimplifiedRole::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SimplifiedRole> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SimplifiedRole>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PostPaymentEvent(
        var acquirerTransactions: ArrayList<AcquirerTransaction>, 
        var createdAt: Calendar, 
        var createdBy: String?, 
        var id: String, 
        var eventId: String?, 
        var event: String?, 
        var place: String?, 
        var employee: String?, 
        var isDeleted: Boolean, 
        var payments: ArrayList<PostPaymentEventPayments>, 
        var payers: ArrayList<PostPaymentEventPayers>, 
        var postPaymentType: PostPaymentEventPostPaymentType 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PostPaymentEvent {
                return gson.fromJson(jsonToParse.toString(), PostPaymentEvent::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PostPaymentEvent> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PostPaymentEvent>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PostPaymentEventPayments(
        var method: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PostPaymentEventPayments {
                return gson.fromJson(jsonToParse.toString(), PostPaymentEventPayments::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PostPaymentEventPayments> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PostPaymentEventPayments>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PostPaymentEventPayers(
        var cpf: String, 
        var name: String, 
        var email: String?, 
        var phone: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PostPaymentEventPayers {
                return gson.fromJson(jsonToParse.toString(), PostPaymentEventPayers::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PostPaymentEventPayers> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PostPaymentEventPayers>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class AcquirerTransaction(
        var transactionId: String?, 
        var acquirer: String?, 
        var transactionUrl: String?, 
        var cardBrand: String?, 
        var cardHolderName: String?, 
        var cardHolderNumber: String?, 
        var authorizatedAmount: Int?, 
        var capturedAmount: Int?, 
        var isExpiredCreditCard: Boolean?, 
        var expirationDate: String?, 
        var paidAmount: Int?, 
        var transactionType: String?, 
        var transactionStatus: String?, 
        var type: Int?, 
        var amount: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): AcquirerTransaction {
                return gson.fromJson(jsonToParse.toString(), AcquirerTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<AcquirerTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<AcquirerTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewMountableSection(
        var title: String, 
        var min: Int, 
        var max: Int, 
        var pricing: NewMountableSectionPricing, 
        var items: ArrayList<NewMountableSectionItems> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewMountableSection {
                return gson.fromJson(jsonToParse.toString(), NewMountableSection::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewMountableSection> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewMountableSection>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewMountableSectionItems(
        var productId: String, 
        var storageCount: Int, 
        var price: Int, 
        var maxCount: Int?, 
        var selectedCount: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewMountableSectionItems {
                return gson.fromJson(jsonToParse.toString(), NewMountableSectionItems::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewMountableSectionItems> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewMountableSectionItems>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MountableSection(
        var id: String, 
        var order: Int, 
        var title: String, 
        var min: Int, 
        var max: Int, 
        var pricing: NewMountableSectionPricing, 
        var items: ArrayList<NewMountableSectionItems> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MountableSection {
                return gson.fromJson(jsonToParse.toString(), MountableSection::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MountableSection> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MountableSection>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewMountableDescription(
        var sections: ArrayList<NewMountableSection> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewMountableDescription {
                return gson.fromJson(jsonToParse.toString(), NewMountableDescription::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewMountableDescription> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewMountableDescription>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MountableDescription(
        var sections: ArrayList<MountableSection> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MountableDescription {
                return gson.fromJson(jsonToParse.toString(), MountableDescription::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MountableDescription> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MountableDescription>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalPrinter(
        var type: PrinterType?, 
        var ip: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalPrinter {
                return gson.fromJson(jsonToParse.toString(), FiscalPrinter::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalPrinter> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalPrinter>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class DashboardAlert(
        var id: String, 
        var date: Calendar, 
        var title: String, 
        var description: String, 
        var image: ImageUrl, 
        var hasRead: Boolean, 
        var type: DashboardAlertType 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): DashboardAlert {
                return gson.fromJson(jsonToParse.toString(), DashboardAlert::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<DashboardAlert> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<DashboardAlert>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Announce(
        var id: String?, 
        var begin: Calendar, 
        var end: Calendar, 
        var text: String, 
        var active: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Announce {
                return gson.fromJson(jsonToParse.toString(), Announce::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Announce> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Announce>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewAccount(
        var name: String, 
        var bankAccount: BackofficeBankAccount? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewAccount {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewAccount::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewAccount> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewAccount>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeAccount(
        var id: String, 
        var name: String, 
        var bankAccount: BackofficeBankAccount? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeAccount {
                return gson.fromJson(jsonToParse.toString(), BackofficeAccount::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeAccount> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeAccount>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeAttachment(
        var fileName: String, 
        var _data: ByteArray 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeAttachment {
                return gson.fromJson(jsonToParse.toString(), BackofficeAttachment::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeAttachment> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeAttachment>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBaseBill(
        var description: String, 
        var performDate: Calendar, 
        var payDate: Calendar?, 
        var dueDate: Calendar, 
        var supplierId: String?, 
        var accountId: String, 
        var paymentMethod: BackofficeBillPaymentMethod?, 
        var observation: String?, 
        var metadata: String?, 
        var value: Int, 
        var interest: Int, 
        var fine: Int, 
        var discount: Int, 
        var reconciled: Boolean, 
        var status: BackofficeBaseBillStatus, 
        var duplicateId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBaseBill {
                return gson.fromJson(jsonToParse.toString(), BackofficeBaseBill::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBaseBill> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBaseBill>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillBillPlanCategory(
        var id: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillBillPlanCategory {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillBillPlanCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillBillPlanCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillBillPlanCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewBill(
        var billType: BackofficePartialBillType?, 
        var costCenter: String?, 
        var billPlanCategories: ArrayList<BackofficeBillBillPlanCategory>, 
        var description: String, 
        var performDate: Calendar, 
        var payDate: Calendar?, 
        var dueDate: Calendar, 
        var supplierId: String?, 
        var accountId: String, 
        var paymentMethod: BackofficeBillPaymentMethod?, 
        var observation: String?, 
        var metadata: String?, 
        var value: Int, 
        var interest: Int, 
        var fine: Int, 
        var discount: Int, 
        var reconciled: Boolean, 
        var status: BackofficeBaseBillStatus, 
        var duplicateId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewBill {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewBill::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewBill> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewBill>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBill(
        var id: String, 
        var supplier: BackofficeSupplier?, 
        var account: BackofficeAccount?, 
        var billType: BackofficeBillType?, 
        var costCenter: BackofficeCostCenter?, 
        var attachment: String?, 
        var billPlanCategories: ArrayList<BackofficeBillBillPlanCategories>, 
        var createdBySystem: Boolean, 
        var description: String, 
        var performDate: Calendar, 
        var payDate: Calendar?, 
        var dueDate: Calendar, 
        var supplierId: String?, 
        var accountId: String, 
        var paymentMethod: BackofficeBillPaymentMethod?, 
        var observation: String?, 
        var metadata: String?, 
        var value: Int, 
        var interest: Int, 
        var fine: Int, 
        var discount: Int, 
        var reconciled: Boolean, 
        var status: BackofficeBaseBillStatus, 
        var duplicateId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBill {
                return gson.fromJson(jsonToParse.toString(), BackofficeBill::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBill> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBill>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillBillPlanCategories(
        var id: String, 
        var name: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillBillPlanCategories {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillBillPlanCategories::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillBillPlanCategories> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillBillPlanCategories>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryBase(
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryBase {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryBase::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryBase> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryBase>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategory(
        var children: ArrayList<BackofficeBillPlanCategoryChildren>, 
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategory {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryChildren(
        var children: ArrayList<BackofficeBillPlanCategoryChildrenChildren>, 
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryChildren {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryChildren::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryChildren> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryChildren>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryChildrenChildren(
        var children: ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildren>, 
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryChildrenChildren {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryChildrenChildren::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryChildrenChildren> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryChildrenChildren>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryChildrenChildrenChildren(
        var children: ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildren>, 
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryChildrenChildrenChildren {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryChildrenChildrenChildren::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildren> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryChildrenChildrenChildren>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryChildrenChildrenChildrenChildren(
        var children: ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildren>, 
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryChildrenChildrenChildrenChildren {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryChildrenChildrenChildrenChildren::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildren> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryChildrenChildrenChildrenChildren>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildren(
        var children: ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildren>, 
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildren {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildren::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildren> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildren>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildren(
        var children: ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildren>, 
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildren {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildren::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildren> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildren>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildren(
        var children: ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren>, 
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildren {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildren::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildren> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildren>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren(
        var children: ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren>, 
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren(
        var children: ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren>, 
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren(
        var id: String, 
        var name: String, 
        var path: String, 
        var parentId: String?, 
        var hasBills: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoryChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildrenChildren>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewBillType(
        var name: String, 
        var categoryId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewBillType {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewBillType::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewBillType> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewBillType>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillType(
        var id: String, 
        var category: BackofficeCategory, 
        var name: String, 
        var categoryId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillType {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillType::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillType> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillType>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficePartialBillType(
        var idOrName: String, 
        var category: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficePartialBillType {
                return gson.fromJson(jsonToParse.toString(), BackofficePartialBillType::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficePartialBillType> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficePartialBillType>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewCategory(
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewCategory {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeCategory(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeCategory {
                return gson.fromJson(jsonToParse.toString(), BackofficeCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeProductCmv(
        var soldCount: Int, 
        var inputs: ArrayList<BackofficeProductCmvInputs>, 
        var productId: String, 
        var unitCostValue: Int?, 
        var realUnitCostValue: Int?, 
        var absolutePopularity: Float?, 
        var relativePopularity: Float? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeProductCmv {
                return gson.fromJson(jsonToParse.toString(), BackofficeProductCmv::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeProductCmv> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeProductCmv>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeProductCmvInputs(
        var productId: String, 
        var count: Int, 
        var unitCostValue: Int?, 
        var realUnitCostValue: Int?, 
        var isCompound: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeProductCmvInputs {
                return gson.fromJson(jsonToParse.toString(), BackofficeProductCmvInputs::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeProductCmvInputs> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeProductCmvInputs>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeProductSimpleCmv(
        var productId: String, 
        var unitCostValue: Int?, 
        var realUnitCostValue: Int?, 
        var absolutePopularity: Float?, 
        var relativePopularity: Float? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeProductSimpleCmv {
                return gson.fromJson(jsonToParse.toString(), BackofficeProductSimpleCmv::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeProductSimpleCmv> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeProductSimpleCmv>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewCostCenter(
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewCostCenter {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewCostCenter::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewCostCenter> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewCostCenter>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeCostCenter(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeCostCenter {
                return gson.fromJson(jsonToParse.toString(), BackofficeCostCenter::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeCostCenter> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeCostCenter>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeFocusCompany(
        var id: String, 
        var cnpj: String, 
        var token: String, 
        var lastInvoiceVersion: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeFocusCompany {
                return gson.fromJson(jsonToParse.toString(), BackofficeFocusCompany::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeFocusCompany> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeFocusCompany>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeFocusInvoice(
        var id: String, 
        var focusCompanyId: String, 
        var supplierName: String, 
        var supplierDocument: String, 
        var accessKey: String, 
        var totalValue: Int, 
        var issueDate: Calendar, 
        var situation: String, 
        var completed: Boolean, 
        var type: Int, 
        var version: Int, 
        var digestValue: String?, 
        var targetManifestion: BackofficeManifestType? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeFocusInvoice {
                return gson.fromJson(jsonToParse.toString(), BackofficeFocusInvoice::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeFocusInvoice> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeFocusInvoice>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBaseInventory(
        var date: Calendar, 
        var isTotal: Boolean, 
        var createdBy: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBaseInventory {
                return gson.fromJson(jsonToParse.toString(), BackofficeBaseInventory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBaseInventory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBaseInventory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewInventory(
        var storageIds: ArrayList<String>, 
        var date: Calendar, 
        var isTotal: Boolean, 
        var createdBy: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewInventory {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewInventory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewInventory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewInventory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeInventory(
        var id: String, 
        var status: BackofficeInventoryStatus, 
        var storages: ArrayList<BackofficeInventoryStorages>, 
        var date: Calendar, 
        var isTotal: Boolean, 
        var createdBy: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeInventory {
                return gson.fromJson(jsonToParse.toString(), BackofficeInventory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeInventory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeInventory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeInventoryStorages(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeInventoryStorages {
                return gson.fromJson(jsonToParse.toString(), BackofficeInventoryStorages::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeInventoryStorages> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeInventoryStorages>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeInventoryProduct(
        var productId: String, 
        var storageId: String, 
        var inventoryCount: Int?, 
        var inventoryUnitValue: Int?, 
        var realCount: Int?, 
        var realUnitValue: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeInventoryProduct {
                return gson.fromJson(jsonToParse.toString(), BackofficeInventoryProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeInventoryProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeInventoryProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeInvoiceDuplicate(
        var id: String?, 
        var code: String?, 
        var dueDate: Calendar?, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeInvoiceDuplicate {
                return gson.fromJson(jsonToParse.toString(), BackofficeInvoiceDuplicate::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeInvoiceDuplicate> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeInvoiceDuplicate>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoice(
        var id: String, 
        var createdAt: Calendar, 
        var xml: String?, 
        var completed: Boolean, 
        var storageId: String?, 
        var bill: BackofficeImportedInvoiceBill, 
        var details: BackofficeImportedInvoiceDetails, 
        var supplier: BackofficeImportedInvoiceSupplier, 
        var products: ArrayList<BackofficeImportedInvoiceProduct>, 
        var duplicates: ArrayList<BackofficeInvoiceDuplicate> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoice {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoice::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoice> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoice>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoiceBill(
        var accountId: String?, 
        var createBill: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoiceBill {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoiceBill::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoiceBill> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoiceBill>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoiceDetails(
        var num: Int, 
        var serie: Int, 
        var operationType: String, 
        var accessKey: String?, 
        var date: Calendar, 
        var supplier: BackofficeImportedInvoiceDetailsSupplier, 
        var totalValue: Int, 
        var totalProductsValue: Int, 
        var taxs: BackofficeImportedInvoiceDetailsTaxs, 
        var insuranceValue: Int, 
        var discountValue: Int, 
        var otherExpensesValue: Int, 
        var shippingValue: Int, 
        var cfop: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoiceDetails {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoiceDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoiceDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoiceDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoiceDetailsSupplier(
        var name: String, 
        var cpfOrCnpj: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoiceDetailsSupplier {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoiceDetailsSupplier::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoiceDetailsSupplier> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoiceDetailsSupplier>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoiceDetailsTaxs(
        var icmsBase: Int, 
        var icmsValue: Int, 
        var icmsstBase: Int, 
        var icmsstValue: Int, 
        var ipiValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoiceDetailsTaxs {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoiceDetailsTaxs::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoiceDetailsTaxs> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoiceDetailsTaxs>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoiceSupplier(
        var pending: Boolean, 
        var skipped: Boolean, 
        var associatedSupplier: BackofficeSupplier?, 
        var invoiceEntry: BackofficeImportedInvoiceSupplierInvoiceEntry? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoiceSupplier {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoiceSupplier::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoiceSupplier> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoiceSupplier>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoiceSupplierInvoiceEntry(
        var cpfOrCnpj: String, 
        var name: String, 
        var legalName: String?, 
        var inscricaoEstadual: String?, 
        var address: BackofficeAddress? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoiceSupplierInvoiceEntry {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoiceSupplierInvoiceEntry::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoiceSupplierInvoiceEntry> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoiceSupplierInvoiceEntry>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewManualInvoice(
        var storageId: String?, 
        var bill: BackofficeNewManualInvoiceBill, 
        var details: BackofficeNewManualInvoiceDetails, 
        var supplierId: String?, 
        var products: ArrayList<BackofficeManualInvoiceProduct>, 
        var duplicates: ArrayList<BackofficeInvoiceDuplicate> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewManualInvoice {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewManualInvoice::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewManualInvoice> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewManualInvoice>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewManualInvoiceBill(
        var accountId: String?, 
        var createBill: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewManualInvoiceBill {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewManualInvoiceBill::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewManualInvoiceBill> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewManualInvoiceBill>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewManualInvoiceDetails(
        var num: Int, 
        var serie: Int, 
        var operationType: String, 
        var date: Calendar, 
        var taxs: BackofficeNewManualInvoiceDetailsTaxs, 
        var insuranceValue: Int, 
        var discountValue: Int, 
        var otherExpensesValue: Int, 
        var shippingValue: Int, 
        var cfop: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewManualInvoiceDetails {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewManualInvoiceDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewManualInvoiceDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewManualInvoiceDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewManualInvoiceDetailsTaxs(
        var icmsBase: Int, 
        var icmsValue: Int, 
        var icmsstBase: Int, 
        var icmsstValue: Int, 
        var ipiValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewManualInvoiceDetailsTaxs {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewManualInvoiceDetailsTaxs::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewManualInvoiceDetailsTaxs> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewManualInvoiceDetailsTaxs>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeManualInvoiceProduct(
        var associatedProductId: String, 
        var count: Int, 
        var unitValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeManualInvoiceProduct {
                return gson.fromJson(jsonToParse.toString(), BackofficeManualInvoiceProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeManualInvoiceProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeManualInvoiceProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoiceProduct(
        var entryId: String, 
        var pending: Boolean, 
        var skipped: Boolean, 
        var associatedProduct: Product?, 
        var unitValue: Int, 
        var unitMultiplier: Int, 
        var invoiceEntry: BackofficeImportedInvoiceProductInvoiceEntry 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoiceProduct {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoiceProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoiceProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoiceProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoiceProductInvoiceEntry(
        var index: Int, 
        var code: String, 
        var description: String, 
        var commercialUnit: String, 
        var commercialQuantity: Int, 
        var unitValue: Int, 
        var EAN: String?, 
        var totalValue: Int, 
        var icmsStValue: Int?, 
        var icmsStAliquot: Int?, 
        var icmsStBase: Int?, 
        var ipiValue: Int?, 
        var ipiAliquot: Int?, 
        var ipiBase: Int?, 
        var shippingValue: Int?, 
        var otherExpensesValue: Int?, 
        var discountValue: Int?, 
        var insuranceValue: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoiceProductInvoiceEntry {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoiceProductInvoiceEntry::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoiceProductInvoiceEntry> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoiceProductInvoiceEntry>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeUpsertManualProduct(
        var id: String?, 
        var code: String, 
        var associatedProductId: String, 
        var count: Int, 
        var unitValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeUpsertManualProduct {
                return gson.fromJson(jsonToParse.toString(), BackofficeUpsertManualProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeUpsertManualProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeUpsertManualProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoiceDetailsObjBillPlanCategory(
        var billPlanCategoryId: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoiceDetailsObjBillPlanCategory {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoiceDetailsObjBillPlanCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoiceDetailsObjBillPlanCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoiceDetailsObjBillPlanCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeImportedInvoiceDetailsBillPlanCategory(
        var invoiceId: String, 
        var invoiceDetailsField: BackofficeImportedInvoiceDetailsField, 
        var billPlanCategoryId: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeImportedInvoiceDetailsBillPlanCategory {
                return gson.fromJson(jsonToParse.toString(), BackofficeImportedInvoiceDetailsBillPlanCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeImportedInvoiceDetailsBillPlanCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeImportedInvoiceDetailsBillPlanCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeProductLastInvoiceDetails(
        var isImported: Boolean, 
        var totalProductValue: Int, 
        var entryId: String, 
        var pending: Boolean, 
        var skipped: Boolean, 
        var associatedProduct: Product?, 
        var unitValue: Int, 
        var unitMultiplier: Int, 
        var invoiceEntry: BackofficeImportedInvoiceProductInvoiceEntry 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeProductLastInvoiceDetails {
                return gson.fromJson(jsonToParse.toString(), BackofficeProductLastInvoiceDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeProductLastInvoiceDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeProductLastInvoiceDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeReceivedInvoiceHeader(
        var id: String, 
        var supplierName: String, 
        var supplierDocument: String, 
        var accessKey: String, 
        var totalValue: Int, 
        var issueDate: Calendar, 
        var state: String, 
        var targetManifestion: String?, 
        var completeNfe: Boolean, 
        var kindNfe: String, 
        var version: Int, 
        var digestValue: String?, 
        var numCorrection: String?, 
        var cancelDate: Calendar?, 
        var cancelJustification: String?, 
        var isImported: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeReceivedInvoiceHeader {
                return gson.fromJson(jsonToParse.toString(), BackofficeReceivedInvoiceHeader::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeReceivedInvoiceHeader> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeReceivedInvoiceHeader>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeResponseInvoiceManifest(
        var statusSefaz: String, 
        var messageSefaz: String, 
        var status: String, 
        var protocol: String, 
        var typeManifest: String, 
        var reason: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeResponseInvoiceManifest {
                return gson.fromJson(jsonToParse.toString(), BackofficeResponseInvoiceManifest::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeResponseInvoiceManifest> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeResponseInvoiceManifest>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeIncomeStatement(
        var grossRevenue: Int, 
        var grossProfit: Int, 
        var netTotal: Int, 
        var taxTotal: Int, 
        var categories: ArrayList<BackofficeCategoryTotal>, 
        var since: Calendar, 
        var until: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeIncomeStatement {
                return gson.fromJson(jsonToParse.toString(), BackofficeIncomeStatement::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeIncomeStatement> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeIncomeStatement>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeCategoryTotal(
        var name: String, 
        var categoryId: String?, 
        var parcialProfit: Int, 
        var total: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeCategoryTotal {
                return gson.fromJson(jsonToParse.toString(), BackofficeCategoryTotal::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeCategoryTotal> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeCategoryTotal>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillHistory(
        var historicByDay: ArrayList<BackofficeBillHistoryByDay>, 
        var since: Calendar, 
        var until: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillHistory {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillHistory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillHistory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillHistory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillHistoryByDay(
        var billDataHistory: ArrayList<BackofficeBillDataHistory>, 
        var date: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillHistoryByDay {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillHistoryByDay::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillHistoryByDay> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillHistoryByDay>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillDataHistory(
        var billId: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillDataHistory {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillDataHistory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillDataHistory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillDataHistory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeCategoryHistory(
        var historicByDay: ArrayList<BackofficeCategoryHistoryByDay>, 
        var since: Calendar, 
        var until: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeCategoryHistory {
                return gson.fromJson(jsonToParse.toString(), BackofficeCategoryHistory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeCategoryHistory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeCategoryHistory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeCategoryHistoryByDay(
        var categoryDataHistory: ArrayList<BackofficeCategoryDataHistory>, 
        var date: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeCategoryHistoryByDay {
                return gson.fromJson(jsonToParse.toString(), BackofficeCategoryHistoryByDay::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeCategoryHistoryByDay> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeCategoryHistoryByDay>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeCategoryDataHistory(
        var categoryName: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeCategoryDataHistory {
                return gson.fromJson(jsonToParse.toString(), BackofficeCategoryDataHistory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeCategoryDataHistory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeCategoryDataHistory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeExtract(
        var previousBalance: Int, 
        var finalBalance: Int, 
        var periodBalance: Int, 
        var totalReceipts: Int, 
        var totalExpenses: Int, 
        var billExtractData: ArrayList<BackofficeBillData>, 
        var since: Calendar, 
        var until: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeExtract {
                return gson.fromJson(jsonToParse.toString(), BackofficeExtract::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeExtract> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeExtract>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillData(
        var date: Calendar, 
        var description: String, 
        var categoryName: String, 
        var value: Int, 
        var balance: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillData {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillData::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillData> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillData>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeCashFlow(
        var previousBalance: Int, 
        var finalBalance: Int, 
        var periodBalance: Int, 
        var totalReceipts: Int, 
        var totalExpenses: Int, 
        var receipts: ArrayList<BackofficeBillData>, 
        var expenses: ArrayList<BackofficeBillData>, 
        var since: Calendar, 
        var until: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeCashFlow {
                return gson.fromJson(jsonToParse.toString(), BackofficeCashFlow::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeCashFlow> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeCashFlow>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeDescriptionReport(
        var bills: ArrayList<BackofficeBillDescriptionData>, 
        var total: Int, 
        var since: Calendar, 
        var until: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeDescriptionReport {
                return gson.fromJson(jsonToParse.toString(), BackofficeDescriptionReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeDescriptionReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeDescriptionReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillDescriptionData(
        var description: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillDescriptionData {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillDescriptionData::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillDescriptionData> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillDescriptionData>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeDayReport(
        var bills: ArrayList<BackofficeBillDayData>, 
        var total: Int, 
        var since: Calendar, 
        var until: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeDayReport {
                return gson.fromJson(jsonToParse.toString(), BackofficeDayReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeDayReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeDayReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillDayData(
        var date: Calendar, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillDayData {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillDayData::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillDayData> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillDayData>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeIncomeByCategory(
        var categoryName: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeIncomeByCategory {
                return gson.fromJson(jsonToParse.toString(), BackofficeIncomeByCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeIncomeByCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeIncomeByCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeCostCenterReport(
        var costCenterBills: ArrayList<BackofficeCostCenterBills>, 
        var total: Int, 
        var since: Calendar, 
        var until: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeCostCenterReport {
                return gson.fromJson(jsonToParse.toString(), BackofficeCostCenterReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeCostCenterReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeCostCenterReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeCostCenterBills(
        var name: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeCostCenterBills {
                return gson.fromJson(jsonToParse.toString(), BackofficeCostCenterBills::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeCostCenterBills> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeCostCenterBills>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillSupplierTrackerReport(
        var billSupplierTrackerData: ArrayList<BackofficeBillSupplierTrackerData>, 
        var since: Calendar, 
        var until: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillSupplierTrackerReport {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillSupplierTrackerReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillSupplierTrackerReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillSupplierTrackerReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillSupplierTrackerData(
        var supplierId: String?, 
        var supplierName: String, 
        var billDescription: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillSupplierTrackerData {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillSupplierTrackerData::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillSupplierTrackerData> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillSupplierTrackerData>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeResume(
        var billsDueToToday: ArrayList<BackofficeResumeBillsDueToToday>, 
        var accountBalances: ArrayList<BackofficeResumeAccountBalances> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeResume {
                return gson.fromJson(jsonToParse.toString(), BackofficeResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeResumeBillsDueToToday(
        var id: String, 
        var value: Int, 
        var status: BackofficeResumeBillsDueToTodayStatus, 
        var description: String, 
        var account: BackofficeResumeBillsDueToTodayAccount 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeResumeBillsDueToToday {
                return gson.fromJson(jsonToParse.toString(), BackofficeResumeBillsDueToToday::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeResumeBillsDueToToday> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeResumeBillsDueToToday>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeResumeBillsDueToTodayAccount(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeResumeBillsDueToTodayAccount {
                return gson.fromJson(jsonToParse.toString(), BackofficeResumeBillsDueToTodayAccount::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeResumeBillsDueToTodayAccount> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeResumeBillsDueToTodayAccount>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeResumeAccountBalances(
        var id: String, 
        var name: String, 
        var balanceValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeResumeAccountBalances {
                return gson.fromJson(jsonToParse.toString(), BackofficeResumeAccountBalances::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeResumeAccountBalances> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeResumeAccountBalances>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficePeriodResume(
        var toReceive: BackofficePeriodResumeToReceive, 
        var toPay: BackofficePeriodResumeToPay, 
        var balance: Int, 
        var growth: Float? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficePeriodResume {
                return gson.fromJson(jsonToParse.toString(), BackofficePeriodResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficePeriodResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficePeriodResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficePeriodResumeToReceive(
        var paid: Int, 
        var pending: Int, 
        var total: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficePeriodResumeToReceive {
                return gson.fromJson(jsonToParse.toString(), BackofficePeriodResumeToReceive::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficePeriodResumeToReceive> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficePeriodResumeToReceive>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficePeriodResumeToPay(
        var paid: Int, 
        var pending: Int, 
        var total: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficePeriodResumeToPay {
                return gson.fromJson(jsonToParse.toString(), BackofficePeriodResumeToPay::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficePeriodResumeToPay> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficePeriodResumeToPay>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoriesReport(
        var id: String, 
        var name: String, 
        var total: Int, 
        var bills: ArrayList<BackofficeBillPlanCategoriesReportBills> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoriesReport {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoriesReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoriesReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoriesReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBillPlanCategoriesReportBills(
        var description: String, 
        var performDate: Calendar, 
        var dueDate: Calendar, 
        var payDate: Calendar?, 
        var value: Int, 
        var account: String?, 
        var supplier: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBillPlanCategoriesReportBills {
                return gson.fromJson(jsonToParse.toString(), BackofficeBillPlanCategoriesReportBills::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBillPlanCategoriesReportBills> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBillPlanCategoriesReportBills>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewStorage(
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewStorage {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewStorage::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewStorage> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewStorage>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeStorage(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeStorage {
                return gson.fromJson(jsonToParse.toString(), BackofficeStorage::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeStorage> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeStorage>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeStorageProduct(
        var id: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeStorageProduct {
                return gson.fromJson(jsonToParse.toString(), BackofficeStorageProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeStorageProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeStorageProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeStorageHistory(
        var currentPage: Int?, 
        var initial: Int, 
        var totalTransfersItems: Int, 
        var transfers: ArrayList<BackofficeStorageHistoryTransfers> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeStorageHistory {
                return gson.fromJson(jsonToParse.toString(), BackofficeStorageHistory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeStorageHistory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeStorageHistory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeStorageHistoryTransfers(
        var date: Calendar, 
        var type: BackofficeStorageTransferType, 
        var count: Int, 
        var employeeId: String, 
        var unitCostValue: Int?, 
        var referenceCode: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeStorageHistoryTransfers {
                return gson.fromJson(jsonToParse.toString(), BackofficeStorageHistoryTransfers::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeStorageHistoryTransfers> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeStorageHistoryTransfers>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeStorageProductsHistory(
        var productName: String?, 
        var productId: String, 
        var history: BackofficeStorageHistory 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeStorageProductsHistory {
                return gson.fromJson(jsonToParse.toString(), BackofficeStorageProductsHistory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeStorageProductsHistory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeStorageProductsHistory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeDeleteProductsResult(
        var success: ArrayList<String>, 
        var error: ArrayList<BackofficeDeleteProductsResultError> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeDeleteProductsResult {
                return gson.fromJson(jsonToParse.toString(), BackofficeDeleteProductsResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeDeleteProductsResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeDeleteProductsResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeDeleteProductsResultError(
        var productId: String, 
        var reason: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeDeleteProductsResultError {
                return gson.fromJson(jsonToParse.toString(), BackofficeDeleteProductsResultError::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeDeleteProductsResultError> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeDeleteProductsResultError>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeMultipleStorageTransferProduct(
        var productId: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeMultipleStorageTransferProduct {
                return gson.fromJson(jsonToParse.toString(), BackofficeMultipleStorageTransferProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeMultipleStorageTransferProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeMultipleStorageTransferProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeInput(
        var productId: String, 
        var storageId: String, 
        var unitCostValue: Int?, 
        var obs: String?, 
        var count: Int, 
        var date: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeInput {
                return gson.fromJson(jsonToParse.toString(), BackofficeInput::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeInput> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeInput>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeInputsResult(
        var success: ArrayList<BackofficeInput>, 
        var error: ArrayList<BackofficeInputsResultError> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeInputsResult {
                return gson.fromJson(jsonToParse.toString(), BackofficeInputsResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeInputsResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeInputsResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeInputsResultError(
        var input: BackofficeInput, 
        var reason: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeInputsResultError {
                return gson.fromJson(jsonToParse.toString(), BackofficeInputsResultError::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeInputsResultError> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeInputsResultError>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewProductionRule(
        var inputs: ArrayList<BackofficeProductionInput>, 
        var outputs: ArrayList<BackofficeNewProductionRuleOutputs> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewProductionRule {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewProductionRule::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewProductionRule> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewProductionRule>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewProductionRuleOutputs(
        var productId: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewProductionRuleOutputs {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewProductionRuleOutputs::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewProductionRuleOutputs> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewProductionRuleOutputs>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeProductionInput(
        var productId: String, 
        var count: Int, 
        var loss: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeProductionInput {
                return gson.fromJson(jsonToParse.toString(), BackofficeProductionInput::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeProductionInput> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeProductionInput>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeProductionOutput(
        var productId: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeProductionOutput {
                return gson.fromJson(jsonToParse.toString(), BackofficeProductionOutput::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeProductionOutput> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeProductionOutput>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeProductionRule(
        var id: String, 
        var inputs: ArrayList<BackofficeProductionRuleInputs>, 
        var outputs: ArrayList<BackofficeProductionRuleOutputs> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeProductionRule {
                return gson.fromJson(jsonToParse.toString(), BackofficeProductionRule::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeProductionRule> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeProductionRule>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeProductionRuleInputs(
        var productId: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeProductionRuleInputs {
                return gson.fromJson(jsonToParse.toString(), BackofficeProductionRuleInputs::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeProductionRuleInputs> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeProductionRuleInputs>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeProductionRuleOutputs(
        var productId: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeProductionRuleOutputs {
                return gson.fromJson(jsonToParse.toString(), BackofficeProductionRuleOutputs::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeProductionRuleOutputs> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeProductionRuleOutputs>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeProductStatusAtStorage(
        var productId: String, 
        var storageId: String, 
        var count: Int, 
        var unitCostValue: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeProductStatusAtStorage {
                return gson.fromJson(jsonToParse.toString(), BackofficeProductStatusAtStorage::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeProductStatusAtStorage> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeProductStatusAtStorage>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeAddress(
        var street: String, 
        var number: String, 
        var complemento: String?, 
        var cep: String, 
        var state: String, 
        var city: String, 
        var neighborhood: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeAddress {
                return gson.fromJson(jsonToParse.toString(), BackofficeAddress::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeAddress> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeAddress>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeBankAccount(
        var bank: String, 
        var account: String, 
        var agency: String, 
        var cpfOrCnpj: String, 
        var ownerName: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeBankAccount {
                return gson.fromJson(jsonToParse.toString(), BackofficeBankAccount::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeBankAccount> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeBankAccount>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewSupplier(
        var name: String, 
        var legalName: String, 
        var inscricaoEstadual: String?, 
        var cpfOrCnpj: String?, 
        var address: BackofficeAddress?, 
        var bankAccount: BackofficeBankAccount? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewSupplier {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewSupplier::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewSupplier> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewSupplier>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeSupplier(
        var id: String, 
        var name: String, 
        var legalName: String, 
        var inscricaoEstadual: String?, 
        var cpfOrCnpj: String?, 
        var address: BackofficeAddress?, 
        var bankAccount: BackofficeBankAccount? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeSupplier {
                return gson.fromJson(jsonToParse.toString(), BackofficeSupplier::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeSupplier> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeSupplier>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeNewTransfer(
        var date: Calendar, 
        var description: String, 
        var value: Int, 
        var paid: Boolean, 
        var sourceAccountId: String, 
        var destinationAccountId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeNewTransfer {
                return gson.fromJson(jsonToParse.toString(), BackofficeNewTransfer::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeNewTransfer> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeNewTransfer>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BackofficeTransfer(
        var id: String, 
        var date: Calendar, 
        var description: String, 
        var value: Int, 
        var paid: Boolean, 
        var sourceAccount: BackofficeAccount, 
        var destinationAccount: BackofficeAccount, 
        var sourceBill: BackofficeBill, 
        var destinationBill: BackofficeBill 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BackofficeTransfer {
                return gson.fromJson(jsonToParse.toString(), BackofficeTransfer::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BackofficeTransfer> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BackofficeTransfer>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Bar(
        var id: String?, 
        var name: String, 
        var image: ImageUrl?, 
        var storageId: String, 
        var storageName: String?, 
        var internalIp: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Bar {
                return gson.fromJson(jsonToParse.toString(), Bar::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Bar> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Bar>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellerReport(
        var id: String, 
        var name: String, 
        var soldValue: Int, 
        var tip: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellerReport {
                return gson.fromJson(jsonToParse.toString(), SellerReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellerReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellerReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class WaiterDelivery(
        var id: String, 
        var name: String, 
        var productsToDelivery: ArrayList<StockProduct> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): WaiterDelivery {
                return gson.fromJson(jsonToParse.toString(), WaiterDelivery::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<WaiterDelivery> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<WaiterDelivery>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BarReport(
        var id: String, 
        var name: String, 
        var quantity: Int, 
        var totalValue: Int, 
        var totalDiscount: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BarReport {
                return gson.fromJson(jsonToParse.toString(), BarReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BarReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BarReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellerTransaction(
        var id: String, 
        var date: Calendar, 
        var clients: ArrayList<String>, 
        var value: Int, 
        var tip: Int, 
        var refunded: Boolean, 
        var products: ArrayList<String> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellerTransaction {
                return gson.fromJson(jsonToParse.toString(), SellerTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellerTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellerTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BaseProduct(
        var id: String, 
        var name: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BaseProduct {
                return gson.fromJson(jsonToParse.toString(), BaseProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BaseProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BaseProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CashierClosingValues(
        var cashValue: Int, 
        var creditValue: Int, 
        var debitValue: Int, 
        var voucherValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CashierClosingValues {
                return gson.fromJson(jsonToParse.toString(), CashierClosingValues::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CashierClosingValues> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CashierClosingValues>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CashierClosingDetails(
        var bleedings: ArrayList<CashierClosingDetailsBleedings>, 
        var closing: CashierClosingValues 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CashierClosingDetails {
                return gson.fromJson(jsonToParse.toString(), CashierClosingDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CashierClosingDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CashierClosingDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CashierClosingDetailsBleedings(
        var id: String, 
        var author: String, 
        var date: Calendar, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CashierClosingDetailsBleedings {
                return gson.fromJson(jsonToParse.toString(), CashierClosingDetailsBleedings::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CashierClosingDetailsBleedings> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CashierClosingDetailsBleedings>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BaseCategory(
        var id: String, 
        var name: String, 
        var description: String?, 
        var priority: Int, 
        var image: ImageUrl?, 
        var parentId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BaseCategory {
                return gson.fromJson(jsonToParse.toString(), BaseCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BaseCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BaseCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Category(
        var subCategories: ArrayList<CategorySubCategories>, 
        var id: String, 
        var name: String, 
        var description: String?, 
        var priority: Int, 
        var image: ImageUrl?, 
        var parentId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Category {
                return gson.fromJson(jsonToParse.toString(), Category::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Category> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Category>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CategorySubCategories(
        var subCategories: ArrayList<CategorySubCategoriesSubCategories>, 
        var id: String, 
        var name: String, 
        var description: String?, 
        var priority: Int, 
        var image: ImageUrl?, 
        var parentId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CategorySubCategories {
                return gson.fromJson(jsonToParse.toString(), CategorySubCategories::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CategorySubCategories> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CategorySubCategories>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CategorySubCategoriesSubCategories(
        var id: String, 
        var name: String, 
        var description: String?, 
        var priority: Int, 
        var image: ImageUrl?, 
        var parentId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CategorySubCategoriesSubCategories {
                return gson.fromJson(jsonToParse.toString(), CategorySubCategoriesSubCategories::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CategorySubCategoriesSubCategories> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CategorySubCategoriesSubCategories>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewCategory(
        var name: String, 
        var description: String?, 
        var priority: Int, 
        var parentId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewCategory {
                return gson.fromJson(jsonToParse.toString(), NewCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellableList(
        var products: ArrayList<SellableListProducts>, 
        var combos: ArrayList<SellableListCombos> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellableList {
                return gson.fromJson(jsonToParse.toString(), SellableList::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellableList> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellableList>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellableListProducts(
        var id: String, 
        var name: String, 
        var category: String, 
        var type: String, 
        var image: ImageUrl?, 
        var hasAlcohol: Boolean?, 
        var fiscalCode: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellableListProducts {
                return gson.fromJson(jsonToParse.toString(), SellableListProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellableListProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellableListProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellableListCombos(
        var id: String, 
        var name: String, 
        var description: String, 
        var category: String, 
        var image: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellableListCombos {
                return gson.fromJson(jsonToParse.toString(), SellableListCombos::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellableListCombos> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellableListCombos>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CostOfGoodsSold(
        var id: String, 
        var name: String, 
        var supplies: ArrayList<CostOfGoodsSoldSupplies>, 
        var resellingPrice: Int, 
        var productUnitCost: Int?, 
        var productRealCost: Int?, 
        var relativePopularity: Float?, 
        var absolutePopularity: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CostOfGoodsSold {
                return gson.fromJson(jsonToParse.toString(), CostOfGoodsSold::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CostOfGoodsSold> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CostOfGoodsSold>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CostOfGoodsSoldSupplies(
        var id: String, 
        var name: String, 
        var quantity: Int, 
        var unitCost: Int?, 
        var realCost: Int?, 
        var isCompound: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CostOfGoodsSoldSupplies {
                return gson.fromJson(jsonToParse.toString(), CostOfGoodsSoldSupplies::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CostOfGoodsSoldSupplies> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CostOfGoodsSoldSupplies>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewCoupon(
        var placeId: String, 
        var name: String, 
        var discountType: CouponDiscountType, 
        var bonusValue: Int?, 
        var promotionId: String?, 
        var type: CouponType, 
        var expiresAt: Calendar?, 
        var quantity: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewCoupon {
                return gson.fromJson(jsonToParse.toString(), NewCoupon::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewCoupon> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewCoupon>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Coupon(
        var id: String, 
        var valid: Boolean, 
        var usageCount: Int, 
        var clientsAverageTicket: Int?, 
        var qrCodeUrl: String, 
        var promotion: MinimalPromotionInfo?, 
        var placeId: String, 
        var name: String, 
        var discountType: CouponDiscountType, 
        var bonusValue: Int?, 
        var promotionId: String?, 
        var type: CouponType, 
        var expiresAt: Calendar?, 
        var quantity: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Coupon {
                return gson.fromJson(jsonToParse.toString(), Coupon::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Coupon> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Coupon>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Device(
        var code: String, 
        var id: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Device {
                return gson.fromJson(jsonToParse.toString(), Device::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Device> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Device>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class DeviceStatus(
        var id: String, 
        var smallId: String, 
        var employee: String, 
        var status: String?, 
        var updatedAt: Calendar?, 
        var removedFromEventAt: Calendar? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): DeviceStatus {
                return gson.fromJson(jsonToParse.toString(), DeviceStatus::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<DeviceStatus> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<DeviceStatus>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class DeviceEventStatus(
        var id: String, 
        var fingerprint: String, 
        var employeeName: String?, 
        var isOnline: Boolean, 
        var lastOfflineSync: Calendar, 
        var pdvVersion: String, 
        var hasFinishedActivity: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): DeviceEventStatus {
                return gson.fromJson(jsonToParse.toString(), DeviceEventStatus::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<DeviceEventStatus> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<DeviceEventStatus>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Employee(
        var id: String?, 
        var name: String, 
        var username: String, 
        var zigReadPermissionKey: String?, 
        var avatar: ImageUrl?, 
        var permissions: ArrayList<Permission>, 
        var role: Role?, 
        var organization: Organization, 
        var places: ArrayList<Place>, 
        var bar: Bar?, 
        var zigTag: String?, 
        var deviceIds: ArrayList<String>?, 
        var blacklistEntrances: ArrayList<String>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Employee {
                return gson.fromJson(jsonToParse.toString(), Employee::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Employee> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Employee>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewEmployee(
        var name: String, 
        var username: String, 
        var zigReadPermissionKey: String?, 
        var permissions: ArrayList<Permission>, 
        var role: Role?, 
        var places: ArrayList<String>, 
        var bar: String?, 
        var blacklistEntrances: ArrayList<String>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewEmployee {
                return gson.fromJson(jsonToParse.toString(), NewEmployee::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewEmployee> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewEmployee>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EditMultipleEmployees(
        var username: String?, 
        var initialUsernameNumber: Int?, 
        var name: String?, 
        var initialNameNumber: Int?, 
        var roleSlug: String?, 
        var barId: String?, 
        var placeIds: ArrayList<String>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EditMultipleEmployees {
                return gson.fromJson(jsonToParse.toString(), EditMultipleEmployees::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EditMultipleEmployees> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EditMultipleEmployees>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MinimalEmployee(
        var id: String, 
        var name: String, 
        var avatar: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MinimalEmployee {
                return gson.fromJson(jsonToParse.toString(), MinimalEmployee::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MinimalEmployee> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MinimalEmployee>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Entrance(
        var id: String?, 
        var name: String, 
        var category: String, 
        var value: Int, 
        var image: ImageUrl?, 
        var minimumConsumptionValue: Int, 
        var active: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Entrance {
                return gson.fromJson(jsonToParse.toString(), Entrance::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Entrance> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Entrance>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EntranceSold(
        var id: String, 
        var name: String, 
        var image: ImageUrl?, 
        var count: Int, 
        var totalValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EntranceSold {
                return gson.fromJson(jsonToParse.toString(), EntranceSold::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EntranceSold> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EntranceSold>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RelationTicket(
        var names: String?, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RelationTicket {
                return gson.fromJson(jsonToParse.toString(), RelationTicket::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RelationTicket> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RelationTicket>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TicketSold(
        var id: String, 
        var name: String, 
        var averageValue: Int, 
        var perRelation: ArrayList<RelationTicket> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TicketSold {
                return gson.fromJson(jsonToParse.toString(), TicketSold::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TicketSold> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TicketSold>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Bilheteiro(
        var id: String, 
        var name: String, 
        var tickets: ArrayList<TicketSold> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Bilheteiro {
                return gson.fromJson(jsonToParse.toString(), Bilheteiro::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Bilheteiro> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Bilheteiro>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EntranceUser(
        var id: String, 
        var name: String, 
        var cpf: String, 
        var date: Calendar, 
        var productName: String, 
        var category: String, 
        var count: Int, 
        var unitValue: Int, 
        var employeeName: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EntranceUser {
                return gson.fromJson(jsonToParse.toString(), EntranceUser::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EntranceUser> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EntranceUser>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EventList(
        var totalClients: Int, 
        var averageTicket: Int, 
        var totalIncome: Int, 
        var futureEvents: Int, 
        var events: ArrayList<EventListEvents> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EventList {
                return gson.fromJson(jsonToParse.toString(), EventList::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EventList> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EventList>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EventListEvents(
        var id: String, 
        var name: String, 
        var status: EventState, 
        var begin: Calendar, 
        var end: Calendar?, 
        var image: ImageUrl?, 
        var tags: ArrayList<String>, 
        var averageTicket: Int?, 
        var income: Int?, 
        var totalPublic: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EventListEvents {
                return gson.fromJson(jsonToParse.toString(), EventListEvents::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EventListEvents> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EventListEvents>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class DayResume(
        var date: Calendar, 
        var withdrew: Int, 
        var _in: Int, 
        var _out: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): DayResume {
                return gson.fromJson(jsonToParse.toString(), DayResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<DayResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<DayResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ExtractResume(
        var available: Int, 
        var availableInTwoDays: Int, 
        var toBeReleased: Int, 
        var availableInMoreThanOneMonth: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ExtractResume {
                return gson.fromJson(jsonToParse.toString(), ExtractResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ExtractResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ExtractResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Fee(
        var zigMachineCredit: Float, 
        var zigMachineDebit: Float, 
        var inHouse: Float, 
        var app: Float, 
        var rechargeRemainings: Float, 
        var bonus: Float, 
        var minimumConsumption: Float 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Fee {
                return gson.fromJson(jsonToParse.toString(), Fee::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Fee> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Fee>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RechargeAvailable(
        var id: String, 
        var user: String, 
        var totalValue: Int, 
        var usedValue: Int, 
        var createdAt: Calendar, 
        var expiredAt: Calendar, 
        var fee: Float, 
        var availableValue: Int, 
        var withdrew: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RechargeAvailable {
                return gson.fromJson(jsonToParse.toString(), RechargeAvailable::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RechargeAvailable> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RechargeAvailable>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RechargeExpireResume(
        var toBeAvailable: Int, 
        var rechargesAvailable: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RechargeExpireResume {
                return gson.fromJson(jsonToParse.toString(), RechargeExpireResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RechargeExpireResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RechargeExpireResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ExtractDayResumeSection(
        var title: String, 
        var description: String?, 
        var tooltip: String?, 
        var date: Calendar?, 
        var values: ArrayList<ExtractDayResumeSectionValues> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ExtractDayResumeSection {
                return gson.fromJson(jsonToParse.toString(), ExtractDayResumeSection::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ExtractDayResumeSection> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ExtractDayResumeSection>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ExtractDayResumeSectionValues(
        var modality: String, 
        var totalValue: Int, 
        var tax: Float?, 
        var payValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ExtractDayResumeSectionValues {
                return gson.fromJson(jsonToParse.toString(), ExtractDayResumeSectionValues::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ExtractDayResumeSectionValues> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ExtractDayResumeSectionValues>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RechargeExpireDayResume(
        var expired: Int, 
        var date: Calendar, 
        var toExpire: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RechargeExpireDayResume {
                return gson.fromJson(jsonToParse.toString(), RechargeExpireDayResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RechargeExpireDayResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RechargeExpireDayResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class WithdrawBankAccount(
        var bank: String, 
        var cpfOrCnpj: String, 
        var account: String, 
        var agency: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): WithdrawBankAccount {
                return gson.fromJson(jsonToParse.toString(), WithdrawBankAccount::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<WithdrawBankAccount> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<WithdrawBankAccount>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Withdraw(
        var id: String, 
        var type: WithdrawType, 
        var value: Int, 
        var anticipationFee: Float?, 
        var status: WithdrawStatus, 
        var date: Calendar, 
        var paymentMethod: String?, 
        var proRataFee: Float? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Withdraw {
                return gson.fromJson(jsonToParse.toString(), Withdraw::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Withdraw> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Withdraw>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ExtractAdjust(
        var id: String, 
        var value: Int, 
        var obs: String?, 
        var payDate: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ExtractAdjust {
                return gson.fromJson(jsonToParse.toString(), ExtractAdjust::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ExtractAdjust> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ExtractAdjust>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ConsolidatedExtract(
        var sections: ArrayList<ConsolidatedExtractSection>, 
        var availableValue: Int, 
        var withdrewValue: Int, 
        var anticipatedValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ConsolidatedExtract {
                return gson.fromJson(jsonToParse.toString(), ConsolidatedExtract::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ConsolidatedExtract> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ConsolidatedExtract>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ConsolidatedExtractSection(
        var title: String, 
        var description: String, 
        var values: ArrayList<ConsolidatedExtractSectionValues> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ConsolidatedExtractSection {
                return gson.fromJson(jsonToParse.toString(), ConsolidatedExtractSection::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ConsolidatedExtractSection> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ConsolidatedExtractSection>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ConsolidatedExtractSectionValues(
        var title: String, 
        var hideIfZero: Boolean, 
        var totalValueUnit: ConsolidatedExtractSectionValuesTotalValueUnit, 
        var totalValue: Int?, 
        var maxFee: Float?, 
        var minFee: Float?, 
        var payValue: Int, 
        var highlighted: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ConsolidatedExtractSectionValues {
                return gson.fromJson(jsonToParse.toString(), ConsolidatedExtractSectionValues::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ConsolidatedExtractSectionValues> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ConsolidatedExtractSectionValues>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ZigMachineResume(
        var debit: Int, 
        var credit: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigMachineResume {
                return gson.fromJson(jsonToParse.toString(), ZigMachineResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigMachineResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigMachineResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class IncomeResume(
        var products: Int, 
        var entrances: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): IncomeResume {
                return gson.fromJson(jsonToParse.toString(), IncomeResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<IncomeResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<IncomeResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ReceiptResume(
        var cashReceipt: Int, 
        var debitReceipt: Int, 
        var creditReceipt: Int, 
        var voucherReceipt: Int, 
        var zigCreditReceipt: Int, 
        var zigDebitReceipt: Int, 
        var appReceipt: Int, 
        var dashboardReceipt: Int, 
        var anticipatedReceipt: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ReceiptResume {
                return gson.fromJson(jsonToParse.toString(), ReceiptResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ReceiptResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ReceiptResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MoneyReceived(
        var cash: Int, 
        var debit: Int, 
        var credit: Int, 
        var zigDebit: Int, 
        var zigCredit: Int, 
        var postEvent: Int, 
        var voucher: Int, 
        var dashboard: Int, 
        var anticipated: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MoneyReceived {
                return gson.fromJson(jsonToParse.toString(), MoneyReceived::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MoneyReceived> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MoneyReceived>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Cashier(
        var id: String, 
        var name: String, 
        var rechargeMoneyReceived: MoneyReceived?, 
        var postPaidMoneyReceived: MoneyReceived?, 
        var bleedings: ArrayList<CashierBleedings>, 
        var closedValues: CashierClosedValues?, 
        var cashZigTagDevolutions: Int, 
        var rechargeDevolutions: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Cashier {
                return gson.fromJson(jsonToParse.toString(), Cashier::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Cashier> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Cashier>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CashierBleedings(
        var id: String, 
        var author: String, 
        var date: Calendar, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CashierBleedings {
                return gson.fromJson(jsonToParse.toString(), CashierBleedings::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CashierBleedings> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CashierBleedings>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CashierClosedValues(
        var cash: Int, 
        var credit: Int, 
        var debit: Int, 
        var voucher: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CashierClosedValues {
                return gson.fromJson(jsonToParse.toString(), CashierClosedValues::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CashierClosedValues> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CashierClosedValues>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Client(
        var id: String, 
        var cpf: String, 
        var name: String, 
        var phone: String, 
        var zigCode: String?, 
        var appTransactionsValue: Int, 
        var housePostPaidTransactionsValue: Int, 
        var housePrePaidTransactionsValue: Int, 
        var bonusTransactionsValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Client {
                return gson.fromJson(jsonToParse.toString(), Client::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Client> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Client>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserTransactionProduct(
        var id: String, 
        var type: SellableThing, 
        var name: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserTransactionProduct {
                return gson.fromJson(jsonToParse.toString(), UserTransactionProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserTransactionProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserTransactionProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Payments(
        var bonus: Int, 
        var prePaid: Int, 
        var ppu: Int, 
        var postPaid: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Payments {
                return gson.fromJson(jsonToParse.toString(), Payments::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Payments> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Payments>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TransactionBuyers(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TransactionBuyers {
                return gson.fromJson(jsonToParse.toString(), TransactionBuyers::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TransactionBuyers> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TransactionBuyers>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserTransaction(
        var id: String, 
        var products: ArrayList<UserTransactionProduct>, 
        var seller: Seller, 
        var buyers: ArrayList<TransactionBuyers>, 
        var date: Calendar, 
        var refunded: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserTransaction {
                return gson.fromJson(jsonToParse.toString(), UserTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Debtor(
        var userId: String, 
        var userName: String, 
        var value: Int, 
        var paidValue: Int, 
        var phone: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Debtor {
                return gson.fromJson(jsonToParse.toString(), Debtor::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Debtor> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Debtor>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserBonusReport(
        var id: String, 
        var name: String, 
        var receivedValue: Int, 
        var spentValue: Int, 
        var givenBy: String, 
        var reason: String?, 
        var date: Calendar? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserBonusReport {
                return gson.fromJson(jsonToParse.toString(), UserBonusReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserBonusReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserBonusReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RechargeDetails(
        var id: String, 
        var totalValue: Int, 
        var usedValue: Int, 
        var isRefunded: Boolean, 
        var date: Calendar, 
        var user: String, 
        var method: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RechargeDetails {
                return gson.fromJson(jsonToParse.toString(), RechargeDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RechargeDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RechargeDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Payment(
        var method: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Payment {
                return gson.fromJson(jsonToParse.toString(), Payment::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Payment> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Payment>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PostDetails(
        var id: String, 
        var payments: ArrayList<Payment>, 
        var payers: ArrayList<String>, 
        var date: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PostDetails {
                return gson.fromJson(jsonToParse.toString(), PostDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PostDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PostDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ClientDetails(
        var id: String, 
        var name: String, 
        var email: String?, 
        var hasApp: Boolean, 
        var zigCode: String?, 
        var phone: String, 
        var cpf: String, 
        var avatar: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ClientDetails {
                return gson.fromJson(jsonToParse.toString(), ClientDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ClientDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ClientDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RechargeConsumption(
        var value: Int, 
        var user: String, 
        var method: RechargeConsumptionMethod?, 
        var author: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RechargeConsumption {
                return gson.fromJson(jsonToParse.toString(), RechargeConsumption::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RechargeConsumption> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RechargeConsumption>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MultipleUserIdsResult(
        var success: ArrayList<String>, 
        var error: ArrayList<MultipleUserIdsResultError> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MultipleUserIdsResult {
                return gson.fromJson(jsonToParse.toString(), MultipleUserIdsResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MultipleUserIdsResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MultipleUserIdsResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MultipleUserIdsResultError(
        var userId: String, 
        var reason: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MultipleUserIdsResultError {
                return gson.fromJson(jsonToParse.toString(), MultipleUserIdsResultError::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MultipleUserIdsResultError> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MultipleUserIdsResultError>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BillPayment(
        var method: PaymentMethod, 
        var value: Int, 
        var isBonus: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BillPayment {
                return gson.fromJson(jsonToParse.toString(), BillPayment::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BillPayment> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BillPayment>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundedRecharge(
        var id: String, 
        var totalValue: Int, 
        var usedValue: Int, 
        var date: Calendar, 
        var userName: String, 
        var authorName: String, 
        var refundedBy: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundedRecharge {
                return gson.fromJson(jsonToParse.toString(), RefundedRecharge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundedRecharge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundedRecharge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ActivationReturnReport(
        var cashValue: Int, 
        var cashQuantity: Int, 
        var products: ArrayList<ActivationReturnReportProducts> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ActivationReturnReport {
                return gson.fromJson(jsonToParse.toString(), ActivationReturnReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ActivationReturnReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ActivationReturnReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ActivationReturnReportProducts(
        var id: String, 
        var count: Int, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ActivationReturnReportProducts {
                return gson.fromJson(jsonToParse.toString(), ActivationReturnReportProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ActivationReturnReportProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ActivationReturnReportProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EventResumeSection(
        var title: String, 
        var description: String?, 
        var columns: ArrayList<String>, 
        var values: ArrayList<EventResumeSectionValues> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EventResumeSection {
                return gson.fromJson(jsonToParse.toString(), EventResumeSection::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EventResumeSection> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EventResumeSection>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EventResumeSectionValues(
        var name: String, 
        var tooltip: String?, 
        var values: ArrayList<EventResumeSectionValuesValues>, 
        var highlighted: Boolean, 
        var hideIfZero: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EventResumeSectionValues {
                return gson.fromJson(jsonToParse.toString(), EventResumeSectionValues::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EventResumeSectionValues> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EventResumeSectionValues>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EventResumeSectionValuesValues(
        var value: Float?, 
        var unit: EventResumeSectionValuesValuesUnit 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EventResumeSectionValuesValues {
                return gson.fromJson(jsonToParse.toString(), EventResumeSectionValuesValues::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EventResumeSectionValuesValues> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EventResumeSectionValuesValues>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UsedPromotion(
        var promotionId: String, 
        var promotionName: String, 
        var productId: String, 
        var productName: String, 
        var productCategory: String, 
        var totalDiscount: Int, 
        var uses: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UsedPromotion {
                return gson.fromJson(jsonToParse.toString(), UsedPromotion::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UsedPromotion> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UsedPromotion>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SoldEntranceWithEmployee(
        var employeeId: String, 
        var employeeName: String, 
        var entranceId: String, 
        var entranceName: String, 
        var totalValue: Int, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SoldEntranceWithEmployee {
                return gson.fromJson(jsonToParse.toString(), SoldEntranceWithEmployee::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SoldEntranceWithEmployee> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SoldEntranceWithEmployee>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundedProduct(
        var id: String, 
        var refundedAt: Calendar, 
        var name: String, 
        var category: String, 
        var unitValue: Int, 
        var count: Int, 
        var refundedBy: String, 
        var refundObs: String, 
        var buyers: ArrayList<String>, 
        var isCanceled: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundedProduct {
                return gson.fromJson(jsonToParse.toString(), RefundedProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundedProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundedProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TipRemovedList(
        var employee: MinimalEmployee, 
        var totalRemoved: Int, 
        var removals: ArrayList<TipRemovedListRemovals> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TipRemovedList {
                return gson.fromJson(jsonToParse.toString(), TipRemovedList::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TipRemovedList> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TipRemovedList>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TipRemovedListRemovals(
        var id: String, 
        var name: String, 
        var value: Int, 
        var reason: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TipRemovedListRemovals {
                return gson.fromJson(jsonToParse.toString(), TipRemovedListRemovals::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TipRemovedListRemovals> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TipRemovedListRemovals>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CardReturnEmployee(
        var id: String, 
        var name: String, 
        var cashReturn: CardReturnEmployeeCashReturn, 
        var productReturn: ArrayList<CardReturnEmployeeProductReturn> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CardReturnEmployee {
                return gson.fromJson(jsonToParse.toString(), CardReturnEmployee::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CardReturnEmployee> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CardReturnEmployee>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CardReturnEmployeeCashReturn(
        var count: Int, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CardReturnEmployeeCashReturn {
                return gson.fromJson(jsonToParse.toString(), CardReturnEmployeeCashReturn::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CardReturnEmployeeCashReturn> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CardReturnEmployeeCashReturn>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CardReturnEmployeeProductReturn(
        var id: String, 
        var count: Int, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CardReturnEmployeeProductReturn {
                return gson.fromJson(jsonToParse.toString(), CardReturnEmployeeProductReturn::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CardReturnEmployeeProductReturn> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CardReturnEmployeeProductReturn>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class DiscountEmployee(
        var id: String, 
        var name: String, 
        var discounts: ArrayList<DiscountEmployeeDiscounts> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): DiscountEmployee {
                return gson.fromJson(jsonToParse.toString(), DiscountEmployee::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<DiscountEmployee> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<DiscountEmployee>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class DiscountEmployeeDiscounts(
        var date: Calendar, 
        var value: Int, 
        var users: ArrayList<String>, 
        var products: ArrayList<String>, 
        var reason: String?, 
        var percentual: Float? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): DiscountEmployeeDiscounts {
                return gson.fromJson(jsonToParse.toString(), DiscountEmployeeDiscounts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<DiscountEmployeeDiscounts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<DiscountEmployeeDiscounts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TransferEmployee(
        var id: String, 
        var name: String, 
        var transfers: ArrayList<TransferEmployeeTransfers> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TransferEmployee {
                return gson.fromJson(jsonToParse.toString(), TransferEmployee::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TransferEmployee> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TransferEmployee>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TransferEmployeeTransfers(
        var date: Calendar, 
        var fromUser: String, 
        var toUser: String, 
        var products: ArrayList<String> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TransferEmployeeTransfers {
                return gson.fromJson(jsonToParse.toString(), TransferEmployeeTransfers::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TransferEmployeeTransfers> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TransferEmployeeTransfers>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ManualInvoice(
        var eventId: String, 
        var invoiceId: String, 
        var value: Int, 
        var paymentMethod: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ManualInvoice {
                return gson.fromJson(jsonToParse.toString(), ManualInvoice::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ManualInvoice> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ManualInvoice>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PostLimitChange(
        var userName: String, 
        var employeeId: String, 
        var employeeName: String, 
        var date: Calendar, 
        var limit: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PostLimitChange {
                return gson.fromJson(jsonToParse.toString(), PostLimitChange::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PostLimitChange> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PostLimitChange>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OpenedBillPayment(
        var user: OpenedBillPaymentUser, 
        var employee: OpenedBillPaymentEmployee, 
        var openedEventsDates: ArrayList<Calendar>, 
        var paymentId: String, 
        var method: OpenedBillPaymentMethod, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OpenedBillPayment {
                return gson.fromJson(jsonToParse.toString(), OpenedBillPayment::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OpenedBillPayment> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OpenedBillPayment>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OpenedBillPaymentUser(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OpenedBillPaymentUser {
                return gson.fromJson(jsonToParse.toString(), OpenedBillPaymentUser::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OpenedBillPaymentUser> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OpenedBillPaymentUser>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OpenedBillPaymentEmployee(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OpenedBillPaymentEmployee {
                return gson.fromJson(jsonToParse.toString(), OpenedBillPaymentEmployee::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OpenedBillPaymentEmployee> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OpenedBillPaymentEmployee>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RappiDiscount(
        var id: String, 
        var products: ArrayList<String>, 
        var value: Int, 
        var date: Calendar, 
        var employee: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RappiDiscount {
                return gson.fromJson(jsonToParse.toString(), RappiDiscount::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RappiDiscount> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RappiDiscount>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProduct(
        var id: String, 
        var image: String?, 
        var name: String, 
        var ncm: String, 
        var ncms: ArrayList<FiscalProductNcms>, 
        var slug: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProduct {
                return gson.fromJson(jsonToParse.toString(), FiscalProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProductNcms(
        var name: String, 
        var ncm: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProductNcms {
                return gson.fromJson(jsonToParse.toString(), FiscalProductNcms::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProductNcms> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProductNcms>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PlaceProductFiscalData(
        var ncm: String?, 
        var cest: String?, 
        var fiscalProfileId: String?, 
        var fiscalProductGroupId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PlaceProductFiscalData {
                return gson.fromJson(jsonToParse.toString(), PlaceProductFiscalData::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PlaceProductFiscalData> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PlaceProductFiscalData>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductFiscalData(
        var codigo: String?, 
        var ncm: String?, 
        var cest: String?, 
        var fiscalProfileId: String?, 
        var fiscalProductGroupId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductFiscalData {
                return gson.fromJson(jsonToParse.toString(), ProductFiscalData::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductFiscalData> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductFiscalData>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Invoice(
        var id: String, 
        var mode: InvoiceMode, 
        var issuedAt: Calendar, 
        var isIssued: Boolean, 
        var isIssuing: Boolean, 
        var error: String?, 
        var users: ArrayList<User>, 
        var pdfUrl: String?, 
        var imgUrl: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Invoice {
                return gson.fromJson(jsonToParse.toString(), Invoice::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Invoice> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Invoice>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class InvoiceWithEvent(
        var event: MinimalEvent, 
        var id: String, 
        var mode: InvoiceMode, 
        var issuedAt: Calendar, 
        var isIssued: Boolean, 
        var isIssuing: Boolean, 
        var error: String?, 
        var users: ArrayList<User>, 
        var pdfUrl: String?, 
        var imgUrl: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): InvoiceWithEvent {
                return gson.fromJson(jsonToParse.toString(), InvoiceWithEvent::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<InvoiceWithEvent> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<InvoiceWithEvent>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class IssueResult(
        var issuedInvoices: ArrayList<Invoice>, 
        var errors: ArrayList<String>, 
        var failedInvoiceCount: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): IssueResult {
                return gson.fromJson(jsonToParse.toString(), IssueResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<IssueResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<IssueResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SimpleIssueResult(
        var errors: ArrayList<String>, 
        var failedInvoiceCount: Int, 
        var success: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SimpleIssueResult {
                return gson.fromJson(jsonToParse.toString(), SimpleIssueResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SimpleIssueResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SimpleIssueResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfile(
        var id: String, 
        var name: String, 
        var org: FiscalProfileOrg, 
        var contact: FiscalProfileContact, 
        var address: FiscalProfileAddress, 
        var nfce: FiscalProfileNfce?, 
        var sat: FiscalProfileSat?, 
        var fiscalMode: FiscalMode? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfile {
                return gson.fromJson(jsonToParse.toString(), FiscalProfile::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfile> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfile>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileOrg(
        var cnpj: String, 
        var razaoSocial: String, 
        var nomeFantasia: String, 
        var regimeTributario: RegimeTributario, 
        var inscricaoEstadual: String, 
        var inscricaoMunicipal: String?, 
        var codigoMunicipio: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileOrg {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileOrg::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileOrg> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileOrg>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileContact(
        var email: String, 
        var telefone: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileContact {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileContact::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileContact> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileContact>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileAddress(
        var logradouro: String, 
        var numero: String, 
        var complemento: String, 
        var bairro: String, 
        var cep: String, 
        var municipio: String, 
        var uf: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileAddress {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileAddress::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileAddress> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileAddress>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileNfce(
        var cscNfceProducao: String, 
        var idNfceProducao: String, 
        var cscNfceHomologacao: String?, 
        var idNfceHomologacao: String?, 
        var certificado: ByteArray?, 
        var senhaCertificado: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileNfce {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileNfce::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileNfce> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileNfce>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileSat(
        var numeroSerieSat: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileSat {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileSat::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileSat> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileSat>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileMock(
        var id: String, 
        var name: String, 
        var org: FiscalProfileMockOrg, 
        var contact: FiscalProfileMockContact, 
        var address: FiscalProfileMockAddress, 
        var nfce: FiscalProfileMockNfce?, 
        var sat: FiscalProfileMockSat?, 
        var fiscalMode: FiscalMode? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileMock {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileMock::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileMock> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileMock>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileMockOrg(
        var cnpj: String, 
        var razaoSocial: String, 
        var nomeFantasia: String, 
        var regimeTributario: RegimeTributario, 
        var inscricaoEstadual: String, 
        var inscricaoMunicipal: String?, 
        var codigoMunicipio: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileMockOrg {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileMockOrg::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileMockOrg> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileMockOrg>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileMockContact(
        var email: String, 
        var telefone: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileMockContact {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileMockContact::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileMockContact> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileMockContact>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileMockAddress(
        var logradouro: String, 
        var numero: String, 
        var complemento: String, 
        var bairro: String, 
        var cep: String, 
        var municipio: String, 
        var uf: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileMockAddress {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileMockAddress::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileMockAddress> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileMockAddress>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileMockNfce(
        var cscNfceProducao: String, 
        var idNfceProducao: String, 
        var certificado: ByteArray?, 
        var senhaCertificado: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileMockNfce {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileMockNfce::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileMockNfce> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileMockNfce>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProfileMockSat(
        var numeroSerieSat: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProfileMockSat {
                return gson.fromJson(jsonToParse.toString(), FiscalProfileMockSat::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProfileMockSat> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProfileMockSat>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProductGroup(
        var id: String, 
        var name: String, 
        var cfop: String?, 
        var icms: FiscalProductGroupIcms?, 
        var pis: FiscalProductGroupPis?, 
        var cofins: FiscalProductGroupCofins?, 
        var service: FiscalProductGroupService?, 
        var type: FiscalProductGroupType 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProductGroup {
                return gson.fromJson(jsonToParse.toString(), FiscalProductGroup::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProductGroup> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProductGroup>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProductGroupIcms(
        var origem: String?, 
        var csosn: String?, 
        var cst: String?, 
        var aliquota: Float?, 
        var baseCalculo: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProductGroupIcms {
                return gson.fromJson(jsonToParse.toString(), FiscalProductGroupIcms::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProductGroupIcms> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProductGroupIcms>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProductGroupPis(
        var cst: String?, 
        var baseCalculo: String?, 
        var aliquotaPerc: Float?, 
        var quantidadeVendida: String?, 
        var aliquotaReais: Float? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProductGroupPis {
                return gson.fromJson(jsonToParse.toString(), FiscalProductGroupPis::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProductGroupPis> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProductGroupPis>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProductGroupCofins(
        var cst: String?, 
        var baseCalculo: String?, 
        var aliquotaPerc: Float?, 
        var quantidadeVendida: String?, 
        var aliquotaReais: Float? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProductGroupCofins {
                return gson.fromJson(jsonToParse.toString(), FiscalProductGroupCofins::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProductGroupCofins> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProductGroupCofins>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FiscalProductGroupService(
        var aliquota: Float, 
        var tipoServico: String, 
        var codigoTributacaoMunicipio: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FiscalProductGroupService {
                return gson.fromJson(jsonToParse.toString(), FiscalProductGroupService::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FiscalProductGroupService> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FiscalProductGroupService>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SalesIssuedInPeriod(
        var sales: Int, 
        var invoices: Int, 
        var percent: Float 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SalesIssuedInPeriod {
                return gson.fromJson(jsonToParse.toString(), SalesIssuedInPeriod::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SalesIssuedInPeriod> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SalesIssuedInPeriod>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NotEmmitedTransaction(
        var id: String, 
        var products: ArrayList<NotEmmitedTransactionProducts>, 
        var date: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NotEmmitedTransaction {
                return gson.fromJson(jsonToParse.toString(), NotEmmitedTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NotEmmitedTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NotEmmitedTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NotEmmitedTransactionProducts(
        var id: String, 
        var name: String, 
        var unitValue: Int, 
        var count: Int, 
        var discountValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NotEmmitedTransactionProducts {
                return gson.fromJson(jsonToParse.toString(), NotEmmitedTransactionProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NotEmmitedTransactionProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NotEmmitedTransactionProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ManualRequest(
        var fiscalProfileId: String, 
        var paymentMethod: String?, 
        var products: ArrayList<ManualRequestProducts> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ManualRequest {
                return gson.fromJson(jsonToParse.toString(), ManualRequest::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ManualRequest> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ManualRequest>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ManualRequestProducts(
        var ncm: String, 
        var cest: String?, 
        var fiscalProductGroupId: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ManualRequestProducts {
                return gson.fromJson(jsonToParse.toString(), ManualRequestProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ManualRequestProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ManualRequestProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NotEmittedProductOrCombo(
        var id: String, 
        var name: String, 
        var type: NotEmittedProductOrComboType, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NotEmittedProductOrCombo {
                return gson.fromJson(jsonToParse.toString(), NotEmittedProductOrCombo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NotEmittedProductOrCombo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NotEmittedProductOrCombo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class InvoicePrintData(
        var printData: ArrayList<ByteArray>?, 
        var epsonPrintData: ArrayList<ByteArray>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): InvoicePrintData {
                return gson.fromJson(jsonToParse.toString(), InvoicePrintData::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<InvoicePrintData> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<InvoicePrintData>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewInventory(
        var date: Calendar, 
        var isTotal: Boolean, 
        var createdBy: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewInventory {
                return gson.fromJson(jsonToParse.toString(), NewInventory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewInventory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewInventory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Inventory(
        var id: String, 
        var status: InventoryStatus, 
        var storages: ArrayList<InventoryStorages>, 
        var date: Calendar, 
        var isTotal: Boolean, 
        var createdBy: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Inventory {
                return gson.fromJson(jsonToParse.toString(), Inventory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Inventory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Inventory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class InventoryStorages(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): InventoryStorages {
                return gson.fromJson(jsonToParse.toString(), InventoryStorages::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<InventoryStorages> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<InventoryStorages>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewInventoryProduct(
        var productId: String, 
        var storageId: String, 
        var inventoryCount: Int?, 
        var inventoryUnitValue: Int?, 
        var realCount: Int?, 
        var realUnitValue: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewInventoryProduct {
                return gson.fromJson(jsonToParse.toString(), NewInventoryProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewInventoryProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewInventoryProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class InventoryProduct(
        var productName: String, 
        var productCategory: String, 
        var productCategoryId: String, 
        var productId: String, 
        var storageId: String, 
        var inventoryCount: Int?, 
        var inventoryUnitValue: Int?, 
        var realCount: Int?, 
        var realUnitValue: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): InventoryProduct {
                return gson.fromJson(jsonToParse.toString(), InventoryProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<InventoryProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<InventoryProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TopSelling(
        var name: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TopSelling {
                return gson.fromJson(jsonToParse.toString(), TopSelling::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TopSelling> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TopSelling>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TopSellingCashier(
        var id: String, 
        var name: String, 
        var rechargeMoneyReceived: Int, 
        var postPaidMoneyReceived: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TopSellingCashier {
                return gson.fromJson(jsonToParse.toString(), TopSellingCashier::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TopSellingCashier> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TopSellingCashier>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class LiveResume(
        var womenInside: Int, 
        var menInside: Int, 
        var otherInside: Int, 
        var menOutside: Int, 
        var womenOutside: Int, 
        var otherOutside: Int, 
        var eventIncome: Int, 
        var productsSold: Int, 
        var top5SellingProductsByQuantity: ArrayList<TopSelling>, 
        var top5SellingProductsByPrice: ArrayList<TopSelling>, 
        var topSellingBar: TopSelling?, 
        var topSellingEmployee: TopSelling?, 
        var averageTicket: Int, 
        var top5Clients: ArrayList<TopSelling>, 
        var topSellingCashier: TopSellingCashier?, 
        var topDeliveryBarman: TopSelling?, 
        var topSellingNonBarmanEmployee: TopSelling? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): LiveResume {
                return gson.fromJson(jsonToParse.toString(), LiveResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<LiveResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<LiveResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Public(
        var womenInside: Int, 
        var menInside: Int, 
        var otherInside: Int, 
        var menOutside: Int, 
        var womenOutside: Int, 
        var otherOutside: Int, 
        var ticketZero: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Public {
                return gson.fromJson(jsonToParse.toString(), Public::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Public> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Public>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TopSellingProducts(
        var top5SellingProductsByQuantity: ArrayList<TopSelling>, 
        var top5SellingProductsByPrice: ArrayList<TopSelling> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TopSellingProducts {
                return gson.fromJson(jsonToParse.toString(), TopSellingProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TopSellingProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TopSellingProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TotalReceipt(
        var bar: Receipt, 
        var entrances: Receipt, 
        var rechargeConsumptions: Int, 
        var entranceConsumptions: Int, 
        var barConsumptions: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TotalReceipt {
                return gson.fromJson(jsonToParse.toString(), TotalReceipt::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TotalReceipt> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TotalReceipt>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Receipt(
        var cash: Int, 
        var credit: Int, 
        var debit: Int, 
        var postEvent: Int, 
        var voucher: Int, 
        var app: Int, 
        var opened: Int, 
        var dashboard: Int, 
        var anticipated: Int, 
        var rappi: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Receipt {
                return gson.fromJson(jsonToParse.toString(), Receipt::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Receipt> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Receipt>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ConsumptionTimeline(
        var start: Calendar, 
        var intervalInMinutes: Int, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ConsumptionTimeline {
                return gson.fromJson(jsonToParse.toString(), ConsumptionTimeline::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ConsumptionTimeline> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ConsumptionTimeline>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ConsumptionResume(
        var minimumConsumption: Int, 
        var bonus: Int, 
        var normal: Int, 
        var openedBils: Int, 
        var creditFromPreviousEvents: Int, 
        var paidInFutureEvents: Int, 
        var app: Int, 
        var productsSold: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ConsumptionResume {
                return gson.fromJson(jsonToParse.toString(), ConsumptionResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ConsumptionResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ConsumptionResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SelledProduct(
        var name: String, 
        var count: Int, 
        var totalValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SelledProduct {
                return gson.fromJson(jsonToParse.toString(), SelledProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SelledProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SelledProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PublicDetail(
        var consumption: Int, 
        var new: Int, 
        var old: Int, 
        var zero: Int, 
        var consumptionPercetage: Float, 
        var inside: Int, 
        var total: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PublicDetail {
                return gson.fromJson(jsonToParse.toString(), PublicDetail::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PublicDetail> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PublicDetail>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewPublic(
        var totalConsumption: Int?, 
        var totalPublic: Int?, 
        var male: PublicDetail, 
        var female: PublicDetail, 
        var other: PublicDetail 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewPublic {
                return gson.fromJson(jsonToParse.toString(), NewPublic::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewPublic> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewPublic>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Organization(
        var id: String, 
        var name: String, 
        var description: String?, 
        var username: String, 
        var icon: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Organization {
                return gson.fromJson(jsonToParse.toString(), Organization::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Organization> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Organization>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BankAccount(
        var bank: String, 
        var accountName: String, 
        var cnpj: String, 
        var type: String, 
        var agency: String, 
        var account: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BankAccount {
                return gson.fromJson(jsonToParse.toString(), BankAccount::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BankAccount> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BankAccount>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PlaceContract(
        var fee: Fee, 
        var createdAt: Calendar, 
        var account: BankAccount? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PlaceContract {
                return gson.fromJson(jsonToParse.toString(), PlaceContract::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PlaceContract> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PlaceContract>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Couvert(
        var value: Int, 
        var name: String, 
        var category: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Couvert {
                return gson.fromJson(jsonToParse.toString(), Couvert::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Couvert> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Couvert>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Place(
        var id: String?, 
        var name: String, 
        var image: ImageUrl?, 
        var postPaidLimit: Int?, 
        var maleCouvert: Couvert?, 
        var femaleCouvert: Couvert?, 
        var bars: ArrayList<Bar>, 
        var tip: Float, 
        var zigTagProduct: PlaceProduct?, 
        var sellVisualizationFormat: PlaceSellVisualizationFormat, 
        var fiscalPrinters: ArrayList<FiscalPrinter>, 
        var localServerIp: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Place {
                return gson.fromJson(jsonToParse.toString(), Place::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Place> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Place>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewPlace(
        var id: String?, 
        var name: String, 
        var postPaidLimit: Int?, 
        var maleCouvert: Couvert?, 
        var femaleCouvert: Couvert?, 
        var bars: ArrayList<Bar>, 
        var tip: Float, 
        var zigTagProductId: String?, 
        var sellVisualizationFormat: NewPlaceSellVisualizationFormat, 
        var localServerIp: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewPlace {
                return gson.fromJson(jsonToParse.toString(), NewPlace::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewPlace> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewPlace>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PlaceDetails(
        var place: Place, 
        var products: ArrayList<PlaceProduct>, 
        var combos: ArrayList<PlaceCombo> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PlaceDetails {
                return gson.fromJson(jsonToParse.toString(), PlaceDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PlaceDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PlaceDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PlaceFeature(
        var id: String, 
        var name: String, 
        var isEditableByPlace: Boolean, 
        var isActive: Boolean, 
        var description: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PlaceFeature {
                return gson.fromJson(jsonToParse.toString(), PlaceFeature::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PlaceFeature> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PlaceFeature>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EverestConfig(
        var host: String, 
        var user: String, 
        var password: String, 
        var cnpj: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EverestConfig {
                return gson.fromJson(jsonToParse.toString(), EverestConfig::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EverestConfig> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EverestConfig>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PosTransaction(
        var brand: String?, 
        var value: Int, 
        var lastDigits: String?, 
        var date: Calendar, 
        var acquirerUid: String, 
        var method: PosTransactionMethodMethod 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PosTransaction {
                return gson.fromJson(jsonToParse.toString(), PosTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PosTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PosTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PaginationPosTransaction(
        var currentPage: Int, 
        var itemsPerPage: Int, 
        var totalItems: Int, 
        var _data: ArrayList<PosTransaction> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PaginationPosTransaction {
                return gson.fromJson(jsonToParse.toString(), PaginationPosTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PaginationPosTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PaginationPosTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PosMachineDayResume(
        var soldValue: Int, 
        var date: Calendar, 
        var brand: String?, 
        var method: PosTransactionMethodMethod 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PosMachineDayResume {
                return gson.fromJson(jsonToParse.toString(), PosMachineDayResume::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PosMachineDayResume> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PosMachineDayResume>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PosTransactionMethod(
        var method: PosTransactionMethodMethod 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PosTransactionMethod {
                return gson.fromJson(jsonToParse.toString(), PosTransactionMethod::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PosTransactionMethod> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PosTransactionMethod>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductBarEntry(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductBarEntry {
                return gson.fromJson(jsonToParse.toString(), ProductBarEntry::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductBarEntry> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductBarEntry>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PlaceProduct(
        var id: String?, 
        var name: String, 
        var category: String, 
        var categoryId: String, 
        var value: Int, 
        var image: ImageUrl?, 
        var bars: ArrayList<ProductBarEntry>, 
        var active: Boolean, 
        var hasAlcohol: Boolean?, 
        var fiscalData: PlaceProductFiscalData?, 
        var type: String, 
        var minimumConsumptionValue: Int?, 
        var hasProductionRule: Boolean, 
        var proportionalValue: ProportionalValue?, 
        var mountableDescription: MountableDescription?, 
        var fiscalCode: String?, 
        var kind: ProductKind 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PlaceProduct {
                return gson.fromJson(jsonToParse.toString(), PlaceProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PlaceProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PlaceProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Product(
        var id: String?, 
        var name: String, 
        var category: String, 
        var value: Int?, 
        var image: ImageUrl?, 
        var hasAlcohol: Boolean?, 
        var type: String, 
        var fiscalCode: String?, 
        var kind: ProductKind 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Product {
                return gson.fromJson(jsonToParse.toString(), Product::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Product> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Product>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewProduct(
        var name: String, 
        var category: String, 
        var hasAlcohol: Boolean, 
        var fiscalCode: String?, 
        var kindId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewProduct {
                return gson.fromJson(jsonToParse.toString(), NewProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EditedProduct(
        var id: String, 
        var name: String, 
        var category: String, 
        var hasAlcohol: Boolean, 
        var fiscalCode: String?, 
        var kindId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EditedProduct {
                return gson.fromJson(jsonToParse.toString(), EditedProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EditedProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EditedProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EditedResults(
        var success: ArrayList<EditedProduct>, 
        var error: ArrayList<EditedResultsError> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EditedResults {
                return gson.fromJson(jsonToParse.toString(), EditedResults::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EditedResults> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EditedResults>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EditedResultsError(
        var product: EditedProduct, 
        var reason: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EditedResultsError {
                return gson.fromJson(jsonToParse.toString(), EditedResultsError::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EditedResultsError> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EditedResultsError>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProportionalValue(
        var quantity: Int, 
        var value: Int, 
        var unit: ProportionalValueUnit 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProportionalValue {
                return gson.fromJson(jsonToParse.toString(), ProportionalValue::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProportionalValue> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProportionalValue>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewPlaceProduct(
        var name: String, 
        var category: String, 
        var hasAlcohol: Boolean, 
        var fiscalData: PlaceProductFiscalData?, 
        var value: Int?, 
        var proportionalValue: ProportionalValue?, 
        var mountableDescription: NewMountableDescription?, 
        var fiscalCode: String?, 
        var kindId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewPlaceProduct {
                return gson.fromJson(jsonToParse.toString(), NewPlaceProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewPlaceProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewPlaceProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EditProductImage(
        var imageId: String?, 
        var _data: ByteArray? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EditProductImage {
                return gson.fromJson(jsonToParse.toString(), EditProductImage::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EditProductImage> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EditProductImage>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Supply(
        var id: String, 
        var name: String, 
        var bars: ArrayList<ProductBarEntry> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Supply {
                return gson.fromJson(jsonToParse.toString(), Supply::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Supply> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Supply>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OrganizationProduct(
        var id: String, 
        var places: ArrayList<OrganizationProductPlaces>, 
        var name: String, 
        var category: String, 
        var categoryId: String, 
        var hasAlcohol: Boolean?, 
        var type: String, 
        var image: ImageUrl?, 
        var fiscalCode: String?, 
        var kind: ProductKind 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OrganizationProduct {
                return gson.fromJson(jsonToParse.toString(), OrganizationProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OrganizationProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OrganizationProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OrganizationProductPlaces(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OrganizationProductPlaces {
                return gson.fromJson(jsonToParse.toString(), OrganizationProductPlaces::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OrganizationProductPlaces> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OrganizationProductPlaces>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class InputProduct(
        var productId: String, 
        var storageId: String, 
        var unitCostValue: Int?, 
        var obs: String?, 
        var count: Int, 
        var date: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): InputProduct {
                return gson.fromJson(jsonToParse.toString(), InputProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<InputProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<InputProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductRule(
        var id: String, 
        var inputs: ArrayList<ProductRuleInputs>, 
        var outputs: ArrayList<ProductRuleOutputs> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductRule {
                return gson.fromJson(jsonToParse.toString(), ProductRule::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductRule> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductRule>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductRuleInputs(
        var id: String, 
        var count: Int, 
        var loss: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductRuleInputs {
                return gson.fromJson(jsonToParse.toString(), ProductRuleInputs::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductRuleInputs> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductRuleInputs>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductRuleOutputs(
        var id: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductRuleOutputs {
                return gson.fromJson(jsonToParse.toString(), ProductRuleOutputs::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductRuleOutputs> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductRuleOutputs>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductKind(
        var id: String, 
        var masterKind: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductKind {
                return gson.fromJson(jsonToParse.toString(), ProductKind::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductKind> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductKind>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SuggestedImages(
        var tags: ArrayList<String>, 
        var id: String, 
        var url: String, 
        var weight: Float 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SuggestedImages {
                return gson.fromJson(jsonToParse.toString(), SuggestedImages::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SuggestedImages> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SuggestedImages>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductImageSearch(
        var name: String, 
        var category: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductImageSearch {
                return gson.fromJson(jsonToParse.toString(), ProductImageSearch::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductImageSearch> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductImageSearch>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PromotionInfo(
        var id: String, 
        var products: ArrayList<String>?, 
        var name: String, 
        var description: String, 
        var productIds: ArrayList<String>?, 
        var discount: Float, 
        var expiresIn: Int?, 
        var maxUses: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PromotionInfo {
                return gson.fromJson(jsonToParse.toString(), PromotionInfo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PromotionInfo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PromotionInfo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewPromotionInfo(
        var name: String, 
        var description: String, 
        var productIds: ArrayList<String>?, 
        var discount: Float, 
        var expiresIn: Int?, 
        var maxUses: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewPromotionInfo {
                return gson.fromJson(jsonToParse.toString(), NewPromotionInfo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewPromotionInfo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewPromotionInfo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MultipleUserPromotionResult(
        var success: ArrayList<String>, 
        var error: ArrayList<MultipleUserPromotionResultError> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MultipleUserPromotionResult {
                return gson.fromJson(jsonToParse.toString(), MultipleUserPromotionResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MultipleUserPromotionResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MultipleUserPromotionResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MultipleUserPromotionResultError(
        var cpfOrPhone: String, 
        var reason: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MultipleUserPromotionResultError {
                return gson.fromJson(jsonToParse.toString(), MultipleUserPromotionResultError::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MultipleUserPromotionResultError> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MultipleUserPromotionResultError>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PromotionUser(
        var id: String, 
        var name: String, 
        var cpf: String, 
        var uses: Int, 
        var promotionId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PromotionUser {
                return gson.fromJson(jsonToParse.toString(), PromotionUser::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PromotionUser> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PromotionUser>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Promoter(
        var id: String, 
        var name: String, 
        var image: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Promoter {
                return gson.fromJson(jsonToParse.toString(), Promoter::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Promoter> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Promoter>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Relation(
        var id: String?, 
        var user: User?, 
        var cpf: String?, 
        var email: String?, 
        var name: String?, 
        var zigCode: String?, 
        var phone: String?, 
        var description: String, 
        var promoter: Promoter?, 
        var employee: EmployeeBase?, 
        var couvertPercentage: Float?, 
        var entrances: ArrayList<String>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Relation {
                return gson.fromJson(jsonToParse.toString(), Relation::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Relation> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Relation>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EmployeeReportItem(
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EmployeeReportItem {
                return gson.fromJson(jsonToParse.toString(), EmployeeReportItem::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EmployeeReportItem> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EmployeeReportItem>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EmployeeReportItemWithTotal(
        var total: Int, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EmployeeReportItemWithTotal {
                return gson.fromJson(jsonToParse.toString(), EmployeeReportItemWithTotal::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EmployeeReportItemWithTotal> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EmployeeReportItemWithTotal>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EmployeeReport(
        var totals: EmployeeReportTotals, 
        var employees: ArrayList<EmployeeReportEmployees> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EmployeeReport {
                return gson.fromJson(jsonToParse.toString(), EmployeeReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EmployeeReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EmployeeReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EmployeeReportTotals(
        var sells: Int, 
        var financialOperations: Int, 
        var tip: Int, 
        var refunds: Int, 
        var checkIns: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EmployeeReportTotals {
                return gson.fromJson(jsonToParse.toString(), EmployeeReportTotals::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EmployeeReportTotals> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EmployeeReportTotals>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EmployeeReportEmployees(
        var id: String, 
        var name: String, 
        var eventNames: ArrayList<String>, 
        var role: String?, 
        var eventRoles: ArrayList<String>, 
        var avatar: ImageUrl?, 
        var sells: EmployeeReportItemWithTotal, 
        var financialOperations: EmployeeReportItemWithTotal, 
        var financialOperationsClosedValue: Int, 
        var tipValue: Int, 
        var refunds: EmployeeReportItemWithTotal, 
        var sellRefunds: EmployeeReportItemWithTotal, 
        var checkIns: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EmployeeReportEmployees {
                return gson.fromJson(jsonToParse.toString(), EmployeeReportEmployees::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EmployeeReportEmployees> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EmployeeReportEmployees>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FinanceBiReport(
        var events: ArrayList<FinanceBiReportEvents> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FinanceBiReport {
                return gson.fromJson(jsonToParse.toString(), FinanceBiReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FinanceBiReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FinanceBiReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FinanceBiReportEvents(
        var id: String, 
        var date: Calendar, 
        var incomes: ArrayList<FinanceBiReportEventsIncomes>, 
        var bonus: Int, 
        var ticketZero: Int, 
        var female: Int, 
        var male: Int, 
        var other: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FinanceBiReportEvents {
                return gson.fromJson(jsonToParse.toString(), FinanceBiReportEvents::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FinanceBiReportEvents> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FinanceBiReportEvents>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FinanceBiReportEventsIncomes(
        var value: Int, 
        var method: FinanceBiMethod 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FinanceBiReportEventsIncomes {
                return gson.fromJson(jsonToParse.toString(), FinanceBiReportEventsIncomes::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FinanceBiReportEventsIncomes> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FinanceBiReportEventsIncomes>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ClientByReport(
        var events: ArrayList<ClientByReportEvents>, 
        var topIncomeClients: ArrayList<ClientByReportTopIncomeClients> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ClientByReport {
                return gson.fromJson(jsonToParse.toString(), ClientByReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ClientByReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ClientByReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ClientByReportEvents(
        var id: String, 
        var date: Calendar, 
        var male: Int, 
        var female: Int, 
        var other: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ClientByReportEvents {
                return gson.fromJson(jsonToParse.toString(), ClientByReportEvents::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ClientByReportEvents> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ClientByReportEvents>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ClientByReportTopIncomeClients(
        var id: String, 
        var name: String, 
        var phone: String, 
        var cpf: String, 
        var income: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ClientByReportTopIncomeClients {
                return gson.fromJson(jsonToParse.toString(), ClientByReportTopIncomeClients::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ClientByReportTopIncomeClients> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ClientByReportTopIncomeClients>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductBiReport(
        var id: String, 
        var name: String, 
        var categoryId: String, 
        var categoryName: String, 
        var image: ImageUrl?, 
        var sellCount: Int, 
        var profit: Int, 
        var consumption: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductBiReport {
                return gson.fromJson(jsonToParse.toString(), ProductBiReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductBiReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductBiReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewReserve(
        var name: String, 
        var estimatedPeopleCount: Int, 
        var obs: String, 
        var date: Calendar, 
        var reserveValue: Int?, 
        var minimumConsumptionValue: Int, 
        var promotionIds: ArrayList<String> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewReserve {
                return gson.fromJson(jsonToParse.toString(), NewReserve::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewReserve> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewReserve>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Reserve(
        var id: String, 
        var name: String, 
        var estimatedPeopleCount: Int, 
        var realPeopleCount: Int, 
        var obs: String, 
        var date: Calendar, 
        var reserveValue: Int?, 
        var minimumConsumptionValue: Int, 
        var promotions: ArrayList<ReservePromotions> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Reserve {
                return gson.fromJson(jsonToParse.toString(), Reserve::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Reserve> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Reserve>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ReservePromotions(
        var id: String, 
        var name: String, 
        var description: String?, 
        var discount: Float 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ReservePromotions {
                return gson.fromJson(jsonToParse.toString(), ReservePromotions::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ReservePromotions> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ReservePromotions>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ReserveDiscountDetail(
        var fiscalProductGroupId: String?, 
        var fiscalProductGroup: String?, 
        var value: Int, 
        var ncm: String?, 
        var cest: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ReserveDiscountDetail {
                return gson.fromJson(jsonToParse.toString(), ReserveDiscountDetail::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ReserveDiscountDetail> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ReserveDiscountDetail>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ReserveDetail(
        var discounts: ArrayList<ReserveDiscountDetail>, 
        var users: ArrayList<ReserveDetailUsers>, 
        var invoices: ArrayList<ReserveDetailInvoices> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ReserveDetail {
                return gson.fromJson(jsonToParse.toString(), ReserveDetail::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ReserveDetail> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ReserveDetail>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ReserveDetailUsers(
        var id: String, 
        var name: String, 
        var addedAt: Calendar, 
        var isInside: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ReserveDetailUsers {
                return gson.fromJson(jsonToParse.toString(), ReserveDetailUsers::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ReserveDetailUsers> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ReserveDetailUsers>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ReserveDetailInvoices(
        var id: String, 
        var date: Calendar, 
        var imgUrl: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ReserveDetailInvoices {
                return gson.fromJson(jsonToParse.toString(), ReserveDetailInvoices::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ReserveDetailInvoices> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ReserveDetailInvoices>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ReserveReportRow(
        var id: String, 
        var name: String, 
        var estimatedPeopleCount: Int, 
        var realPeopleCount: Int, 
        var totalConsumed: Int, 
        var totalDiscount: Int, 
        var totalPaidOrRecharged: Int, 
        var minimumConsumptionGiven: Int, 
        var minimumConsumptionUsed: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ReserveReportRow {
                return gson.fromJson(jsonToParse.toString(), ReserveReportRow::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ReserveReportRow> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ReserveReportRow>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class StorageProduct(
        var id: String, 
        var name: String, 
        var quantity: Int, 
        var category: String, 
        var categoryId: String, 
        var image: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): StorageProduct {
                return gson.fromJson(jsonToParse.toString(), StorageProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<StorageProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<StorageProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class User(
        var id: String, 
        var cpf: String, 
        var name: String, 
        var avatar: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): User {
                return gson.fromJson(jsonToParse.toString(), User::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<User> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<User>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserFilter(
        var type: UserFilterType, 
        var filter: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserFilter {
                return gson.fromJson(jsonToParse.toString(), UserFilter::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserFilter> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserFilter>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MinimalEvent(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MinimalEvent {
                return gson.fromJson(jsonToParse.toString(), MinimalEvent::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MinimalEvent> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MinimalEvent>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MinimalEventDetails(
        var id: String, 
        var name: String, 
        var beginAt: Calendar?, 
        var endAt: Calendar? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MinimalEventDetails {
                return gson.fromJson(jsonToParse.toString(), MinimalEventDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MinimalEventDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MinimalEventDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserDetails(
        var recharges: ArrayList<UserRechargeWithEvent>, 
        var transactions: ArrayList<UserDetailsTransactions>, 
        var postPayments: ArrayList<PostPaymentEvent>, 
        var cpf: String, 
        var phone: String, 
        var email: String?, 
        var checkInTime: Calendar?, 
        var checkOutTime: Calendar? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserDetails {
                return gson.fromJson(jsonToParse.toString(), UserDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserDetailsTransactions(
        var id: String, 
        var userValue: Int, 
        var date: Calendar, 
        var isShared: Boolean, 
        var isRefunded: Boolean, 
        var refundedBy: String?, 
        var sellerName: String, 
        var event: MinimalEventDetails, 
        var tipValue: Int, 
        var consumptionWithCreditFromOtherEvents: Int, 
        var paidByApp: Boolean, 
        var products: ArrayList<UserDetailsTransactionsProducts> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserDetailsTransactions {
                return gson.fromJson(jsonToParse.toString(), UserDetailsTransactions::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserDetailsTransactions> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserDetailsTransactions>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserDetailsTransactionsProducts(
        var name: String, 
        var count: Int, 
        var value: Int, 
        var discountValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserDetailsTransactionsProducts {
                return gson.fromJson(jsonToParse.toString(), UserDetailsTransactionsProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserDetailsTransactionsProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserDetailsTransactionsProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserRecharge(
        var totalValue: Int, 
        var usedValue: Int, 
        var authorName: String, 
        var date: Calendar, 
        var isBonus: Boolean, 
        var method: UserRechargeMethod?, 
        var refunded: Boolean, 
        var expired: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserRecharge {
                return gson.fromJson(jsonToParse.toString(), UserRecharge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserRecharge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserRecharge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserRechargeWithEvent(
        var event: MinimalEvent, 
        var totalValue: Int, 
        var usedValue: Int, 
        var authorName: String, 
        var date: Calendar, 
        var isBonus: Boolean, 
        var method: UserRechargeMethod?, 
        var refunded: Boolean, 
        var expired: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserRechargeWithEvent {
                return gson.fromJson(jsonToParse.toString(), UserRechargeWithEvent::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserRechargeWithEvent> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserRechargeWithEvent>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewUserRecharge(
        var type: NewUserRechargeType, 
        var reason: String?, 
        var value: Int, 
        var cpf: String, 
        var name: String?, 
        var email: String?, 
        var phone: String?, 
        var isForeign: Boolean?, 
        var gender: NewUserRechargeGender 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewUserRecharge {
                return gson.fromJson(jsonToParse.toString(), NewUserRecharge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewUserRecharge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewUserRecharge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class GiveRechargeError(
        var cpf: String, 
        var err: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): GiveRechargeError {
                return gson.fromJson(jsonToParse.toString(), GiveRechargeError::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<GiveRechargeError> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<GiveRechargeError>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EventUser(
        var id: String, 
        var hasApp: Boolean, 
        var checkInTime: Calendar, 
        var name: String, 
        var email: String?, 
        var gender: String?, 
        var phone: String, 
        var cpf: String, 
        var zigCode: String?, 
        var checkOutTime: Calendar? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EventUser {
                return gson.fromJson(jsonToParse.toString(), EventUser::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EventUser> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EventUser>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SearchFilters(
        var gender: ArrayList<String>, 
        var consumptionVolume: Int, 
        var presences: Int, 
        var presenceDays: ArrayList<Int>, 
        var productConsumption: ArrayList<SearchFiltersProductConsumption>, 
        var notUsedRecharges: Int?, 
        var since: Calendar, 
        var until: Calendar, 
        var name: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SearchFilters {
                return gson.fromJson(jsonToParse.toString(), SearchFilters::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SearchFilters> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SearchFilters>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SearchFiltersProductConsumption(
        var id: String, 
        var amount: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SearchFiltersProductConsumption {
                return gson.fromJson(jsonToParse.toString(), SearchFiltersProductConsumption::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SearchFiltersProductConsumption> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SearchFiltersProductConsumption>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SearchResult(
        var id: String, 
        var name: String, 
        var email: String?, 
        var birthdate: Calendar?, 
        var cpf: String, 
        var phone: String, 
        var hasApp: Boolean, 
        var consumption: Int, 
        var presences: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SearchResult {
                return gson.fromJson(jsonToParse.toString(), SearchResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SearchResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SearchResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserDetailsByEvents(
        var event: MinimalEventDetails, 
        var recharges: ArrayList<UserRecharge>, 
        var transactions: ArrayList<UserDetailsByEventsTransactions>, 
        var postPayments: ArrayList<PostPaymentEvent>, 
        var cpf: String, 
        var phone: String, 
        var email: String?, 
        var checkInTime: Calendar?, 
        var checkOutTime: Calendar? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserDetailsByEvents {
                return gson.fromJson(jsonToParse.toString(), UserDetailsByEvents::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserDetailsByEvents> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserDetailsByEvents>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserDetailsByEventsTransactions(
        var id: String, 
        var userValue: Int, 
        var date: Calendar, 
        var isShared: Boolean, 
        var isRefunded: Boolean, 
        var refundedBy: String?, 
        var sellerName: String, 
        var tipValue: Int, 
        var consumptionWithCreditFromOtherEvents: Int, 
        var paidByApp: Boolean, 
        var products: ArrayList<UserDetailsByEventsTransactionsProducts> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserDetailsByEventsTransactions {
                return gson.fromJson(jsonToParse.toString(), UserDetailsByEventsTransactions::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserDetailsByEventsTransactions> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserDetailsByEventsTransactions>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserDetailsByEventsTransactionsProducts(
        var name: String, 
        var count: Int, 
        var value: Int, 
        var discountValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserDetailsByEventsTransactionsProducts {
                return gson.fromJson(jsonToParse.toString(), UserDetailsByEventsTransactionsProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserDetailsByEventsTransactionsProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserDetailsByEventsTransactionsProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ZigTagBlockConfirm(
        var id: String, 
        var employee: MinimalEmployee, 
        var event: MinimalEvent, 
        var date: Calendar, 
        var user: User, 
        var zigTagCode: String, 
        var recharges: ArrayList<ZigTagBlockConfirmRecharges> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigTagBlockConfirm {
                return gson.fromJson(jsonToParse.toString(), ZigTagBlockConfirm::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigTagBlockConfirm> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigTagBlockConfirm>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ZigTagBlockConfirmRecharges(
        var id: String, 
        var value: Int, 
        var date: Calendar, 
        var expirationDate: Calendar, 
        var paymentPrePaidType: PaymentPrePaidType? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigTagBlockConfirmRecharges {
                return gson.fromJson(jsonToParse.toString(), ZigTagBlockConfirmRecharges::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigTagBlockConfirmRecharges> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigTagBlockConfirmRecharges>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ZigTagSyncForce(
        var id: String, 
        var employee: MinimalEmployee, 
        var event: MinimalEvent, 
        var date: Calendar, 
        var user: User, 
        var reason: String, 
        var balance: Int, 
        var zigTagCode: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigTagSyncForce {
                return gson.fromJson(jsonToParse.toString(), ZigTagSyncForce::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigTagSyncForce> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigTagSyncForce>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ImageUrl(
        var url: String, 
        var sha256: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ImageUrl {
                return gson.fromJson(jsonToParse.toString(), ImageUrl::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ImageUrl> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ImageUrl>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Event(
        var id: String, 
        var name: String, 
        var description: String?, 
        var place: Place?, 
        var status: EventState, 
        var begin: Calendar, 
        var end: Calendar?, 
        var cover: ImageUrl?, 
        var image: ImageUrl?, 
        var attractions: ArrayList<String>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Event {
                return gson.fromJson(jsonToParse.toString(), Event::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Event> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Event>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BaseCombo(
        var name: String, 
        var value: Int, 
        var description: String?, 
        var category: String, 
        var active: Boolean, 
        var fiscalData: ProductFiscalData? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BaseCombo {
                return gson.fromJson(jsonToParse.toString(), BaseCombo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BaseCombo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BaseCombo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewPlaceCombo(
        var kindId: String, 
        var products: ArrayList<NewPlaceComboProducts>, 
        var name: String, 
        var value: Int, 
        var description: String?, 
        var category: String, 
        var active: Boolean, 
        var fiscalData: ProductFiscalData? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewPlaceCombo {
                return gson.fromJson(jsonToParse.toString(), NewPlaceCombo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewPlaceCombo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewPlaceCombo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class NewPlaceComboProducts(
        var id: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): NewPlaceComboProducts {
                return gson.fromJson(jsonToParse.toString(), NewPlaceComboProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<NewPlaceComboProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<NewPlaceComboProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PlaceCombo(
        var id: String, 
        var kind: ProductKind, 
        var products: ArrayList<PlaceComboProducts>, 
        var image: ImageUrl?, 
        var name: String, 
        var value: Int, 
        var description: String?, 
        var category: String, 
        var active: Boolean, 
        var fiscalData: ProductFiscalData? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PlaceCombo {
                return gson.fromJson(jsonToParse.toString(), PlaceCombo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PlaceCombo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PlaceCombo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PlaceComboProducts(
        var id: String, 
        var count: Int, 
        var name: String, 
        var image: ImageUrl?, 
        var category: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PlaceComboProducts {
                return gson.fromJson(jsonToParse.toString(), PlaceComboProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PlaceComboProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PlaceComboProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EventDetails(
        var placeId: String, 
        var begin: Calendar, 
        var end: Calendar?, 
        var name: String, 
        var description: String?, 
        var attractions: ArrayList<String>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EventDetails {
                return gson.fromJson(jsonToParse.toString(), EventDetails::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EventDetails> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EventDetails>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OpenedReports(
        var type: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OpenedReports {
                return gson.fromJson(jsonToParse.toString(), OpenedReports::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OpenedReports> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OpenedReports>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EmployeeBase(
        var id: String?, 
        var name: String, 
        var username: String, 
        var zigReadPermissionKey: String?, 
        var avatar: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EmployeeBase {
                return gson.fromJson(jsonToParse.toString(), EmployeeBase::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EmployeeBase> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EmployeeBase>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Seller(
        var id: String, 
        var name: String, 
        var avatar: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Seller {
                return gson.fromJson(jsonToParse.toString(), Seller::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Seller> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Seller>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TransactionProduct(
        var id: String, 
        var name: String, 
        var category: String, 
        var value: Int, 
        var count: Int, 
        var image: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TransactionProduct {
                return gson.fromJson(jsonToParse.toString(), TransactionProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TransactionProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TransactionProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TransactionComboProduct(
        var id: String, 
        var name: String, 
        var count: Int, 
        var originalValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TransactionComboProduct {
                return gson.fromJson(jsonToParse.toString(), TransactionComboProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TransactionComboProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TransactionComboProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TransactionCombo(
        var id: String, 
        var products: ArrayList<TransactionComboProduct>, 
        var value: Int, 
        var name: String, 
        var category: String, 
        var count: Int, 
        var description: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TransactionCombo {
                return gson.fromJson(jsonToParse.toString(), TransactionCombo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TransactionCombo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TransactionCombo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Transaction(
        var id: String, 
        var date: Calendar, 
        var products: ArrayList<TransactionProduct>, 
        var combos: ArrayList<TransactionCombo>, 
        var place: String, 
        var event: String, 
        var seller: Seller, 
        var buyers: ArrayList<User>, 
        var paid: Boolean, 
        var refunded: Boolean, 
        var refundedBy: String?, 
        var refundedObs: String?, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Transaction {
                return gson.fromJson(jsonToParse.toString(), Transaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Transaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Transaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductSold(
        var id: String, 
        var name: String, 
        var category: String, 
        var image: ImageUrl?, 
        var count: Int, 
        var type: SellableThing, 
        var totalValue: Int, 
        var totalDiscount: Int, 
        var mountableProducts: ArrayList<ProductSoldMountableProducts>, 
        var productUnitCost: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductSold {
                return gson.fromJson(jsonToParse.toString(), ProductSold::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductSold> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductSold>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductSoldMountableProducts(
        var id: String, 
        var name: String, 
        var count: Int, 
        var section: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductSoldMountableProducts {
                return gson.fromJson(jsonToParse.toString(), ProductSoldMountableProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductSoldMountableProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductSoldMountableProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class StockProduct(
        var id: String, 
        var name: String, 
        var quantity: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): StockProduct {
                return gson.fromJson(jsonToParse.toString(), StockProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<StockProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<StockProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Transfer(
        var value: Int, 
        var date: Calendar, 
        var paid: Boolean, 
        var reason: TransferReason 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Transfer {
                return gson.fromJson(jsonToParse.toString(), Transfer::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Transfer> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Transfer>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductTransfer(
        var employee: EmployeeBase, 
        var count: Int, 
        var date: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductTransfer {
                return gson.fromJson(jsonToParse.toString(), ProductTransfer::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductTransfer> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductTransfer>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ReportProduct(
        var id: String, 
        var name: String, 
        var value: Int, 
        var image: ImageUrl?, 
        var transfers: ArrayList<ProductTransfer>?, 
        var finalCount: Int?, 
        var lostCount: Int?, 
        var deliveredCount: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ReportProduct {
                return gson.fromJson(jsonToParse.toString(), ReportProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ReportProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ReportProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class StockReportData(
        var products: ArrayList<ReportProduct> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): StockReportData {
                return gson.fromJson(jsonToParse.toString(), StockReportData::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<StockReportData> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<StockReportData>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BarReportData(
        var products: ArrayList<ReportProduct> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BarReportData {
                return gson.fromJson(jsonToParse.toString(), BarReportData::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BarReportData> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BarReportData>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CashierTransfer(
        var value: Int, 
        var employee: EmployeeBase, 
        var date: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CashierTransfer {
                return gson.fromJson(jsonToParse.toString(), CashierTransfer::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CashierTransfer> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CashierTransfer>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CashierReportDataInput(
        var finalInputCredit: Int, 
        var finalInputDebit: Int, 
        var finalInputCash: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CashierReportDataInput {
                return gson.fromJson(jsonToParse.toString(), CashierReportDataInput::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CashierReportDataInput> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CashierReportDataInput>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CashierReportData(
        var finalInputCredit: Int?, 
        var finalInputDebit: Int?, 
        var finalInputCash: Int?, 
        var transfers: ArrayList<CashierTransfer>, 
        var salesCredit: Int?, 
        var salesDebit: Int?, 
        var salesCash: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CashierReportData {
                return gson.fromJson(jsonToParse.toString(), CashierReportData::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CashierReportData> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CashierReportData>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EventReport(
        var id: String, 
        var type: ReportType, 
        var closed: Boolean, 
        var employee: Employee?, 
        var cashierData: CashierReportData?, 
        var barData: BarReportData?, 
        var stockData: StockReportData?, 
        var bar: Bar? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EventReport {
                return gson.fromJson(jsonToParse.toString(), EventReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EventReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EventReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class StockTransfer(
        var employeeBar: String, 
        var stocker: String, 
        var product: String, 
        var quantity: Int, 
        var date: Calendar, 
        var bar: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): StockTransfer {
                return gson.fromJson(jsonToParse.toString(), StockTransfer::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<StockTransfer> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<StockTransfer>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BillProducts(
        var category: String, 
        var name: String, 
        var value: Int, 
        var quantity: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BillProducts {
                return gson.fromJson(jsonToParse.toString(), BillProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BillProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BillProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BillTransaction(
        var id: String, 
        var date: Calendar, 
        var products: ArrayList<BillProducts>, 
        var seller: String, 
        var refunded: Boolean, 
        var value: Int, 
        var tip: Int, 
        var totalValue: Int, 
        var paid: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BillTransaction {
                return gson.fromJson(jsonToParse.toString(), BillTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BillTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BillTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserBill(
        var id: String, 
        var name: String, 
        var transactions: ArrayList<BillTransaction> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserBill {
                return gson.fromJson(jsonToParse.toString(), UserBill::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserBill> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserBill>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CpfOrPhone(
        var type: CpfOrPhoneType, 
        var value: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CpfOrPhone {
                return gson.fromJson(jsonToParse.toString(), CpfOrPhone::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CpfOrPhone> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CpfOrPhone>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TimelineProduct(
        var name: String, 
        var id: String, 
        var count: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TimelineProduct {
                return gson.fromJson(jsonToParse.toString(), TimelineProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TimelineProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TimelineProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TimelineEvent(
        var id: String, 
        var type: TimelineEventType, 
        var value: Int, 
        var products: ArrayList<TimelineProduct>?, 
        var shared: Boolean?, 
        var paid: Boolean?, 
        var refunded: Boolean?, 
        var usedValue: Int?, 
        var expired: Boolean?, 
        var rechargeType: String?, 
        var date: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TimelineEvent {
                return gson.fromJson(jsonToParse.toString(), TimelineEvent::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TimelineEvent> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TimelineEvent>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BonusProduct(
        var bonusValue: Int, 
        var name: String, 
        var id: String, 
        var category: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BonusProduct {
                return gson.fromJson(jsonToParse.toString(), BonusProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BonusProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BonusProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class GuinnessLog(
        var employee: String, 
        var name: String?, 
        var cpf: String?, 
        var uid: String, 
        var date: Calendar, 
        var type: GuinnessLogType 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): GuinnessLog {
                return gson.fromJson(jsonToParse.toString(), GuinnessLog::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<GuinnessLog> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<GuinnessLog>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class GetBackofficeConfig(
        var zigAccountId: String, 
        var placeAccountId: String, 
        var zigSupplierId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): GetBackofficeConfig {
                return gson.fromJson(jsonToParse.toString(), GetBackofficeConfig::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<GetBackofficeConfig> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<GetBackofficeConfig>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    enum class CouponType {
    DiscountCoupon, 
    GiftCard
    }

    enum class CouponDiscountType {
    Bonus, 
    Promotion
    }

    enum class PostPaymentEventPostPaymentType {
    App, 
    Place
    }

    enum class NewMountableSectionPricing {
    sum, 
    average, 
    higher
    }

    enum class PrinterType {
    EPSON, 
    BEMATECH
    }

    enum class PaymentPrePaidType {
    MinimumConsumption, 
    Bonus, 
    FromEvent, 
    FromOtherEvent, 
    FromApp, 
    FakeRecharge
    }

    enum class DashboardAlertType {
    ExtractPriority, 
    Normal
    }

    enum class BackofficeBillPaymentMethod {
    App, 
    Money, 
    Check, 
    Boleto, 
    CreditCard, 
    BankTransfer, 
    Promissory, 
    DebitCard, 
    AutomaticDebit
    }

    enum class BackofficeBaseBillStatus {
    Paid, 
    Pending
    }

    enum class BackofficeBillStatusFilter {
    Any, 
    ToPay, 
    ToReceive
    }

    enum class BackofficeBillDateFilter {
    ByDue, 
    ByPerform
    }

    enum class BackofficeInventoryStatus {
    Opened, 
    Closed, 
    Canceled
    }

    enum class BackofficeImportedInvoiceDetailsField {
    insuranceValue, 
    discountValue, 
    otherExpensesValue, 
    shippingValue
    }

    enum class BackofficeManifestType {
    ciencia, 
    confirmacao, 
    desconhecimento, 
    naoRealizada
    }

    enum class BackofficeResumeBillsDueToTodayStatus {
    Paid, 
    Pending
    }

    enum class BackofficeStorageTransferType {
    input, 
    transferOut, 
    transferIn, 
    lost, 
    sell, 
    refund, 
    manual, 
    productionOut, 
    productionIn, 
    invoice, 
    inventory
    }

    enum class WithdrawStatus {
    Pending, 
    Paid, 
    Refused, 
    Processing
    }

    enum class WithdrawType {
    Anticipation, 
    Withdraw
    }

    enum class ConsolidatedExtractSectionValuesTotalValueUnit {
    unit, 
    money
    }

    enum class RechargeConsumptionMethod {
    debit, 
    credit, 
    cash, 
    voucher, 
    dashboard, 
    anticipated
    }

    enum class PaymentMethod {
    CreditCard, 
    DebitCard, 
    Cash, 
    PostEvent, 
    ZigPosCredit, 
    ZigPosDebit, 
    Voucher
    }

    enum class EventResumeSectionValuesValuesUnit {
    money, 
    un, 
    percentage
    }

    enum class OpenedBillPaymentMethod {
    CreditCard, 
    DebitCard, 
    Cash, 
    PostEvent, 
    ZigPosCredit, 
    ZigPosDebit, 
    Voucher, 
    Bonus
    }

    enum class RegimeTributario {
    SimplesNacional, 
    SimplesNacionalExcesso, 
    RegimeNormal
    }

    enum class FiscalMode {
    nfce, 
    sat
    }

    enum class InvoiceMode {
    sat, 
    nfse, 
    nfce
    }

    enum class FiscalProductGroupType {
    invoice, 
    service
    }

    enum class NotEmittedProductOrComboType {
    combo, 
    product
    }

    enum class InventoryStatus {
    Opened, 
    Closed, 
    Canceled
    }

    enum class PlaceSellVisualizationFormat {
    Grid, 
    List
    }

    enum class NewPlaceSellVisualizationFormat {
    Grid, 
    List
    }

    enum class PosTransactionMethodMethod {
    Credit, 
    Debit
    }

    enum class ProportionalValueUnit {
    gramas, 
    ml
    }

    enum class FinanceBiMethod {
    Credit, 
    Debit, 
    Cash, 
    PostEvent, 
    ZigCredit, 
    ZigDebit, 
    Voucher, 
    Anticipated, 
    Dashboard, 
    App
    }

    enum class UserFilterType {
    cpf, 
    phone, 
    zigcode, 
    id
    }

    enum class UserRechargeMethod {
    CreditCard, 
    DebitCard, 
    Cash, 
    App, 
    ZigPosCredit, 
    ZigPosDebit, 
    Voucher, 
    Dashboard, 
    Anticipated
    }

    enum class NewUserRechargeType {
    minimumConsumption, 
    normal
    }

    enum class NewUserRechargeGender {
    Male, 
    Female, 
    Other
    }

    enum class EventState {
    Created, 
    Finished, 
    Running, 
    Canceled
    }

    enum class SellableThing {
    Product, 
    Combo
    }

    enum class TransferReason {
    PPUTransaction, 
    PostTransactionOnline, 
    PostTransactionInPlace, 
    RechargeInPlace, 
    RechargeInApp, 
    PreTransaction, 
    PreTransactionWithPlaceCredit, 
    PreTransactionWithPlaceCreditFromOtherEvent, 
    BonusTransaction, 
    RefundedTransaction, 
    PaidPostPaidTransactionsInPlace
    }

    enum class ReportType {
    Waiter, 
    Stock, 
    GeneralEmployee, 
    Cashier, 
    Bar
    }

    enum class CpfOrPhoneType {
    cpf, 
    phone
    }

    enum class TimelineEventType {
    Recharge, 
    Transaction, 
    PostPayment
    }

    enum class GuinnessLogType {
    checkin, 
    checkout
    }

    enum class ErrorType {
    BackofficeError, 
    NotLoggedIn, 
    LacksPermission, 
    InvalidArgument, 
    WrongLogin, 
    DoesntExist, 
    LacksFeature, 
    Fatal, 
    Connection, 
    Serialization
    }

    enum class ApplySuggestedImageForProductType {
    product, 
    combo
    }

var calls = object: Calls { 
         override fun getAllPermissions(): Deferred<LiveData<Response<ArrayList<Permission>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getAllPermissions", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Permission>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Permission>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Permission>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAllRoles(): Deferred<LiveData<Response<ArrayList<Role>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getAllRoles", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Role>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Role>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Role>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteRole(slug: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("slug", slug)
              }
              val r = makeRequest("deleteRole", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createRole(name: String, permissions: ArrayList<String>): Deferred<LiveData<Response<Role?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("name", name)
    put("permissions", JSONArray().apply {
            permissions.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("createRole", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Role::class.java)
              } else null
             MutableLiveData<Response<Role?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun updateRole(slug: String, name: String, permissions: ArrayList<String>): Deferred<LiveData<Response<Role?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("slug", slug)
    put("name", name)
    put("permissions", JSONArray().apply {
            permissions.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("updateRole", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Role::class.java)
              } else null
             MutableLiveData<Response<Role?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAlerts(placeId: String, skip: Int, limit: Int): Deferred<LiveData<Response<ArrayList<DashboardAlert>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("skip", skip)
    put("limit", limit)
              }
              val r = makeRequest("getAlerts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<DashboardAlert>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<DashboardAlert>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<DashboardAlert>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun markAlertAsRead(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("markAlertAsRead", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAnnounces(eventId: String): Deferred<LiveData<Response<ArrayList<Announce>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getAnnounces", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Announce>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Announce>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Announce>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createAnnounce(announce: Announce, eventId: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("announce", gson.toJson(announce))
    put("eventId", eventId)
              }
              val r = makeRequest("createAnnounce", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editAnnounce(announce: Announce): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("announce", gson.toJson(announce))
              }
              val r = makeRequest("editAnnounce", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun removeAnnounce(announceId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("announceId", announceId)
              }
              val r = makeRequest("removeAnnounce", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeConfig(placeId: String): Deferred<LiveData<Response<GetBackofficeConfig?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeConfig", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), GetBackofficeConfig::class.java)
              } else null
             MutableLiveData<Response<GetBackofficeConfig?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeAccount(id: String, placeId: String): Deferred<LiveData<Response<BackofficeAccount?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeAccount", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeAccount::class.java)
              } else null
             MutableLiveData<Response<BackofficeAccount?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeAccounts(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeAccount>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeAccounts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeAccount>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeAccount>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeAccount>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeAccount(placeId: String, account: BackofficeNewAccount): Deferred<LiveData<Response<BackofficeAccount?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("account", gson.toJson(account))
              }
              val r = makeRequest("createBackofficeAccount", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeAccount::class.java)
              } else null
             MutableLiveData<Response<BackofficeAccount?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeAccount(placeId: String, id: String, account: BackofficeNewAccount): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("account", gson.toJson(account))
              }
              val r = makeRequest("editBackofficeAccount", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeAccount(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
              }
              val r = makeRequest("deleteBackofficeAccount", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBill(id: String, placeId: String): Deferred<LiveData<Response<BackofficeBill?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeBill", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeBill::class.java)
              } else null
             MutableLiveData<Response<BackofficeBill?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBills(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<ArrayList<BackofficeBill>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeBills", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeBill>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeBill>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeBill>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeBill(placeId: String, bill: BackofficeNewBill, attachment: BackofficeAttachment?): Deferred<LiveData<Response<BackofficeBill?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("bill", gson.toJson(bill))
    put("attachment", attachment?.let { gson.toJson(attachment) })
              }
              val r = makeRequest("createBackofficeBill", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeBill::class.java)
              } else null
             MutableLiveData<Response<BackofficeBill?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeBill(placeId: String, id: String, bill: BackofficeNewBill): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("bill", gson.toJson(bill))
              }
              val r = makeRequest("editBackofficeBill", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun payBackofficeBill(placeId: String, id: String, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("date", dateTimeFormat.format(date))
              }
              val r = makeRequest("payBackofficeBill", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeBillAttachment(placeId: String, id: String, attachment: BackofficeAttachment?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("attachment", attachment?.let { gson.toJson(attachment) })
              }
              val r = makeRequest("editBackofficeBillAttachment", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeBill(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
              }
              val r = makeRequest("deleteBackofficeBill", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun exportBackofficeBills(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("exportBackofficeBills", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun parseBackofficeBillsToImport(placeId: String, _file: ByteArray): Deferred<LiveData<Response<ArrayList<BackofficeNewBill>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("file", Base64.encodeToString(_file, Base64.DEFAULT))
              }
              val r = makeRequest("parseBackofficeBillsToImport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeNewBill>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeNewBill>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeNewBill>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun importBackofficeBills(placeId: String, _file: ByteArray): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("file", Base64.encodeToString(_file, Base64.DEFAULT))
              }
              val r = makeRequest("importBackofficeBills", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeBillPlanCategory(name: String, parentId: String?): Deferred<LiveData<Response<BackofficeBillPlanCategoryBase?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("name", name)
    put("parentId", parentId?.let { parentId })
              }
              val r = makeRequest("createBackofficeBillPlanCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeBillPlanCategoryBase::class.java)
              } else null
             MutableLiveData<Response<BackofficeBillPlanCategoryBase?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeBillPlanCategory(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteBackofficeBillPlanCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillPlanCategory(id: String): Deferred<LiveData<Response<BackofficeBillPlanCategoryBase?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("getBackofficeBillPlanCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeBillPlanCategoryBase::class.java)
              } else null
             MutableLiveData<Response<BackofficeBillPlanCategoryBase?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillPlans(): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getBackofficeBillPlans", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeBillPlanCategory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeBillPlanCategory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillPlansTreeLeafs(): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategoryBase>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getBackofficeBillPlansTreeLeafs", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeBillPlanCategoryBase>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeBillPlanCategoryBase>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeBillPlanCategoryBase>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillPlansTreeLeafsByLevel(level: Int): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategoryBase>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("level", level)
              }
              val r = makeRequest("getBackofficeBillPlansTreeLeafsByLevel", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeBillPlanCategoryBase>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeBillPlanCategoryBase>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeBillPlanCategoryBase>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeBillPlanCategoryName(id: String, name: String): Deferred<LiveData<Response<BackofficeBillPlanCategoryBase?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("name", name)
              }
              val r = makeRequest("editBackofficeBillPlanCategoryName", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeBillPlanCategoryBase::class.java)
              } else null
             MutableLiveData<Response<BackofficeBillPlanCategoryBase?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeBillPlanCategoryParent(id: String, newParentId: String?): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("newParentId", newParentId?.let { newParentId })
              }
              val r = makeRequest("editBackofficeBillPlanCategoryParent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeBillPlanCategory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeBillPlanCategory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeSuggestedBillPlan(): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("createBackofficeSuggestedBillPlan", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeBillPlanCategory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeBillPlanCategory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun exportBackofficeBillPlan(): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("exportBackofficeBillPlan", null)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun importBackofficeBillPlan(_file: ByteArray): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("file", Base64.encodeToString(_file, Base64.DEFAULT))
              }
              val r = makeRequest("importBackofficeBillPlan", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeBillPlanCategory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeBillPlanCategory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeBillPlanCategory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeBillPlan(): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("deleteBackofficeBillPlan", null)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillType(id: String, placeId: String): Deferred<LiveData<Response<BackofficeBillType?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeBillType", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeBillType::class.java)
              } else null
             MutableLiveData<Response<BackofficeBillType?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillTypes(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeBillType>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeBillTypes", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeBillType>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeBillType>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeBillType>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeBillType(placeId: String, billType: BackofficeNewBillType): Deferred<LiveData<Response<BackofficeBillType?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("billType", gson.toJson(billType))
              }
              val r = makeRequest("createBackofficeBillType", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeBillType::class.java)
              } else null
             MutableLiveData<Response<BackofficeBillType?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeBillType(placeId: String, id: String, billType: BackofficeNewBillType): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("billType", gson.toJson(billType))
              }
              val r = makeRequest("editBackofficeBillType", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeBillType(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
              }
              val r = makeRequest("deleteBackofficeBillType", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeCategory(id: String, placeId: String): Deferred<LiveData<Response<BackofficeCategory?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeCategory::class.java)
              } else null
             MutableLiveData<Response<BackofficeCategory?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeCategories(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeCategory>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeCategories", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeCategory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeCategory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeCategory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeCategory(placeId: String, category: BackofficeNewCategory): Deferred<LiveData<Response<BackofficeCategory?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("category", gson.toJson(category))
              }
              val r = makeRequest("createBackofficeCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeCategory::class.java)
              } else null
             MutableLiveData<Response<BackofficeCategory?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeCategory(placeId: String, id: String, category: BackofficeNewCategory): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("category", gson.toJson(category))
              }
              val r = makeRequest("editBackofficeCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeCategory(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
              }
              val r = makeRequest("deleteBackofficeCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductsCmv(placeId: String, productIds: ArrayList<String>, since: Calendar, until: Calendar, shouldUseLastInvoice: Boolean): Deferred<LiveData<Response<ArrayList<BackofficeProductCmv>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("shouldUseLastInvoice", shouldUseLastInvoice)
              }
              val r = makeRequest("getBackofficeProductsCmv", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeProductCmv>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeProductCmv>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeProductCmv>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductsCmvAvg(placeId: String, productIds: ArrayList<String>, since: Calendar, until: Calendar, shouldUseLastInvoice: Boolean): Deferred<LiveData<Response<ArrayList<BackofficeProductCmv>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("shouldUseLastInvoice", shouldUseLastInvoice)
              }
              val r = makeRequest("getBackofficeProductsCmvAvg", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeProductCmv>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeProductCmv>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeProductCmv>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeCurrentProductsCmv(placeId: String, productIds: ArrayList<String>, shouldUseLastInvoice: Boolean): Deferred<LiveData<Response<ArrayList<BackofficeProductCmv>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
    put("shouldUseLastInvoice", shouldUseLastInvoice)
              }
              val r = makeRequest("getBackofficeCurrentProductsCmv", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeProductCmv>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeProductCmv>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeProductCmv>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductsSimpleCurrentCmv(placeId: String, productIds: ArrayList<String>, shouldUseLastInvoice: Boolean): Deferred<LiveData<Response<ArrayList<BackofficeProductSimpleCmv>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
    put("shouldUseLastInvoice", shouldUseLastInvoice)
              }
              val r = makeRequest("getBackofficeProductsSimpleCurrentCmv", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeProductSimpleCmv>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeProductSimpleCmv>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeProductSimpleCmv>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductsSimpleCmv(placeId: String, productIds: ArrayList<String>, since: Calendar, until: Calendar, shouldUseLastInvoice: Boolean): Deferred<LiveData<Response<ArrayList<BackofficeProductSimpleCmv>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("shouldUseLastInvoice", shouldUseLastInvoice)
              }
              val r = makeRequest("getBackofficeProductsSimpleCmv", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeProductSimpleCmv>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeProductSimpleCmv>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeProductSimpleCmv>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeCostCenter(id: String, placeId: String): Deferred<LiveData<Response<BackofficeCostCenter?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeCostCenter", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeCostCenter::class.java)
              } else null
             MutableLiveData<Response<BackofficeCostCenter?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeCostCenters(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeCostCenter>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeCostCenters", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeCostCenter>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeCostCenter>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeCostCenter>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeCostCenter(placeId: String, costCenter: BackofficeNewCostCenter): Deferred<LiveData<Response<BackofficeCostCenter?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("costCenter", gson.toJson(costCenter))
              }
              val r = makeRequest("createBackofficeCostCenter", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeCostCenter::class.java)
              } else null
             MutableLiveData<Response<BackofficeCostCenter?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeCostCenter(placeId: String, id: String, costCenter: BackofficeNewCostCenter): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("costCenter", gson.toJson(costCenter))
              }
              val r = makeRequest("editBackofficeCostCenter", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeCostCenter(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
              }
              val r = makeRequest("deleteBackofficeCostCenter", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun insertBackofficeFocusCompany(placeId: String, cnpj: String, token: String, lastInvoiceVersion: Int?): Deferred<LiveData<Response<BackofficeFocusCompany?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("cnpj", cnpj)
    put("token", token)
    put("lastInvoiceVersion", lastInvoiceVersion?.let { lastInvoiceVersion })
              }
              val r = makeRequest("insertBackofficeFocusCompany", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeFocusCompany::class.java)
              } else null
             MutableLiveData<Response<BackofficeFocusCompany?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun updateBackofficeFocusCompany(placeId: String, id: String, token: String): Deferred<LiveData<Response<BackofficeFocusCompany?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("token", token)
              }
              val r = makeRequest("updateBackofficeFocusCompany", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeFocusCompany::class.java)
              } else null
             MutableLiveData<Response<BackofficeFocusCompany?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeFocusCompany(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
              }
              val r = makeRequest("deleteBackofficeFocusCompany", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeFocusCompanies(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeFocusCompany>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeFocusCompanies", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeFocusCompany>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeFocusCompany>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeFocusCompany>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeFocusCompany(id: String, placeId: String): Deferred<LiveData<Response<BackofficeFocusCompany?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeFocusCompany", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeFocusCompany::class.java)
              } else null
             MutableLiveData<Response<BackofficeFocusCompany?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeFocusInvoices(placeId: String, focusCompanyId: String): Deferred<LiveData<Response<ArrayList<BackofficeFocusInvoice>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("focusCompanyId", focusCompanyId)
              }
              val r = makeRequest("getBackofficeFocusInvoices", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeFocusInvoice>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeFocusInvoice>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeFocusInvoice>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeInventory(placeId: String, inventory: BackofficeNewInventory, shouldOverlapOldInventory: Boolean): Deferred<LiveData<Response<BackofficeInventory?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("inventory", gson.toJson(inventory))
    put("shouldOverlapOldInventory", shouldOverlapOldInventory)
              }
              val r = makeRequest("createBackofficeInventory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeInventory::class.java)
              } else null
             MutableLiveData<Response<BackofficeInventory?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeInventories(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<BackofficeInventory>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getBackofficeInventories", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeInventory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeInventory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeInventory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeOpenedInventories(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeInventory>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeOpenedInventories", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeInventory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeInventory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeInventory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun addBackofficeStorageToInventory(placeId: String, inventoryId: String, storageIds: ArrayList<String>): Deferred<LiveData<Response<BackofficeInventory?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("inventoryId", inventoryId)
    put("storageIds", JSONArray().apply {
            storageIds.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("addBackofficeStorageToInventory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeInventory::class.java)
              } else null
             MutableLiveData<Response<BackofficeInventory?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeInventoryProductsAtStorage(placeId: String, inventoryId: String, storageId: String): Deferred<LiveData<Response<ArrayList<BackofficeInventoryProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("inventoryId", inventoryId)
    put("storageId", storageId)
              }
              val r = makeRequest("getBackofficeInventoryProductsAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeInventoryProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeInventoryProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeInventoryProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeInventoryProducts(placeId: String, inventoryId: String): Deferred<LiveData<Response<ArrayList<BackofficeInventoryProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("inventoryId", inventoryId)
              }
              val r = makeRequest("getBackofficeInventoryProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeInventoryProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeInventoryProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeInventoryProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun updateBackofficeInventoryProduct(placeId: String, inventoryId: String, authorId: String, products: ArrayList<BackofficeInventoryProduct>): Deferred<LiveData<Response<ArrayList<BackofficeInventoryProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("inventoryId", inventoryId)
    put("authorId", authorId)
    put("products", JSONArray().apply {
            products.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("updateBackofficeInventoryProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeInventoryProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeInventoryProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeInventoryProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun closeBackofficeInventory(placeId: String, authorId: String, inventoryId: String, alterUnitValue: Boolean): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("authorId", authorId)
    put("inventoryId", inventoryId)
    put("alterUnitValue", alterUnitValue)
              }
              val r = makeRequest("closeBackofficeInventory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun cancelBackofficeInventory(placeId: String, authorId: String, inventoryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("authorId", authorId)
    put("inventoryId", inventoryId)
              }
              val r = makeRequest("cancelBackofficeInventory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeImportedInvoices(placeId: String, since: Calendar?, until: Calendar?): Deferred<LiveData<Response<ArrayList<BackofficeImportedInvoice>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", since?.let { dateTimeFormat.format(since) })
    put("until", until?.let { dateTimeFormat.format(until) })
              }
              val r = makeRequest("getBackofficeImportedInvoices", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeImportedInvoice>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeImportedInvoice>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeImportedInvoice>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun importBackofficeInvoice(placeId: String, storageId: String?, xml: ByteArray): Deferred<LiveData<Response<BackofficeImportedInvoice?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId?.let { storageId })
    put("xml", Base64.encodeToString(xml, Base64.DEFAULT))
              }
              val r = makeRequest("importBackofficeInvoice", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeImportedInvoice::class.java)
              } else null
             MutableLiveData<Response<BackofficeImportedInvoice?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeManualInvoice(placeId: String, manualInvoice: BackofficeNewManualInvoice): Deferred<LiveData<Response<BackofficeImportedInvoice?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("manualInvoice", gson.toJson(manualInvoice))
              }
              val r = makeRequest("createBackofficeManualInvoice", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeImportedInvoice::class.java)
              } else null
             MutableLiveData<Response<BackofficeImportedInvoice?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeManualInvoiceDetails(placeId: String, invoiceId: String, invoiceInfo: BackofficeNewManualInvoice): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("invoiceId", invoiceId)
    put("invoiceInfo", gson.toJson(invoiceInfo))
              }
              val r = makeRequest("editBackofficeManualInvoiceDetails", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun invalidateBackofficeInvoiceProduct(placeId: String, invoiceId: String, entryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("invoiceId", invoiceId)
    put("entryId", entryId)
              }
              val r = makeRequest("invalidateBackofficeInvoiceProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductLastInvoiceDetails(placeId: String, associatedProductId: String): Deferred<LiveData<Response<BackofficeProductLastInvoiceDetails??>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("associatedProductId", associatedProductId)
              }
              val r = makeRequest("getBackofficeProductLastInvoiceDetails", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeProductLastInvoiceDetails::class.java)
              } else null
             MutableLiveData<Response<BackofficeProductLastInvoiceDetails??>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeInvoiceProducts(placeId: String, invoiceId: String): Deferred<LiveData<Response<ArrayList<BackofficeImportedInvoiceProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("invoiceId", invoiceId)
              }
              val r = makeRequest("getBackofficeInvoiceProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeImportedInvoiceProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeImportedInvoiceProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeImportedInvoiceProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeInvoiceBill(placeId: String, invoiceId: String, createBill: Boolean, accountId: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("invoiceId", invoiceId)
    put("createBill", createBill)
    put("accountId", accountId?.let { accountId })
              }
              val r = makeRequest("editBackofficeInvoiceBill", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeInvoiceProduct(placeId: String, invoiceId: String, entryId: String, skipped: Boolean, productId: String?, unitValue: Int?, unitMultiplier: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("invoiceId", invoiceId)
    put("entryId", entryId)
    put("skipped", skipped)
    put("productId", productId?.let { productId })
    put("unitValue", unitValue?.let { unitValue })
    put("unitMultiplier", unitMultiplier)
              }
              val r = makeRequest("editBackofficeInvoiceProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeInvoiceSupplier(placeId: String, invoiceId: String, skipped: Boolean, supplierId: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("invoiceId", invoiceId)
    put("skipped", skipped)
    put("supplierId", supplierId?.let { supplierId })
              }
              val r = makeRequest("editBackofficeInvoiceSupplier", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun upsertBackofficeManualInvoiceProduct(placeId: String, invoiceId: String, products: ArrayList<BackofficeUpsertManualProduct>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("invoiceId", invoiceId)
    put("products", JSONArray().apply {
            products.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("upsertBackofficeManualInvoiceProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun finishBackofficeInvoiceImport(invoiceId: String, skipPendings: Boolean, productsBillPlanCategories: ArrayList<BackofficeBillBillPlanCategory>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("invoiceId", invoiceId)
    put("skipPendings", skipPendings)
    put("productsBillPlanCategories", JSONArray().apply {
            productsBillPlanCategories.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("finishBackofficeInvoiceImport", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun upsertBackofficeInvoiceDuplicates(placeId: String, invoiceId: String, accountId: String, duplicates: ArrayList<BackofficeInvoiceDuplicate>): Deferred<LiveData<Response<ArrayList<BackofficeInvoiceDuplicate>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("invoiceId", invoiceId)
    put("accountId", accountId)
    put("duplicates", JSONArray().apply {
            duplicates.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("upsertBackofficeInvoiceDuplicates", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeInvoiceDuplicate>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeInvoiceDuplicate>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeInvoiceDuplicate>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeInvoiceDetailsBillPlanCategories(invoiceId: String, invoiceDetailsField: BackofficeImportedInvoiceDetailsField): Deferred<LiveData<Response<ArrayList<BackofficeImportedInvoiceDetailsBillPlanCategory>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("invoiceId", invoiceId)
    put("invoiceDetailsField", invoiceDetailsField.name)
              }
              val r = makeRequest("getBackofficeInvoiceDetailsBillPlanCategories", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeImportedInvoiceDetailsBillPlanCategory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeImportedInvoiceDetailsBillPlanCategory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeImportedInvoiceDetailsBillPlanCategory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun saveBackofficeDetailsBillPlanCategories(invoiceId: String, objBillPlanCategories: ArrayList<BackofficeImportedInvoiceDetailsObjBillPlanCategory>, invoiceDetailsField: BackofficeImportedInvoiceDetailsField): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("invoiceId", invoiceId)
    put("objBillPlanCategories", JSONArray().apply {
            objBillPlanCategories.forEach { item -> put(gson.toJson(item)) }
          })
    put("invoiceDetailsField", invoiceDetailsField.name)
              }
              val r = makeRequest("saveBackofficeDetailsBillPlanCategories", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeReceivedInvoices(placeId: String, focusCompanyId: String, versao: Int?): Deferred<LiveData<Response<ArrayList<BackofficeReceivedInvoiceHeader>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("focusCompanyId", focusCompanyId)
    put("versao", versao?.let { versao })
              }
              val r = makeRequest("getBackofficeReceivedInvoices", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeReceivedInvoiceHeader>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeReceivedInvoiceHeader>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeReceivedInvoiceHeader>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeReceivedInvoice(placeId: String, focusCompanyId: String, accessKey: String): Deferred<LiveData<Response<BackofficeReceivedInvoiceHeader?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("focusCompanyId", focusCompanyId)
    put("accessKey", accessKey)
              }
              val r = makeRequest("getBackofficeReceivedInvoice", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeReceivedInvoiceHeader::class.java)
              } else null
             MutableLiveData<Response<BackofficeReceivedInvoiceHeader?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeInvoiceXmlUrl(placeId: String, id: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
              }
              val r = makeRequest("getBackofficeInvoiceXmlUrl", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun postBackofficeManifest(placeId: String, id: String, manifestType: BackofficeManifestType, reason: String?): Deferred<LiveData<Response<BackofficeResponseInvoiceManifest?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("manifestType", manifestType.name)
    put("reason", reason?.let { reason })
              }
              val r = makeRequest("postBackofficeManifest", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeResponseInvoiceManifest::class.java)
              } else null
             MutableLiveData<Response<BackofficeResponseInvoiceManifest?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeIncomeStatement(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeIncomeStatement?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeIncomeStatement", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeIncomeStatement::class.java)
              } else null
             MutableLiveData<Response<BackofficeIncomeStatement?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeIncomeByCategory(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<ArrayList<BackofficeIncomeByCategory>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeIncomeByCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeIncomeByCategory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeIncomeByCategory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeIncomeByCategory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillHistory(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeBillHistory?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeBillHistory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeBillHistory::class.java)
              } else null
             MutableLiveData<Response<BackofficeBillHistory?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeExtract(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeExtract?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeExtract", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeExtract::class.java)
              } else null
             MutableLiveData<Response<BackofficeExtract?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeCashFlow(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeCashFlow?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeCashFlow", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeCashFlow::class.java)
              } else null
             MutableLiveData<Response<BackofficeCashFlow?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeDescriptionReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeDescriptionReport?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeDescriptionReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeDescriptionReport::class.java)
              } else null
             MutableLiveData<Response<BackofficeDescriptionReport?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeDayReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeDayReport?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeDayReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeDayReport::class.java)
              } else null
             MutableLiveData<Response<BackofficeDayReport?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillTypeReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeDescriptionReport?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeBillTypeReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeDescriptionReport::class.java)
              } else null
             MutableLiveData<Response<BackofficeDescriptionReport?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillCategoryReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeDescriptionReport?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeBillCategoryReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeDescriptionReport::class.java)
              } else null
             MutableLiveData<Response<BackofficeDescriptionReport?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeCostCenterReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeCostCenterReport?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeCostCenterReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeCostCenterReport::class.java)
              } else null
             MutableLiveData<Response<BackofficeCostCenterReport?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeCategoryHistoryReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeCategoryHistory?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeCategoryHistoryReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeCategoryHistory::class.java)
              } else null
             MutableLiveData<Response<BackofficeCategoryHistory?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillSupplierTrackerReport(placeId: String, since: Calendar, until: Calendar, statusFilter: BackofficeBillStatusFilter, dateFilter: BackofficeBillDateFilter): Deferred<LiveData<Response<BackofficeBillSupplierTrackerReport?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("statusFilter", statusFilter.name)
    put("dateFilter", dateFilter.name)
              }
              val r = makeRequest("getBackofficeBillSupplierTrackerReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeBillSupplierTrackerReport::class.java)
              } else null
             MutableLiveData<Response<BackofficeBillSupplierTrackerReport?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeResume(placeId: String): Deferred<LiveData<Response<BackofficeResume?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeResume", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeResume::class.java)
              } else null
             MutableLiveData<Response<BackofficeResume?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeResumeInPeriod(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<BackofficePeriodResume?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getBackofficeResumeInPeriod", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficePeriodResume::class.java)
              } else null
             MutableLiveData<Response<BackofficePeriodResume?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeBillPlanCategoriesReport(billPlanCategoryIds: ArrayList<String>, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<BackofficeBillPlanCategoriesReport>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("billPlanCategoryIds", JSONArray().apply {
            billPlanCategoryIds.forEach { item -> put(item) }
          })
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getBackofficeBillPlanCategoriesReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeBillPlanCategoriesReport>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeBillPlanCategoriesReport>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeBillPlanCategoriesReport>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeStorage(id: String, placeId: String): Deferred<LiveData<Response<BackofficeStorage?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeStorage::class.java)
              } else null
             MutableLiveData<Response<BackofficeStorage?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeStorages(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeStorage>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeStorages", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeStorage>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeStorage>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeStorage>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeStorage(placeId: String, storage: BackofficeNewStorage): Deferred<LiveData<Response<BackofficeStorage?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storage", gson.toJson(storage))
              }
              val r = makeRequest("createBackofficeStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeStorage::class.java)
              } else null
             MutableLiveData<Response<BackofficeStorage?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeStorage(placeId: String, id: String, storage: BackofficeNewStorage): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("storage", gson.toJson(storage))
              }
              val r = makeRequest("editBackofficeStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeStorage(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
              }
              val r = makeRequest("deleteBackofficeStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductsAtStorage(placeId: String, storageId: String): Deferred<LiveData<Response<ArrayList<BackofficeStorageProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
              }
              val r = makeRequest("getBackofficeProductsAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeStorageProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeStorageProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeStorageProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductStorageHistory(placeId: String, storageId: String, productId: String, since: Calendar, until: Calendar, page: Int?, itemPerPage: Int?): Deferred<LiveData<Response<BackofficeStorageHistory?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("productId", productId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("page", page?.let { page })
    put("itemPerPage", itemPerPage?.let { itemPerPage })
              }
              val r = makeRequest("getBackofficeProductStorageHistory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeStorageHistory::class.java)
              } else null
             MutableLiveData<Response<BackofficeStorageHistory?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductStorageHistoryXls(placeId: String, storageId: String, productId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("productId", productId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getBackofficeProductStorageHistoryXls", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductsStorageHistory(placeId: String, storageId: String, productIds: ArrayList<String>, since: Calendar, until: Calendar, page: Int?, itemPerPage: Int?): Deferred<LiveData<Response<ArrayList<BackofficeStorageProductsHistory>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("page", page?.let { page })
    put("itemPerPage", itemPerPage?.let { itemPerPage })
              }
              val r = makeRequest("getBackofficeProductsStorageHistory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeStorageProductsHistory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeStorageProductsHistory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeStorageProductsHistory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun sellBackofficeProductAtStorage(placeId: String, storageId: String, productId: String, count: Int, date: Calendar, shouldApplyProductionRule: Boolean): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("productId", productId)
    put("count", count)
    put("date", dateTimeFormat.format(date))
    put("shouldApplyProductionRule", shouldApplyProductionRule)
              }
              val r = makeRequest("sellBackofficeProductAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun refundBackofficeSellProductAtStorage(placeId: String, storageId: String, productId: String, count: Int, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("productId", productId)
    put("count", count)
    put("date", dateTimeFormat.format(date))
              }
              val r = makeRequest("refundBackofficeSellProductAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun cancelBackofficeSellProductAtStorage(placeId: String, storageId: String, productId: String, count: Int, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("productId", productId)
    put("count", count)
    put("date", dateTimeFormat.format(date))
              }
              val r = makeRequest("cancelBackofficeSellProductAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun lostBackofficeProductAtStorage(placeId: String, storageId: String, productId: String, count: Int, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("productId", productId)
    put("count", count)
    put("date", dateTimeFormat.format(date))
              }
              val r = makeRequest("lostBackofficeProductAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun manualBackofficeAdjustProductAtStorage(placeId: String, storageId: String, productId: String, count: Int, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("productId", productId)
    put("count", count)
    put("date", dateTimeFormat.format(date))
              }
              val r = makeRequest("manualBackofficeAdjustProductAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun transferBackofficeProduct(placeId: String, productId: String, fromId: String, toId: String, count: Int, date: Calendar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productId", productId)
    put("fromId", fromId)
    put("toId", toId)
    put("count", count)
    put("date", dateTimeFormat.format(date))
              }
              val r = makeRequest("transferBackofficeProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun transferBackofficeProducts(placeId: String, fromId: String, toId: String, date: Calendar, products: ArrayList<BackofficeMultipleStorageTransferProduct>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("fromId", fromId)
    put("toId", toId)
    put("date", dateTimeFormat.format(date))
    put("products", JSONArray().apply {
            products.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("transferBackofficeProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeProductAtStorage(placeId: String, storageId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("productId", productId)
              }
              val r = makeRequest("deleteBackofficeProductAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeProductsAtStorage(placeId: String, storageId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<BackofficeDeleteProductsResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("deleteBackofficeProductsAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeDeleteProductsResult::class.java)
              } else null
             MutableLiveData<Response<BackofficeDeleteProductsResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun zeroBackofficeAllProductsAtStorage(placeId: String, storageId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
              }
              val r = makeRequest("zeroBackofficeAllProductsAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun inputBackofficeProduct(placeId: String, input: BackofficeInput, transferType: BackofficeStorageTransferType?, referenceCode: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("input", gson.toJson(input))
    put("transferType", transferType?.let { transferType.name })
    put("referenceCode", referenceCode?.let { referenceCode })
              }
              val r = makeRequest("inputBackofficeProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun inputBackofficeProducts(placeId: String, inputs: ArrayList<BackofficeInput>, transferType: BackofficeStorageTransferType?, referenceCode: String?): Deferred<LiveData<Response<BackofficeInputsResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("inputs", JSONArray().apply {
            inputs.forEach { item -> put(gson.toJson(item)) }
          })
    put("transferType", transferType?.let { transferType.name })
    put("referenceCode", referenceCode?.let { referenceCode })
              }
              val r = makeRequest("inputBackofficeProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeInputsResult::class.java)
              } else null
             MutableLiveData<Response<BackofficeInputsResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeProductionRule(placeId: String, rule: BackofficeNewProductionRule): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("rule", gson.toJson(rule))
              }
              val r = makeRequest("createBackofficeProductionRule", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeProductionRuleInputs(placeId: String, productionRuleId: String, inputs: ArrayList<BackofficeProductionInput>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productionRuleId", productionRuleId)
    put("inputs", JSONArray().apply {
            inputs.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("editBackofficeProductionRuleInputs", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeProductionRule(placeId: String, ruleId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("ruleId", ruleId)
              }
              val r = makeRequest("deleteBackofficeProductionRule", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun applyBackofficeProductionRule(placeId: String, storageId: String, ruleId: String, inputs: ArrayList<BackofficeProductionInput>, outputs: ArrayList<BackofficeProductionOutput>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
    put("ruleId", ruleId)
    put("inputs", JSONArray().apply {
            inputs.forEach { item -> put(gson.toJson(item)) }
          })
    put("outputs", JSONArray().apply {
            outputs.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("applyBackofficeProductionRule", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductionRules(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeProductionRule>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeProductionRules", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeProductionRule>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeProductionRule>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeProductionRule>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeProductsStatusAtStorageAndDate(placeId: String, productIds: ArrayList<String>, storageId: String, date: Calendar): Deferred<LiveData<Response<ArrayList<BackofficeProductStatusAtStorage>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
    put("storageId", storageId)
    put("date", dateTimeFormat.format(date))
              }
              val r = makeRequest("getBackofficeProductsStatusAtStorageAndDate", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeProductStatusAtStorage>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeProductStatusAtStorage>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeProductStatusAtStorage>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeSupplier(id: String, placeId: String): Deferred<LiveData<Response<BackofficeSupplier?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeSupplier", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeSupplier::class.java)
              } else null
             MutableLiveData<Response<BackofficeSupplier?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeSuppliers(placeId: String): Deferred<LiveData<Response<ArrayList<BackofficeSupplier>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBackofficeSuppliers", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeSupplier>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeSupplier>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeSupplier>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createBackofficeSupplier(placeId: String, supplier: BackofficeNewSupplier): Deferred<LiveData<Response<BackofficeSupplier?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("supplier", gson.toJson(supplier))
              }
              val r = makeRequest("createBackofficeSupplier", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeSupplier::class.java)
              } else null
             MutableLiveData<Response<BackofficeSupplier?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeSupplier(placeId: String, id: String, supplier: BackofficeNewSupplier): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("supplier", gson.toJson(supplier))
              }
              val r = makeRequest("editBackofficeSupplier", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeSupplier(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
              }
              val r = makeRequest("deleteBackofficeSupplier", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBackofficeTransfers(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<BackofficeTransfer>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getBackofficeTransfers", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BackofficeTransfer>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BackofficeTransfer>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BackofficeTransfer>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun doBackofficeTransfer(placeId: String, transfer: BackofficeNewTransfer): Deferred<LiveData<Response<BackofficeTransfer?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("transfer", gson.toJson(transfer))
              }
              val r = makeRequest("doBackofficeTransfer", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeTransfer::class.java)
              } else null
             MutableLiveData<Response<BackofficeTransfer?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBackofficeTransfer(placeId: String, id: String, transfer: BackofficeNewTransfer): Deferred<LiveData<Response<BackofficeTransfer?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
    put("transfer", gson.toJson(transfer))
              }
              val r = makeRequest("editBackofficeTransfer", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeTransfer::class.java)
              } else null
             MutableLiveData<Response<BackofficeTransfer?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBackofficeTransfer(placeId: String, id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("id", id)
              }
              val r = makeRequest("deleteBackofficeTransfer", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editBar(bar: Bar): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("bar", gson.toJson(bar))
              }
              val r = makeRequest("editBar", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteBar(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteBar", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun addBar(placeId: String, barName: String, storageId: String, internalIp: String?): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("barName", barName)
    put("storageId", storageId)
    put("internalIp", internalIp?.let { internalIp })
              }
              val r = makeRequest("addBar", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBars(placeId: String): Deferred<LiveData<Response<ArrayList<Bar>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getBars", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Bar>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Bar>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Bar>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun activateProductAtBar(placeId: String, barId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("barId", barId)
    put("productId", productId)
              }
              val r = makeRequest("activateProductAtBar", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deactivateProductAtBar(placeId: String, barId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("barId", barId)
    put("productId", productId)
              }
              val r = makeRequest("deactivateProductAtBar", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun activateAllProductsAtBar(placeId: String, barId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("barId", barId)
              }
              val r = makeRequest("activateAllProductsAtBar", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deactivateAllProductsAtBar(placeId: String, barId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("barId", barId)
              }
              val r = makeRequest("deactivateAllProductsAtBar", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun activateProductsAtBar(placeId: String, barId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("barId", barId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("activateProductsAtBar", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deactivateProductsAtBar(placeId: String, barId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("barId", barId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("deactivateProductsAtBar", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun productsSoldAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("productsSoldAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ProductSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deliveredProductsByBarAtEvent(eventId: String, barId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("barId", barId)
              }
              val r = makeRequest("deliveredProductsByBarAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ProductSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun productsSoldAtPlace(since: Calendar, until: Calendar, placeId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("placeId", placeId)
              }
              val r = makeRequest("productsSoldAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ProductSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun productsSoldAtOrganization(since: Calendar, until: Calendar, organizationId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("organizationId", organizationId)
              }
              val r = makeRequest("productsSoldAtOrganization", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ProductSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deliveredProductsByBarAtPlace(since: Calendar, until: Calendar, placeId: String, barId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("placeId", placeId)
    put("barId", barId)
              }
              val r = makeRequest("deliveredProductsByBarAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ProductSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSellerReports(eventId: String): Deferred<LiveData<Response<ArrayList<SellerReport>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getSellerReports", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<SellerReport>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<SellerReport>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<SellerReport>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSellerReportForPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<SellerReport>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getSellerReportForPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<SellerReport>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<SellerReport>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<SellerReport>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getNotDeliveredProductsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<WaiterDelivery>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getNotDeliveredProductsAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<WaiterDelivery>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<WaiterDelivery>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<WaiterDelivery>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getConsolidatedBarSellingReport(eventId: String): Deferred<LiveData<Response<ArrayList<BarReport>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getConsolidatedBarSellingReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BarReport>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BarReport>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BarReport>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSellerDetails(eventId: String, sellerId: String): Deferred<LiveData<Response<ArrayList<SellerTransaction>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("sellerId", sellerId)
              }
              val r = makeRequest("getSellerDetails", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<SellerTransaction>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<SellerTransaction>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<SellerTransaction>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSellerDetailsPlace(placeId: String, sellerId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<SellerTransaction>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("sellerId", sellerId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getSellerDetailsPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<SellerTransaction>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<SellerTransaction>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<SellerTransaction>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSellerProductDetails(eventId: String, sellerId: String): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("sellerId", sellerId)
              }
              val r = makeRequest("getSellerProductDetails", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ProductSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSellerProductDetailsPlace(placeId: String, sellerId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("sellerId", sellerId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getSellerProductDetailsPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ProductSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun exportAllTransactionProductsXlsx(eventId: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("exportAllTransactionProductsXlsx", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSoldBaseProductsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<BaseProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getSoldBaseProductsAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BaseProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BaseProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BaseProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSoldBaseProductsAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<BaseProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getSoldBaseProductsAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BaseProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BaseProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BaseProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCashierClosingDetails(eventId: String, cashierId: String): Deferred<LiveData<Response<CashierClosingDetails?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("cashierId", cashierId)
              }
              val r = makeRequest("getCashierClosingDetails", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), CashierClosingDetails::class.java)
              } else null
             MutableLiveData<Response<CashierClosingDetails?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun bleedCashier(eventId: String, cashierId: String, value: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("cashierId", cashierId)
    put("value", value)
              }
              val r = makeRequest("bleedCashier", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun updateCashierClosing(eventId: String, cashierId: String, values: CashierClosingValues): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("cashierId", cashierId)
    put("values", gson.toJson(values))
              }
              val r = makeRequest("updateCashierClosing", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlainCategories(): Deferred<LiveData<Response<ArrayList<BaseCategory>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getPlainCategories", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BaseCategory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BaseCategory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BaseCategory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCategories(): Deferred<LiveData<Response<ArrayList<Category>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getCategories", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Category>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Category>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Category>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createCategory(category: NewCategory, _data: ByteArray?): Deferred<LiveData<Response<Category?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("category", gson.toJson(category))
    put("data", _data?.let { Base64.encodeToString(_data, Base64.DEFAULT) })
              }
              val r = makeRequest("createCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Category::class.java)
              } else null
             MutableLiveData<Response<Category?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editCategory(id: String, category: NewCategory, _data: ByteArray?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("category", gson.toJson(category))
    put("data", _data?.let { Base64.encodeToString(_data, Base64.DEFAULT) })
              }
              val r = makeRequest("editCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteCategory(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSellablesFromCategory(categoryId: String): Deferred<LiveData<Response<SellableList?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("categoryId", categoryId)
              }
              val r = makeRequest("getSellablesFromCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), SellableList::class.java)
              } else null
             MutableLiveData<Response<SellableList?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun filterSellablesCategories(name: String): Deferred<LiveData<Response<ArrayList<Category>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("name", name)
              }
              val r = makeRequest("filterSellablesCategories", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Category>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Category>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Category>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCostOfGoodsSoldByProductList(placeId: String, productIds: ArrayList<String>, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<CostOfGoodsSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getCostOfGoodsSoldByProductList", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<CostOfGoodsSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<CostOfGoodsSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<CostOfGoodsSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCurrentCostOfGoodsSoldByProductList(placeId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<ArrayList<CostOfGoodsSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("getCurrentCostOfGoodsSoldByProductList", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<CostOfGoodsSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<CostOfGoodsSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<CostOfGoodsSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getTotalCmvAtEvent(eventId: String): Deferred<LiveData<Response<Int?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getTotalCmvAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getInt("result")
              } else null
             MutableLiveData<Response<Int?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getTotalCmvAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<Int?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getTotalCmvAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getInt("result")
              } else null
             MutableLiveData<Response<Int?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun addNewCombo(placeId: String, combo: NewPlaceCombo, _data: ByteArray?): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("combo", gson.toJson(combo))
    put("data", _data?.let { Base64.encodeToString(_data, Base64.DEFAULT) })
              }
              val r = makeRequest("addNewCombo", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editCombo(comboId: String, combo: NewPlaceCombo): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("comboId", comboId)
    put("combo", gson.toJson(combo))
              }
              val r = makeRequest("editCombo", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editComboImage(comboId: String, _data: ByteArray): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("comboId", comboId)
    put("data", Base64.encodeToString(_data, Base64.DEFAULT))
              }
              val r = makeRequest("editComboImage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteCombo(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteCombo", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCoupon(id: String): Deferred<LiveData<Response<Coupon?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("getCoupon", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Coupon::class.java)
              } else null
             MutableLiveData<Response<Coupon?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCoupons(placeId: String): Deferred<LiveData<Response<ArrayList<Coupon>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getCoupons", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Coupon>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Coupon>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Coupon>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createCoupon(coupon: NewCoupon): Deferred<LiveData<Response<Coupon?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("coupon", gson.toJson(coupon))
              }
              val r = makeRequest("createCoupon", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Coupon::class.java)
              } else null
             MutableLiveData<Response<Coupon?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteCoupon(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteCoupon", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun addDeviceToOrganization(smallId: String): Deferred<LiveData<Response<Device?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("smallId", smallId)
              }
              val r = makeRequest("addDeviceToOrganization", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Device::class.java)
              } else null
             MutableLiveData<Response<Device?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getDevices(): Deferred<LiveData<Response<ArrayList<Device>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getDevices", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Device>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Device>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Device>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun removeDeviceFromOrganization(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("removeDeviceFromOrganization", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getDeviceStatusAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<DeviceStatus>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getDeviceStatusAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<DeviceStatus>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<DeviceStatus>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<DeviceStatus>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun removeDeviceFromEvent(deviceId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("deviceId", deviceId)
              }
              val r = makeRequest("removeDeviceFromEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun logOutAndRemoveAllDevices(): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("logOutAndRemoveAllDevices", null)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getDeviceEventStatus(eventId: String): Deferred<LiveData<Response<ArrayList<DeviceEventStatus>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getDeviceEventStatus", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<DeviceEventStatus>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<DeviceEventStatus>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<DeviceEventStatus>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEmployees(): Deferred<LiveData<Response<ArrayList<Employee>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getEmployees", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Employee>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Employee>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Employee>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEmployee(employeeId: String): Deferred<LiveData<Response<Employee?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("employeeId", employeeId)
              }
              val r = makeRequest("getEmployee", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Employee::class.java)
              } else null
             MutableLiveData<Response<Employee?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun addEmployee(employee: NewEmployee, password: String, image: ByteArray?): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("employee", gson.toJson(employee))
    put("password", password)
    put("image", image?.let { Base64.encodeToString(image, Base64.DEFAULT) })
              }
              val r = makeRequest("addEmployee", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editEmployee(id: String, employee: NewEmployee): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("employee", gson.toJson(employee))
              }
              val r = makeRequest("editEmployee", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editEmployeePassword(id: String, password: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("password", password)
              }
              val r = makeRequest("editEmployeePassword", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editEmployeeImage(id: String, _data: ByteArray): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("data", Base64.encodeToString(_data, Base64.DEFAULT))
              }
              val r = makeRequest("editEmployeeImage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editMultipleEmployees(employeeIds: ArrayList<String>, edition: EditMultipleEmployees): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("employeeIds", JSONArray().apply {
            employeeIds.forEach { item -> put(item) }
          })
    put("edition", gson.toJson(edition))
              }
              val r = makeRequest("editMultipleEmployees", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun removeMultipleEmployees(employeeIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("employeeIds", JSONArray().apply {
            employeeIds.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("removeMultipleEmployees", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun removeEmployee(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("removeEmployee", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun addZigTagToWaiter(zigCode: String, employeeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("zigCode", zigCode)
    put("employeeId", employeeId)
              }
              val r = makeRequest("addZigTagToWaiter", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun removeZigTagFromWaiter(employeeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("employeeId", employeeId)
              }
              val r = makeRequest("removeZigTagFromWaiter", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createMultipleEmployees(placeId: String, bar: String?, quantity: Int, role: String?, permissions: ArrayList<String>, username: String, password: String, initial: Int, _final: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("bar", bar?.let { bar })
    put("quantity", quantity)
    put("role", role?.let { role })
    put("permissions", JSONArray().apply {
            permissions.forEach { item -> put(item) }
          })
    put("username", username)
    put("password", password)
    put("initial", initial)
    put("final", _final)
              }
              val r = makeRequest("createMultipleEmployees", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createEntrance(placeId: String, entrance: Entrance, _data: ByteArray): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("entrance", gson.toJson(entrance))
    put("data", Base64.encodeToString(_data, Base64.DEFAULT))
              }
              val r = makeRequest("createEntrance", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteEntrance(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteEntrance", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAllEntrances(placeId: String): Deferred<LiveData<Response<ArrayList<Entrance>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getAllEntrances", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Entrance>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Entrance>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Entrance>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editEntrance(placeId: String, entrance: Entrance): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("entrance", gson.toJson(entrance))
              }
              val r = makeRequest("editEntrance", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editEntranceImage(entranceId: String, _data: ByteArray): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("entranceId", entranceId)
    put("data", Base64.encodeToString(_data, Base64.DEFAULT))
              }
              val r = makeRequest("editEntranceImage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun entrancesSoldAtPlace(since: Calendar, until: Calendar, placeId: String): Deferred<LiveData<Response<ArrayList<EntranceSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("placeId", placeId)
              }
              val r = makeRequest("entrancesSoldAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<EntranceSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<EntranceSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<EntranceSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun entrancesSoldAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<EntranceSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("entrancesSoldAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<EntranceSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<EntranceSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<EntranceSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun entrancesSoldByEmployee(eventId: String): Deferred<LiveData<Response<ArrayList<Bilheteiro>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("entrancesSoldByEmployee", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Bilheteiro>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Bilheteiro>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Bilheteiro>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEntrancesSoldToUserAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<EntranceUser>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getEntrancesSoldToUserAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<EntranceUser>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<EntranceUser>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<EntranceUser>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEntrancesSoldToUserAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<EntranceUser>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getEntrancesSoldToUserAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<EntranceUser>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<EntranceUser>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<EntranceUser>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAllEvents(since: Calendar, until: Calendar, placeId: String): Deferred<LiveData<Response<ArrayList<Event>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("placeId", placeId)
              }
              val r = makeRequest("getAllEvents", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Event>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Event>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Event>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createEvent(details: EventDetails, image: ByteArray?, cover: ByteArray?): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("details", gson.toJson(details))
    put("image", image?.let { Base64.encodeToString(image, Base64.DEFAULT) })
    put("cover", cover?.let { Base64.encodeToString(cover, Base64.DEFAULT) })
              }
              val r = makeRequest("createEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun openEvent(id: String, actualizingdatetime: Boolean): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("actualizingdatetime", actualizingdatetime)
              }
              val r = makeRequest("openEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun closeEvent(id: String): Deferred<LiveData<Response<ArrayList<OpenedReports>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("closeEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<OpenedReports>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<OpenedReports>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<OpenedReports>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteEvent(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEvent(id: String): Deferred<LiveData<Response<Event?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("getEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Event::class.java)
              } else null
             MutableLiveData<Response<Event?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEvents(placeId: String, month: Int, year: Int): Deferred<LiveData<Response<EventList?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("month", month)
    put("year", year)
              }
              val r = makeRequest("getEvents", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), EventList::class.java)
              } else null
             MutableLiveData<Response<EventList?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun extract(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<DayResume>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("extract", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<DayResume>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<DayResume>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<DayResume>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun extractResume(placeId: String): Deferred<LiveData<Response<ExtractResume?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("extractResume", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ExtractResume::class.java)
              } else null
             MutableLiveData<Response<ExtractResume?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getExtractDayResumeInSections(placeId: String, date: Calendar): Deferred<LiveData<Response<ArrayList<ExtractDayResumeSection>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("date", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date))
              }
              val r = makeRequest("getExtractDayResumeInSections", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ExtractDayResumeSection>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ExtractDayResumeSection>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ExtractDayResumeSection>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getRechargeExpireResume(placeId: String): Deferred<LiveData<Response<RechargeExpireResume?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getRechargeExpireResume", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), RechargeExpireResume::class.java)
              } else null
             MutableLiveData<Response<RechargeExpireResume?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getRechargeExpireExtract(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<RechargeExpireDayResume>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getRechargeExpireExtract", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<RechargeExpireDayResume>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RechargeExpireDayResume>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<RechargeExpireDayResume>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun askForAnticipation(placeId: String, value: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("value", value)
              }
              val r = makeRequest("askForAnticipation", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun withdraw(placeId: String, value: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("value", value)
              }
              val r = makeRequest("withdraw", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun askForAnticipationWithObservation(placeId: String, value: Int, obs: String?, otherBankAccount: WithdrawBankAccount?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("value", value)
    put("obs", obs?.let { obs })
    put("otherBankAccount", otherBankAccount?.let { gson.toJson(otherBankAccount) })
              }
              val r = makeRequest("askForAnticipationWithObservation", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun withdrawWithObservation(placeId: String, value: Int, obs: String?, otherBankAccount: WithdrawBankAccount?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("value", value)
    put("obs", obs?.let { obs })
    put("otherBankAccount", otherBankAccount?.let { gson.toJson(otherBankAccount) })
              }
              val r = makeRequest("withdrawWithObservation", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getWithdrawsResume(placeId: String, from: Calendar): Deferred<LiveData<Response<ArrayList<Withdraw>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("from", dateTimeFormat.format(from))
              }
              val r = makeRequest("getWithdrawsResume", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Withdraw>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Withdraw>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Withdraw>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getWithdraws(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<Withdraw>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getWithdraws", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Withdraw>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Withdraw>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Withdraw>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getExtractAdjusts(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ExtractAdjust>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getExtractAdjusts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ExtractAdjust>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ExtractAdjust>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ExtractAdjust>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getConsolidatedExtractInSections(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ConsolidatedExtract?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getConsolidatedExtractInSections", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ConsolidatedExtract::class.java)
              } else null
             MutableLiveData<Response<ConsolidatedExtract?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun cashierDetailsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<Cashier>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("cashierDetailsAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Cashier>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Cashier>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Cashier>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun clientsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<Client>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("clientsAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Client>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Client>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Client>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun clientsAtPlace(since: Calendar, until: Calendar, placeId: String): Deferred<LiveData<Response<ArrayList<Client>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("placeId", placeId)
              }
              val r = makeRequest("clientsAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Client>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Client>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Client>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun clientAtPlace(userId: String, placeId: String): Deferred<LiveData<Response<Client?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("userId", userId)
    put("placeId", placeId)
              }
              val r = makeRequest("clientAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Client::class.java)
              } else null
             MutableLiveData<Response<Client?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun clientTransactionsAtEvent(userId: String, eventId: String): Deferred<LiveData<Response<ArrayList<UserTransaction>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("userId", userId)
    put("eventId", eventId)
              }
              val r = makeRequest("clientTransactionsAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<UserTransaction>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UserTransaction>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<UserTransaction>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun resumeForOrganization(since: Calendar, until: Calendar, organizationId: String): Deferred<LiveData<Response<ReceiptResume?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("organizationId", organizationId)
              }
              val r = makeRequest("resumeForOrganization", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ReceiptResume::class.java)
              } else null
             MutableLiveData<Response<ReceiptResume?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun cashierDetailsAtPlace(since: Calendar, until: Calendar, placeId: String): Deferred<LiveData<Response<ArrayList<Cashier>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
    put("placeId", placeId)
              }
              val r = makeRequest("cashierDetailsAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Cashier>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Cashier>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Cashier>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun debtorsInEvent(eventId: String): Deferred<LiveData<Response<ArrayList<Debtor>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("debtorsInEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Debtor>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Debtor>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Debtor>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun debtorsInPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<Debtor>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("debtorsInPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Debtor>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Debtor>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Debtor>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBonusReport(eventId: String): Deferred<LiveData<Response<ArrayList<UserBonusReport>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getBonusReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<UserBonusReport>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UserBonusReport>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<UserBonusReport>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBonusReportForPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<UserBonusReport>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getBonusReportForPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<UserBonusReport>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UserBonusReport>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<UserBonusReport>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getMinimumConsumptionReport(eventId: String): Deferred<LiveData<Response<ArrayList<UserBonusReport>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getMinimumConsumptionReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<UserBonusReport>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UserBonusReport>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<UserBonusReport>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCashierRechargeDetails(eventId: String, cashierId: String): Deferred<LiveData<Response<ArrayList<RechargeDetails>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("cashierId", cashierId)
              }
              val r = makeRequest("getCashierRechargeDetails", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<RechargeDetails>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RechargeDetails>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<RechargeDetails>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCashierPostDetails(eventId: String, cashierId: String): Deferred<LiveData<Response<ArrayList<PostDetails>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("cashierId", cashierId)
              }
              val r = makeRequest("getCashierPostDetails", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<PostDetails>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PostDetails>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<PostDetails>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getClientDetails(clientId: String): Deferred<LiveData<Response<ClientDetails?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("clientId", clientId)
              }
              val r = makeRequest("getClientDetails", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ClientDetails::class.java)
              } else null
             MutableLiveData<Response<ClientDetails?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editClient(clientId: String, name: String, phone: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("clientId", clientId)
    put("name", name)
    put("phone", phone)
              }
              val r = makeRequest("editClient", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBonusByProduct(eventId: String): Deferred<LiveData<Response<ArrayList<BonusProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getBonusByProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BonusProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BonusProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BonusProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBonusByProductAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<BonusProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getBonusByProductAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<BonusProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<BonusProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<BonusProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getRechargeConsumptions(eventId: String): Deferred<LiveData<Response<ArrayList<RechargeConsumption>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getRechargeConsumptions", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<RechargeConsumption>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RechargeConsumption>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<RechargeConsumption>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun payBillAtEventWithMultiplePayments(userId: String, eventId: String, payments: ArrayList<BillPayment>, reason: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("userId", userId)
    put("eventId", eventId)
    put("payments", JSONArray().apply {
            payments.forEach { item -> put(gson.toJson(item)) }
          })
    put("reason", reason?.let { reason })
              }
              val r = makeRequest("payBillAtEventWithMultiplePayments", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun payBillAtPlaceInPeriodWithMultiplePayments(userId: String, placeId: String, payments: ArrayList<BillPayment>, since: Calendar, until: Calendar, reason: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("userId", userId)
    put("placeId", placeId)
    put("payments", JSONArray().apply {
            payments.forEach { item -> put(gson.toJson(item)) }
          })
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
    put("reason", reason?.let { reason })
              }
              val r = makeRequest("payBillAtPlaceInPeriodWithMultiplePayments", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun payBillsAtEventWithMultiplePayments(userIds: ArrayList<String>, eventId: String, method: PaymentMethod, isBonus: Boolean): Deferred<LiveData<Response<MultipleUserIdsResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("userIds", JSONArray().apply {
            userIds.forEach { item -> put(item) }
          })
    put("eventId", eventId)
    put("method", method.name)
    put("isBonus", isBonus)
              }
              val r = makeRequest("payBillsAtEventWithMultiplePayments", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), MultipleUserIdsResult::class.java)
              } else null
             MutableLiveData<Response<MultipleUserIdsResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun payBillsAtPlaceInPeriodWithMultiplePayments(userIds: ArrayList<String>, placeId: String, since: Calendar, until: Calendar, method: PaymentMethod, isBonus: Boolean): Deferred<LiveData<Response<MultipleUserIdsResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("userIds", JSONArray().apply {
            userIds.forEach { item -> put(item) }
          })
    put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
    put("method", method.name)
    put("isBonus", isBonus)
              }
              val r = makeRequest("payBillsAtPlaceInPeriodWithMultiplePayments", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), MultipleUserIdsResult::class.java)
              } else null
             MutableLiveData<Response<MultipleUserIdsResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getRefundedRechargesAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<RefundedRecharge>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getRefundedRechargesAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<RefundedRecharge>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RefundedRecharge>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<RefundedRecharge>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCardActivationReturnReportAtEvent(eventId: String): Deferred<LiveData<Response<ActivationReturnReport?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getCardActivationReturnReportAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ActivationReturnReport::class.java)
              } else null
             MutableLiveData<Response<ActivationReturnReport?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getResumeForEventInSections(eventId: String): Deferred<LiveData<Response<ArrayList<EventResumeSection>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getResumeForEventInSections", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<EventResumeSection>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<EventResumeSection>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<EventResumeSection>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getResumeForPlaceInSections(placeId: String, from: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<EventResumeSection>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("from", dateTimeFormat.format(from))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getResumeForPlaceInSections", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<EventResumeSection>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<EventResumeSection>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<EventResumeSection>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPromotionsUsedAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<UsedPromotion>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getPromotionsUsedAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<UsedPromotion>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UsedPromotion>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<UsedPromotion>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPromotionsUsedAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<UsedPromotion>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getPromotionsUsedAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<UsedPromotion>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UsedPromotion>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<UsedPromotion>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEntrancesSoldByEmployeeAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<SoldEntranceWithEmployee>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getEntrancesSoldByEmployeeAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<SoldEntranceWithEmployee>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<SoldEntranceWithEmployee>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<SoldEntranceWithEmployee>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEntrancesSoldByEmployeeAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<SoldEntranceWithEmployee>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getEntrancesSoldByEmployeeAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<SoldEntranceWithEmployee>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<SoldEntranceWithEmployee>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<SoldEntranceWithEmployee>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getRefundedProductsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<RefundedProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getRefundedProductsAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<RefundedProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RefundedProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<RefundedProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getRefundedProductsAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<RefundedProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getRefundedProductsAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<RefundedProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RefundedProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<RefundedProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getTipsRemovedAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<TipRemovedList>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getTipsRemovedAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<TipRemovedList>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<TipRemovedList>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<TipRemovedList>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getTipsRemovedAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<TipRemovedList>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getTipsRemovedAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<TipRemovedList>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<TipRemovedList>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<TipRemovedList>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCardActivationReturnReportByEmployeeAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<CardReturnEmployee>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getCardActivationReturnReportByEmployeeAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<CardReturnEmployee>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<CardReturnEmployee>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<CardReturnEmployee>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getDiscountsAtEventByEmployee(eventId: String): Deferred<LiveData<Response<ArrayList<DiscountEmployee>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getDiscountsAtEventByEmployee", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<DiscountEmployee>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<DiscountEmployee>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<DiscountEmployee>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getDiscountsAtPlaceByEmployee(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<DiscountEmployee>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getDiscountsAtPlaceByEmployee", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<DiscountEmployee>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<DiscountEmployee>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<DiscountEmployee>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getTransactionTransfersByEmployee(eventId: String): Deferred<LiveData<Response<ArrayList<TransferEmployee>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getTransactionTransfersByEmployee", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<TransferEmployee>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<TransferEmployee>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<TransferEmployee>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getManualInvoices(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ManualInvoice>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getManualInvoices", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ManualInvoice>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ManualInvoice>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ManualInvoice>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPostLimitChangesAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<PostLimitChange>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getPostLimitChangesAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<PostLimitChange>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PostLimitChange>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<PostLimitChange>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPostLimitChanges(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<PostLimitChange>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getPostLimitChanges", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<PostLimitChange>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PostLimitChange>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<PostLimitChange>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun emitInnovaroGeneralInvoices(eventId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("emitInnovaroGeneralInvoices", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getOpenedBillPayments(eventId: String): Deferred<LiveData<Response<ArrayList<OpenedBillPayment>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getOpenedBillPayments", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<OpenedBillPayment>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<OpenedBillPayment>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<OpenedBillPayment>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getOpenedBillPaymentsAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<OpenedBillPayment>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getOpenedBillPaymentsAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<OpenedBillPayment>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<OpenedBillPayment>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<OpenedBillPayment>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getRappiDiscountsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<RappiDiscount>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getRappiDiscountsAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<RappiDiscount>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RappiDiscount>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<RappiDiscount>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getRappiDiscountsAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<RappiDiscount>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getRappiDiscountsAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<RappiDiscount>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RappiDiscount>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<RappiDiscount>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun setCouvertFiscalData(placeId: String, _data: ProductFiscalData): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("data", gson.toJson(_data))
              }
              val r = makeRequest("setCouvertFiscalData", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCouvertFiscalData(placeId: String): Deferred<LiveData<Response<ProductFiscalData??>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getCouvertFiscalData", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ProductFiscalData::class.java)
              } else null
             MutableLiveData<Response<ProductFiscalData??>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun searchFiscalProducts(query: String): Deferred<LiveData<Response<ArrayList<FiscalProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("query", query)
              }
              val r = makeRequest("searchFiscalProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<FiscalProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<FiscalProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<FiscalProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun isNcmValid(ncm: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("ncm", ncm)
              }
              val r = makeRequest("isNcmValid", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getBoolean("result")
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getInvoicePrintout(invoiceId: String): Deferred<LiveData<Response<ArrayList<ByteArray>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("invoiceId", invoiceId)
              }
              val r = makeRequest("getInvoicePrintout", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ByteArray>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ByteArray>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ByteArray>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun issueInvoicesForUser(eventId: String, userId: String): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("userId", userId)
              }
              val r = makeRequest("issueInvoicesForUser", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), IssueResult::class.java)
              } else null
             MutableLiveData<Response<IssueResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun emitAllInvoicesInMonth(placeId: String): Deferred<LiveData<Response<SimpleIssueResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("emitAllInvoicesInMonth", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), SimpleIssueResult::class.java)
              } else null
             MutableLiveData<Response<SimpleIssueResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun issueInvoicesForCnpj(eventId: String, userId: String, cnpj: String): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("userId", userId)
    put("cnpj", cnpj)
              }
              val r = makeRequest("issueInvoicesForCnpj", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), IssueResult::class.java)
              } else null
             MutableLiveData<Response<IssueResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun issueInvoicesForCpf(eventId: String, userId: String, cpf: String): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("userId", userId)
    put("cpf", cpf)
              }
              val r = makeRequest("issueInvoicesForCpf", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), IssueResult::class.java)
              } else null
             MutableLiveData<Response<IssueResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun issueInvoicesOfTransactions(eventId: String, transactionIds: ArrayList<String>, cpf: String?, cnpj: String?): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("transactionIds", JSONArray().apply {
            transactionIds.forEach { item -> put(item) }
          })
    put("cpf", cpf?.let { cpf })
    put("cnpj", cnpj?.let { cnpj })
              }
              val r = makeRequest("issueInvoicesOfTransactions", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), IssueResult::class.java)
              } else null
             MutableLiveData<Response<IssueResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getInvoices(eventId: String): Deferred<LiveData<Response<ArrayList<Invoice>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getInvoices", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Invoice>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Invoice>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Invoice>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getInvoicesFromUser(eventId: String, userId: String): Deferred<LiveData<Response<ArrayList<Invoice>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("userId", userId)
              }
              val r = makeRequest("getInvoicesFromUser", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Invoice>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Invoice>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Invoice>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getInvoicesFromUserAtPlace(placeId: String, userId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<InvoiceWithEvent>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("userId", userId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getInvoicesFromUserAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<InvoiceWithEvent>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<InvoiceWithEvent>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<InvoiceWithEvent>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun hasPendingInvoices(eventId: String, userId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("userId", userId)
              }
              val r = makeRequest("hasPendingInvoices", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getBoolean("result")
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun sendInvoicesByEmail(invoiceIds: ArrayList<String>, email: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("invoiceIds", JSONArray().apply {
            invoiceIds.forEach { item -> put(item) }
          })
    put("email", email?.let { email })
              }
              val r = makeRequest("sendInvoicesByEmail", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun printInvoice(invoiceId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("invoiceId", invoiceId)
              }
              val r = makeRequest("printInvoice", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun hasPrinter(invoiceId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("invoiceId", invoiceId)
              }
              val r = makeRequest("hasPrinter", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getBoolean("result")
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getFiscalProfiles(): Deferred<LiveData<Response<ArrayList<FiscalProfile>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getFiscalProfiles", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<FiscalProfile>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<FiscalProfile>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<FiscalProfile>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getFiscalProductGroups(): Deferred<LiveData<Response<ArrayList<FiscalProductGroup>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getFiscalProductGroups", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<FiscalProductGroup>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<FiscalProductGroup>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<FiscalProductGroup>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createFiscalProfile(fiscalProfile: FiscalProfile): Deferred<LiveData<Response<FiscalProfile?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("fiscalProfile", gson.toJson(fiscalProfile))
              }
              val r = makeRequest("createFiscalProfile", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), FiscalProfile::class.java)
              } else null
             MutableLiveData<Response<FiscalProfile?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun updateFiscalProfile(fiscalProfile: FiscalProfile): Deferred<LiveData<Response<FiscalProfile?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("fiscalProfile", gson.toJson(fiscalProfile))
              }
              val r = makeRequest("updateFiscalProfile", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), FiscalProfile::class.java)
              } else null
             MutableLiveData<Response<FiscalProfile?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteFiscalProfile(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteFiscalProfile", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createFiscalProductGroup(fiscalProductGroup: FiscalProductGroup): Deferred<LiveData<Response<FiscalProductGroup?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("fiscalProductGroup", gson.toJson(fiscalProductGroup))
              }
              val r = makeRequest("createFiscalProductGroup", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), FiscalProductGroup::class.java)
              } else null
             MutableLiveData<Response<FiscalProductGroup?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun updateFiscalProductGroup(fiscalProductGroup: FiscalProductGroup): Deferred<LiveData<Response<FiscalProductGroup?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("fiscalProductGroup", gson.toJson(fiscalProductGroup))
              }
              val r = makeRequest("updateFiscalProductGroup", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), FiscalProductGroup::class.java)
              } else null
             MutableLiveData<Response<FiscalProductGroup?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteFiscalProductGroup(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteFiscalProductGroup", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getFiscalInvoices(placeId: String, fiscalProfileId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<Invoice>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("fiscalProfileId", fiscalProfileId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getFiscalInvoices", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Invoice>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Invoice>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Invoice>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPercentOfSalesIssuedInPeriod(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<SalesIssuedInPeriod?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getPercentOfSalesIssuedInPeriod", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), SalesIssuedInPeriod::class.java)
              } else null
             MutableLiveData<Response<SalesIssuedInPeriod?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getInvoice(id: String): Deferred<LiveData<Response<Invoice?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("getInvoice", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Invoice::class.java)
              } else null
             MutableLiveData<Response<Invoice?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getZippedInvoices(placeId: String, fiscalProfileId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("fiscalProfileId", fiscalProfileId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getZippedInvoices", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getNotEmmitedTransactionsFromEvent(eventId: String, userId: String): Deferred<LiveData<Response<ArrayList<NotEmmitedTransaction>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("userId", userId)
              }
              val r = makeRequest("getNotEmmitedTransactionsFromEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<NotEmmitedTransaction>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<NotEmmitedTransaction>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<NotEmmitedTransaction>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun issueManualInvoice(eventId: String, manualRequest: ManualRequest, cpf: String?, cnpj: String?): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("manualRequest", gson.toJson(manualRequest))
    put("cpf", cpf?.let { cpf })
    put("cnpj", cnpj?.let { cnpj })
              }
              val r = makeRequest("issueManualInvoice", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), IssueResult::class.java)
              } else null
             MutableLiveData<Response<IssueResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun issueManualInvoiceForReserve(eventId: String, manualRequest: ManualRequest, cpf: String?, cnpj: String?, reserveId: String): Deferred<LiveData<Response<IssueResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("manualRequest", gson.toJson(manualRequest))
    put("cpf", cpf?.let { cpf })
    put("cnpj", cnpj?.let { cnpj })
    put("reserveId", reserveId)
              }
              val r = makeRequest("issueManualInvoiceForReserve", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), IssueResult::class.java)
              } else null
             MutableLiveData<Response<IssueResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun emitAllInnovaroInvoices(eventId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("emitAllInnovaroInvoices", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getNotEmittedProductsInEvent(eventId: String): Deferred<LiveData<Response<ArrayList<NotEmittedProductOrCombo>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getNotEmittedProductsInEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<NotEmittedProductOrCombo>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<NotEmittedProductOrCombo>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<NotEmittedProductOrCombo>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getNotEmittedProducts(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<NotEmittedProductOrCombo>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getNotEmittedProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<NotEmittedProductOrCombo>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<NotEmittedProductOrCombo>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<NotEmittedProductOrCombo>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getInvoicePrintData(invoiceId: String): Deferred<LiveData<Response<InvoicePrintData?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("invoiceId", invoiceId)
              }
              val r = makeRequest("getInvoicePrintData", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), InvoicePrintData::class.java)
              } else null
             MutableLiveData<Response<InvoicePrintData?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createInventory(placeId: String, storageIds: ArrayList<String>, newInventory: NewInventory): Deferred<LiveData<Response<Inventory?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageIds", JSONArray().apply {
            storageIds.forEach { item -> put(item) }
          })
    put("newInventory", gson.toJson(newInventory))
              }
              val r = makeRequest("createInventory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Inventory::class.java)
              } else null
             MutableLiveData<Response<Inventory?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun cancelInventory(inventoryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("inventoryId", inventoryId)
              }
              val r = makeRequest("cancelInventory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun addStorageToInventory(inventoryId: String, storageIds: ArrayList<String>): Deferred<LiveData<Response<Inventory?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("inventoryId", inventoryId)
    put("storageIds", JSONArray().apply {
            storageIds.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("addStorageToInventory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Inventory::class.java)
              } else null
             MutableLiveData<Response<Inventory?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getInventories(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<Inventory>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getInventories", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Inventory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Inventory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Inventory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getOpenedInventories(placeId: String): Deferred<LiveData<Response<ArrayList<Inventory>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getOpenedInventories", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Inventory>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Inventory>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Inventory>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun closeInventory(inventoryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("inventoryId", inventoryId)
              }
              val r = makeRequest("closeInventory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getInventoryProductsAtStorage(inventoryId: String, storageId: String): Deferred<LiveData<Response<ArrayList<InventoryProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("inventoryId", inventoryId)
    put("storageId", storageId)
              }
              val r = makeRequest("getInventoryProductsAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<InventoryProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<InventoryProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<InventoryProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getInventoryProducts(inventoryId: String): Deferred<LiveData<Response<ArrayList<InventoryProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("inventoryId", inventoryId)
              }
              val r = makeRequest("getInventoryProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<InventoryProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<InventoryProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<InventoryProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun updateInventoryProduct(inventoryId: String, products: ArrayList<NewInventoryProduct>): Deferred<LiveData<Response<ArrayList<InventoryProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("inventoryId", inventoryId)
    put("products", JSONArray().apply {
            products.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("updateInventoryProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<InventoryProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<InventoryProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<InventoryProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getLiveResume(eventId: String): Deferred<LiveData<Response<LiveResume?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getLiveResume", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), LiveResume::class.java)
              } else null
             MutableLiveData<Response<LiveResume?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPublic(eventId: String): Deferred<LiveData<Response<Public?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getPublic", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Public::class.java)
              } else null
             MutableLiveData<Response<Public?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getNewPublic(eventId: String): Deferred<LiveData<Response<NewPublic?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getNewPublic", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), NewPublic::class.java)
              } else null
             MutableLiveData<Response<NewPublic?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getTopSellingProducts(eventId: String): Deferred<LiveData<Response<TopSellingProducts?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getTopSellingProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), TopSellingProducts::class.java)
              } else null
             MutableLiveData<Response<TopSellingProducts?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSelledProducts(eventId: String): Deferred<LiveData<Response<ArrayList<SelledProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getSelledProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<SelledProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<SelledProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<SelledProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getTotalReceipt(eventId: String): Deferred<LiveData<Response<TotalReceipt?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getTotalReceipt", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), TotalReceipt::class.java)
              } else null
             MutableLiveData<Response<TotalReceipt?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getRechargeTimeline(eventId: String): Deferred<LiveData<Response<ArrayList<ConsumptionTimeline>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getRechargeTimeline", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ConsumptionTimeline>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ConsumptionTimeline>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ConsumptionTimeline>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getConsumptionTimeline(eventId: String): Deferred<LiveData<Response<ArrayList<ConsumptionTimeline>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getConsumptionTimeline", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ConsumptionTimeline>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ConsumptionTimeline>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ConsumptionTimeline>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getTopClients(eventId: String): Deferred<LiveData<Response<ArrayList<TopSelling>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getTopClients", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<TopSelling>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<TopSelling>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<TopSelling>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getBarConsumptionResume(eventId: String): Deferred<LiveData<Response<ConsumptionResume?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getBarConsumptionResume", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ConsumptionResume::class.java)
              } else null
             MutableLiveData<Response<ConsumptionResume?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEntrancesConsumptionResume(eventId: String): Deferred<LiveData<Response<ConsumptionResume?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getEntrancesConsumptionResume", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ConsumptionResume::class.java)
              } else null
             MutableLiveData<Response<ConsumptionResume?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun logIn(username: String, password: String, organizationUsername: String): Deferred<LiveData<Response<Employee?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("username", username)
    put("password", password)
    put("organizationUsername", organizationUsername)
              }
              val r = makeRequest("logIn", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Employee::class.java)
              } else null
             MutableLiveData<Response<Employee?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun logOut(): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("logOut", null)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCurrentEmployee(): Deferred<LiveData<Response<Employee?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getCurrentEmployee", null)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Employee::class.java)
              } else null
             MutableLiveData<Response<Employee?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getCurrentOrganization(): Deferred<LiveData<Response<Organization?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getCurrentOrganization", null)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Organization::class.java)
              } else null
             MutableLiveData<Response<Organization?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlaceDetails(placeId: String): Deferred<LiveData<Response<PlaceContract?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getPlaceDetails", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), PlaceContract::class.java)
              } else null
             MutableLiveData<Response<PlaceContract?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlaces(): Deferred<LiveData<Response<ArrayList<Place>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getPlaces", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Place>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Place>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Place>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlace(placeId: String): Deferred<LiveData<Response<PlaceDetails?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), PlaceDetails::class.java)
              } else null
             MutableLiveData<Response<PlaceDetails?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editPlace(place: NewPlace, image: ByteArray?): Deferred<LiveData<Response<Place?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("place", gson.toJson(place))
    put("image", image?.let { Base64.encodeToString(image, Base64.DEFAULT) })
              }
              val r = makeRequest("editPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Place::class.java)
              } else null
             MutableLiveData<Response<Place?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlaceFeatures(placeId: String): Deferred<LiveData<Response<ArrayList<PlaceFeature>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getPlaceFeatures", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<PlaceFeature>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PlaceFeature>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<PlaceFeature>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun activePlaceFeature(placeId: String, featureId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("featureId", featureId)
              }
              val r = makeRequest("activePlaceFeature", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deactivePlaceFeature(placeId: String, featureId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("featureId", featureId)
              }
              val r = makeRequest("deactivePlaceFeature", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun setZigPlaceFeatures(placeId: String, featureIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("featureIds", JSONArray().apply {
            featureIds.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("setZigPlaceFeatures", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editPlacePassword(placeId: String, pass: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("pass", pass?.let { pass })
              }
              val r = makeRequest("editPlacePassword", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlacePassword(placeId: String): Deferred<LiveData<Response<String??>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getPlacePassword", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String??>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun overridePlaceAccountId(placeId: String, accountId: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("accountId", accountId?.let { accountId })
              }
              val r = makeRequest("overridePlaceAccountId", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getOverridenPlaceAccount(placeId: String): Deferred<LiveData<Response<BackofficeAccount??>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getOverridenPlaceAccount", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), BackofficeAccount::class.java)
              } else null
             MutableLiveData<Response<BackofficeAccount??>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editPlaceEverestConfig(placeId: String, config: EverestConfig?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("config", config?.let { gson.toJson(config) })
              }
              val r = makeRequest("editPlaceEverestConfig", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlaceEverestConfig(placeId: String): Deferred<LiveData<Response<EverestConfig??>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getPlaceEverestConfig", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), EverestConfig::class.java)
              } else null
             MutableLiveData<Response<EverestConfig??>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAllPlaces(): Deferred<LiveData<Response<ArrayList<Place>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getAllPlaces", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Place>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Place>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Place>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPosMachineTransactions(placeId: String, dates: ArrayList<Calendar>, page: Int, searchTerm: String?, itemsPerPage: Int?): Deferred<LiveData<Response<PaginationPosTransaction?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("dates", JSONArray().apply {
            dates.forEach { item -> put(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item)) }
          })
    put("page", page)
    put("searchTerm", searchTerm?.let { searchTerm })
    put("itemsPerPage", itemsPerPage?.let { itemsPerPage })
              }
              val r = makeRequest("getPosMachineTransactions", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), PaginationPosTransaction::class.java)
              } else null
             MutableLiveData<Response<PaginationPosTransaction?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getConsolidatedPosMachineTransactions(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<PosMachineDayResume>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getConsolidatedPosMachineTransactions", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<PosMachineDayResume>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PosMachineDayResume>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<PosMachineDayResume>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun activateProductInPlace(placeId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productId", productId)
              }
              val r = makeRequest("activateProductInPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deactivateProductInPlace(placeId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productId", productId)
              }
              val r = makeRequest("deactivateProductInPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun updatePriceAtPlace(placeId: String, productId: String, value: Int): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productId", productId)
    put("value", value)
              }
              val r = makeRequest("updatePriceAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun updateProportionalValueAtPlace(placeId: String, productId: String, proportionalValue: ProportionalValue?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productId", productId)
    put("proportionalValue", proportionalValue?.let { gson.toJson(proportionalValue) })
              }
              val r = makeRequest("updateProportionalValueAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun activateAllProductsInPlace(placeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("activateAllProductsInPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deactivateAllProductsInPlace(placeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("deactivateAllProductsInPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun activateProductsInPlace(placeId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("activateProductsInPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deactivateProductsInPlace(placeId: String, productIds: ArrayList<String>): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
              }
              val r = makeRequest("deactivateProductsInPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun activateAllProductsInPlaceFromCategory(placeId: String, categoryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("categoryId", categoryId)
              }
              val r = makeRequest("activateAllProductsInPlaceFromCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deactivateAllProductsInPlaceFromCategory(placeId: String, categoryId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("categoryId", categoryId)
              }
              val r = makeRequest("deactivateAllProductsInPlaceFromCategory", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun addProductToPlace(placeId: String, productId: String, fiscalData: PlaceProductFiscalData?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productId", productId)
    put("fiscalData", fiscalData?.let { gson.toJson(fiscalData) })
              }
              val r = makeRequest("addProductToPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun removeProductFromPlace(placeId: String, productId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productId", productId)
              }
              val r = makeRequest("removeProductFromPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getProductsByPlace(place: String): Deferred<LiveData<Response<ArrayList<Product>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("place", place)
              }
              val r = makeRequest("getProductsByPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Product>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Product>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Product>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun addNewProductToOrganization(product: NewProduct, image: EditProductImage?): Deferred<LiveData<Response<Product?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("product", gson.toJson(product))
    put("image", image?.let { gson.toJson(image) })
              }
              val r = makeRequest("addNewProductToOrganization", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Product::class.java)
              } else null
             MutableLiveData<Response<Product?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun addNewProduct(product: NewPlaceProduct, placeId: String, image: EditProductImage?): Deferred<LiveData<Response<PlaceProduct?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("product", gson.toJson(product))
    put("placeId", placeId)
    put("image", image?.let { gson.toJson(image) })
              }
              val r = makeRequest("addNewProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), PlaceProduct::class.java)
              } else null
             MutableLiveData<Response<PlaceProduct?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editProduct(id: String, product: NewProduct): Deferred<LiveData<Response<Product?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("product", gson.toJson(product))
              }
              val r = makeRequest("editProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), Product::class.java)
              } else null
             MutableLiveData<Response<Product?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editProducts(products: ArrayList<EditedProduct>): Deferred<LiveData<Response<EditedResults?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("products", JSONArray().apply {
            products.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("editProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), EditedResults::class.java)
              } else null
             MutableLiveData<Response<EditedResults?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editPlaceProduct(id: String, product: NewPlaceProduct, placeId: String): Deferred<LiveData<Response<PlaceProduct?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("product", gson.toJson(product))
    put("placeId", placeId)
              }
              val r = makeRequest("editPlaceProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), PlaceProduct::class.java)
              } else null
             MutableLiveData<Response<PlaceProduct?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editProductImage(id: String, image: EditProductImage): Deferred<LiveData<Response<String??>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
    put("image", gson.toJson(image))
              }
              val r = makeRequest("editProductImage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String??>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteProduct(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteMultipleProducts(productIds: ArrayList<String>, placeId: String?): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("productIds", JSONArray().apply {
            productIds.forEach { item -> put(item) }
          })
    put("placeId", placeId?.let { placeId })
              }
              val r = makeRequest("deleteMultipleProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createSupply(name: String, placeId: String, image: ByteArray?): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("name", name)
    put("placeId", placeId)
    put("image", image?.let { Base64.encodeToString(image, Base64.DEFAULT) })
              }
              val r = makeRequest("createSupply", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSupplyProducts(placeId: String): Deferred<LiveData<Response<ArrayList<Supply>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getSupplyProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Supply>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Supply>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Supply>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAllProductCategories(placeId: String): Deferred<LiveData<Response<ArrayList<String>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getAllProductCategories", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<String>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<String>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<String>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlaceProduct(placeId: String, productId: String): Deferred<LiveData<Response<PlaceProduct?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productId", productId)
              }
              val r = makeRequest("getPlaceProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), PlaceProduct::class.java)
              } else null
             MutableLiveData<Response<PlaceProduct?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlaceProducts(placeId: String): Deferred<LiveData<Response<ArrayList<PlaceProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getPlaceProducts", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<PlaceProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PlaceProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<PlaceProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAllProducts(): Deferred<LiveData<Response<ArrayList<OrganizationProduct>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getAllProducts", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<OrganizationProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<OrganizationProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<OrganizationProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getProduct(id: String): Deferred<LiveData<Response<OrganizationProduct?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("getProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), OrganizationProduct::class.java)
              } else null
             MutableLiveData<Response<OrganizationProduct?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun inputProductAtStorage(placeId: String, input: InputProduct): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("input", gson.toJson(input))
              }
              val r = makeRequest("inputProductAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun attachPlaceProductToProductionRule(productId: String, placeId: String, productionRuleId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("productId", productId)
    put("placeId", placeId)
    put("productionRuleId", productionRuleId)
              }
              val r = makeRequest("attachPlaceProductToProductionRule", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun removePlaceProductProductionRule(productId: String, placeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("productId", productId)
    put("placeId", placeId)
              }
              val r = makeRequest("removePlaceProductProductionRule", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getProductionRuleFromProduct(productId: String, placeId: String): Deferred<LiveData<Response<ProductRule?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("productId", productId)
    put("placeId", placeId)
              }
              val r = makeRequest("getProductionRuleFromProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ProductRule::class.java)
              } else null
             MutableLiveData<Response<ProductRule?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getProductKinds(): Deferred<LiveData<Response<ArrayList<ProductKind>?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("getProductKinds", null)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ProductKind>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductKind>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ProductKind>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSuggestedImageFor(product: ProductImageSearch): Deferred<LiveData<Response<ArrayList<SuggestedImages>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("product", gson.toJson(product))
              }
              val r = makeRequest("getSuggestedImageFor", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<SuggestedImages>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<SuggestedImages>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<SuggestedImages>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun applySuggestedImageForProduct(imageId: String, productId: String, type: ApplySuggestedImageForProductType): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("imageId", imageId)
    put("productId", productId)
    put("type", type.name)
              }
              val r = makeRequest("applySuggestedImageForProduct", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getProductsFromPlaceReportXls(placeId: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getProductsFromPlaceReportXls", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlaceProductsByType(placeId: String, productType: String): Deferred<LiveData<Response<ArrayList<PlaceProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("productType", productType)
              }
              val r = makeRequest("getPlaceProductsByType", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<PlaceProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PlaceProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<PlaceProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createPromotion(promotion: NewPromotionInfo): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("promotion", gson.toJson(promotion))
              }
              val r = makeRequest("createPromotion", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createPromotionForPlace(promotion: NewPromotionInfo, placeId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("promotion", gson.toJson(promotion))
    put("placeId", placeId)
              }
              val r = makeRequest("createPromotionForPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deletePromotion(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deletePromotion", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAvailablePromotions(placeId: String): Deferred<LiveData<Response<ArrayList<PromotionInfo>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getAvailablePromotions", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<PromotionInfo>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PromotionInfo>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<PromotionInfo>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun givePromotion(promotionId: String, userId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("promotionId", promotionId)
    put("userId", userId)
              }
              val r = makeRequest("givePromotion", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun givePromotionToManyUsers(promotionId: String, cpfPhones: ArrayList<CpfOrPhone>): Deferred<LiveData<Response<MultipleUserPromotionResult?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("promotionId", promotionId)
    put("cpfPhones", JSONArray().apply {
            cpfPhones.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("givePromotionToManyUsers", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), MultipleUserPromotionResult::class.java)
              } else null
             MutableLiveData<Response<MultipleUserPromotionResult?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPromotionUsers(promotionId: String): Deferred<LiveData<Response<ArrayList<PromotionUser>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("promotionId", promotionId)
              }
              val r = makeRequest("getPromotionUsers", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<PromotionUser>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PromotionUser>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<PromotionUser>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun removeUserFromPromotion(promotionId: String, userId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("promotionId", promotionId)
    put("userId", userId)
              }
              val r = makeRequest("removeUserFromPromotion", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun editPromotion(promotionId: String, promotion: NewPromotionInfo): Deferred<LiveData<Response<PromotionInfo?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("promotionId", promotionId)
    put("promotion", gson.toJson(promotion))
              }
              val r = makeRequest("editPromotion", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), PromotionInfo::class.java)
              } else null
             MutableLiveData<Response<PromotionInfo?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAllRelationsFromEvent(eventId: String): Deferred<LiveData<Response<ArrayList<Relation>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getAllRelationsFromEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Relation>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Relation>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Relation>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAllRelationsFromPlace(placeId: String): Deferred<LiveData<Response<ArrayList<Relation>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              val r = makeRequest("getAllRelationsFromPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Relation>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Relation>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Relation>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteRelation(id: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              val r = makeRequest("deleteRelation", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createEventRelation(relation: Relation, eventId: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("relation", gson.toJson(relation))
    put("eventId", eventId)
              }
              val r = makeRequest("createEventRelation", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createPlaceRelation(relation: Relation, placeId: String): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("relation", gson.toJson(relation))
    put("placeId", placeId)
              }
              val r = makeRequest("createPlaceRelation", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getPlaceEmployeeReport(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<EmployeeReport?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getPlaceEmployeeReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), EmployeeReport::class.java)
              } else null
             MutableLiveData<Response<EmployeeReport?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getFinanceBiReport(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<FinanceBiReport?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getFinanceBiReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), FinanceBiReport::class.java)
              } else null
             MutableLiveData<Response<FinanceBiReport?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getClientsBiReport(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ClientByReport?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getClientsBiReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ClientByReport::class.java)
              } else null
             MutableLiveData<Response<ClientByReport?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getProductsBiReport(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ProductBiReport>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(since))
    put("until", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(until))
              }
              val r = makeRequest("getProductsBiReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ProductBiReport>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductBiReport>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ProductBiReport>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun createReserve(eventId: String, reserve: NewReserve): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("reserve", gson.toJson(reserve))
              }
              val r = makeRequest("createReserve", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun deleteReserve(reserveId: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("reserveId", reserveId)
              }
              val r = makeRequest("deleteReserve", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun updateReserve(reserveId: String, reserve: NewReserve): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("reserveId", reserveId)
    put("reserve", gson.toJson(reserve))
              }
              val r = makeRequest("updateReserve", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getReserves(eventId: String): Deferred<LiveData<Response<ArrayList<Reserve>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getReserves", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<Reserve>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Reserve>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<Reserve>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getReserveDetail(reserveId: String): Deferred<LiveData<Response<ReserveDetail?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("reserveId", reserveId)
              }
              val r = makeRequest("getReserveDetail", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), ReserveDetail::class.java)
              } else null
             MutableLiveData<Response<ReserveDetail?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getReservesReport(eventId: String): Deferred<LiveData<Response<ArrayList<ReserveReportRow>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getReservesReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ReserveReportRow>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ReserveReportRow>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ReserveReportRow>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getReserveDiscountDetails(reserveId: String): Deferred<LiveData<Response<ArrayList<ReserveDiscountDetail>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("reserveId", reserveId)
              }
              val r = makeRequest("getReserveDiscountDetails", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ReserveDiscountDetail>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ReserveDiscountDetail>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ReserveDiscountDetail>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getProductsAtStorage(placeId: String, storageId: String): Deferred<LiveData<Response<ArrayList<StorageProduct>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("storageId", storageId)
              }
              val r = makeRequest("getProductsAtStorage", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<StorageProduct>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<StorageProduct>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<StorageProduct>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEntrancesSoldAtEventInPeriod(eventId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<EntranceSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getEntrancesSoldAtEventInPeriod", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<EntranceSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<EntranceSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<EntranceSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getProductsSoldAtEventInPeriod(eventId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ProductSold>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getProductsSoldAtEventInPeriod", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ProductSold>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductSold>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ProductSold>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getUser(placeId: String, cpf: String?, phone: String?): Deferred<LiveData<Response<User?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("cpf", cpf?.let { cpf })
    put("phone", phone?.let { phone })
              }
              val r = makeRequest("getUser", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), User::class.java)
              } else null
             MutableLiveData<Response<User?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getUserAtEvent(eventId: String, filter: UserFilter): Deferred<LiveData<Response<User?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("filter", gson.toJson(filter))
              }
              val r = makeRequest("getUserAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), User::class.java)
              } else null
             MutableLiveData<Response<User?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getUserDetailsAtEvent(eventId: String, userId: String): Deferred<LiveData<Response<UserDetails?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("userId", userId)
              }
              val r = makeRequest("getUserDetailsAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), UserDetails::class.java)
              } else null
             MutableLiveData<Response<UserDetails?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getUserDetailsAtPlace(placeId: String, userId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<UserDetails?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("userId", userId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getUserDetailsAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), UserDetails::class.java)
              } else null
             MutableLiveData<Response<UserDetails?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getUserRechargesInEvent(userId: String, eventId: String): Deferred<LiveData<Response<ArrayList<UserRecharge>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("userId", userId)
    put("eventId", eventId)
              }
              val r = makeRequest("getUserRechargesInEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<UserRecharge>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UserRecharge>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<UserRecharge>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun giveRechargeToUsers(eventId: String, recharges: ArrayList<NewUserRecharge>): Deferred<LiveData<Response<ArrayList<GiveRechargeError>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("recharges", JSONArray().apply {
            recharges.forEach { item -> put(gson.toJson(item)) }
          })
              }
              val r = makeRequest("giveRechargeToUsers", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<GiveRechargeError>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<GiveRechargeError>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<GiveRechargeError>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getAllUsersAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<EventUser>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getAllUsersAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<EventUser>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<EventUser>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<EventUser>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getEventUserAtEvent(eventId: String, userId: String): Deferred<LiveData<Response<EventUser?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("userId", userId)
              }
              val r = makeRequest("getEventUserAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), EventUser::class.java)
              } else null
             MutableLiveData<Response<EventUser?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getClientsAtPlaceWithFilter(placeId: String, filters: SearchFilters, offset: Int): Deferred<LiveData<Response<ArrayList<SearchResult>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("filters", gson.toJson(filters))
    put("offset", offset)
              }
              val r = makeRequest("getClientsAtPlaceWithFilter", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<SearchResult>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<SearchResult>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<SearchResult>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getClientsAtPlaceWithFilterXls(placeId: String, filters: SearchFilters): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("filters", gson.toJson(filters))
              }
              val r = makeRequest("getClientsAtPlaceWithFilterXls", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getUserByCpf(placeId: String, cpf: String): Deferred<LiveData<Response<SearchResult??>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("cpf", cpf)
              }
              val r = makeRequest("getUserByCpf", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson(r.data?.getJSONObject("result")?.toString(), SearchResult::class.java)
              } else null
             MutableLiveData<Response<SearchResult??>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getUserDetailsAtEventsAndPlace(placeId: String, userId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<UserDetailsByEvents>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("userId", userId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getUserDetailsAtEventsAndPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<UserDetailsByEvents>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UserDetailsByEvents>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<UserDetailsByEvents>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getSuggestedImages(keyword: String): Deferred<LiveData<Response<ArrayList<String>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("keyword", keyword)
              }
              val r = makeRequest("getSuggestedImages", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<String>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<String>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<String>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getZigTagBlockConfirmsAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<ZigTagBlockConfirm>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getZigTagBlockConfirmsAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ZigTagBlockConfirm>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ZigTagBlockConfirm>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ZigTagBlockConfirm>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getZigTagBlockConfirmsAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ZigTagBlockConfirm>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getZigTagBlockConfirmsAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ZigTagBlockConfirm>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ZigTagBlockConfirm>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ZigTagBlockConfirm>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getZigTagSyncForcesAtEvent(eventId: String): Deferred<LiveData<Response<ArrayList<ZigTagSyncForce>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getZigTagSyncForcesAtEvent", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ZigTagSyncForce>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ZigTagSyncForce>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ZigTagSyncForce>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getZigTagSyncForcesAtPlace(placeId: String, since: Calendar, until: Calendar): Deferred<LiveData<Response<ArrayList<ZigTagSyncForce>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
    put("until", dateTimeFormat.format(until))
              }
              val r = makeRequest("getZigTagSyncForcesAtPlace", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<ZigTagSyncForce>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ZigTagSyncForce>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<ZigTagSyncForce>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getNotOpenedCashiers(eventId: String): Deferred<LiveData<Response<ArrayList<EmployeeBase>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getNotOpenedCashiers", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<EmployeeBase>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<EmployeeBase>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<EmployeeBase>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getStockTransferReport(eventId: String): Deferred<LiveData<Response<ArrayList<StockTransfer>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getStockTransferReport", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<StockTransfer>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<StockTransfer>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<StockTransfer>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getTransactionHistory(placeId: String, userId: String): Deferred<LiveData<Response<ArrayList<TimelineEvent>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("userId", userId)
              }
              val r = makeRequest("getTransactionHistory", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<TimelineEvent>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<TimelineEvent>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<TimelineEvent>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getGuinnessResume(eventId: String): Deferred<LiveData<Response<Int?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getGuinnessResume", bodyArgs)
              val responseData = if (r.data != null) {
                    r.data?.getInt("result")
              } else null
             MutableLiveData<Response<Int?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun getGuinnessLog(eventId: String): Deferred<LiveData<Response<ArrayList<GuinnessLog>?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              val r = makeRequest("getGuinnessLog", bodyArgs)
              val responseData = if (r.data != null) {
                    gson.fromJson<ArrayList<GuinnessLog>>(r.data?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<GuinnessLog>>() { }.type) 
              } else null
             MutableLiveData<Response<ArrayList<GuinnessLog>?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun ping(): Deferred<LiveData<Response<String?>>> = CoroutineScope(IO).async { 
              val r = makeRequest("ping", null)
              val responseData = if (r.data != null) {
                    r.data?.getString("result")
              } else null
             MutableLiveData<Response<String?>>().apply { postValue(Response(r.error, responseData)) }
         }
         override fun setPushToken(token: String): Deferred<LiveData<Response<Boolean?>>> = CoroutineScope(IO).async { 
              val bodyArgs = JSONObject().apply {
                  put("token", token)
              }
              val r = makeRequest("setPushToken", bodyArgs)
              val responseData = if (r.data != null) {
                    r.error == null
              } else null
             MutableLiveData<Response<Boolean?>>().apply { postValue(Response(r.error, responseData)) }
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

        private suspend fun makeRequest(functionName: String, bodyArgs: JSONObject?, timeoutSeconds: Int = 15): InternalResponse = suspendCoroutine { continuation ->
            try {
                val body = JSONObject().apply {
                    put("id", randomBytesHex(8))
                    put("device", device())
                    put("name", functionName)
                    put("args", bodyArgs ?: JSONObject())
                    put("staging", API.useStaging)
                }

                val request = Request.Builder()
                        .url("https://$BASE_URL${if (useStaging) "-staging" else ""}/$functionName")
                        .post(body.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()))
                        .build()
                 client.newCall(request).enqueue(object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                        continuation.resume(InternalResponse(Error(ErrorType.Fatal, e?.message ?: "Chamada falhou sem mensagem de erro!"), null))
                    }

                    override fun onResponse(call: Call, response: okhttp3.Response) {
                        if (response.code == 502) {
                            continuation.resume(InternalResponse(Error(ErrorType.Fatal, "Erro Fatal (502) - Tente novamente"), null))
                            return
                        }

                        val responseBody = try {
                            val stringBody = response?.body?.string()
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
            } catch (e: JSONException) {
                e.printStackTrace()
                continuation.resume(InternalResponse(Error(ErrorType.Fatal, e.message ?: "Erro ao parsear json"), null))
            }
        }
    }