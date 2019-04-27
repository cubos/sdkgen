
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
import com.google.gson.reflect.TypeToken

@SuppressLint("SimpleDateFormat")
open class API {
    
    interface Calls {
       fun sendOfflineActions(list: ArrayList<SignedOfflineAction>, flag: Int? = null, callback: (error: Error?, OfflineActionError: ArrayList<OfflineActionError>?) -> Unit) 
       fun getPpuAuthorization(placeId: String, cpf: String, minval: Int, flag: Int? = null, callback: (error: Error?, pPUAuthorization: PPUAuthorization?) -> Unit) 
       fun getAppPostPayment(placeId: String, cpf: String, billValue: Int, flag: Int? = null, callback: (error: Error?, pPUAuthorization: PPUAuthorization?) -> Unit) 
       fun getOfflinePrefixId(flag: Int? = null, callback: (error: Error?, value: String?) -> Unit) 
       fun makeCardTransactionOffline(placeId: String, payment: ZigPayment, flag: Int? = null, callback: (error: Error?, zigMachineAnswer: ZigMachineAnswer?) -> Unit) 
       fun getAvailableEntrancesOffline(placeId: String, userCpf: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getOfflineUserStateByUid(zigTagUid: String, placeId: String, flag: Int? = null, callback: (error: Error?, offlineUserState: OfflineUserState??) -> Unit) 
       fun getOfflineUserState(zigTagUid: String, updatedAt: Calendar?, ppuUpdatedAt: Calendar?, cpf: String?, placeId: String, minPpuValue: Int?, flag: Int? = null, callback: (error: Error?, offlineUserState: OfflineUserState??) -> Unit) 
       fun getOfflineCurrentEvent(placeId: String, flag: Int? = null, callback: (error: Error?, mqttEventInfo: MqttEventInfo?) -> Unit) 
       fun getCurrentEventDontCreateNew(placeId: String, flag: Int? = null, callback: (error: Error?, mqttEventInfo: MqttEventInfo??) -> Unit) 
       fun offlineRefundTransaction(request: RefundTransactionRequest, flag: Int? = null, callback: (error: Error?, refundTransactionResult: RefundTransactionResult?) -> Unit) 
       fun offlineRefundTransactionMultipleUsers(request: RefundTransactionRequest, flag: Int? = null, callback: (error: Error?, RefundUser: ArrayList<RefundUser>?) -> Unit) 
       fun offlineUndoRefundTransaction(result: RefundTransactionResult, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun tryToUsePromotion(placeId: String, zigTagUid: String, cpf: String, products: ArrayList<SellingProduct>, userCredit: Int, flag: Int? = null, callback: (error: Error?, UsedPromotion: ArrayList<UsedPromotion>?) -> Unit) 
       fun undoUsedPromotion(promotionId: String, productId: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun authorizeCreditForUsers(placeId: String, users: ArrayList<CreditAsker>, flag: Int? = null, callback: (error: Error?, UserWithPpu: ArrayList<UserWithPpu>?) -> Unit) 
       fun sendCsv(csv: ByteArray, email: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun removeTipFromPrePaid(placeId: String, cpf: String, flag: Int? = null, callback: (error: Error?, refundTip: RefundTip?) -> Unit) 
       fun removeTipFromPrePaidAlternative(placeId: String, cpf: String, flag: Int? = null, callback: (error: Error?, refundTip: RefundTip?) -> Unit) 
       fun getOldEventsBill(placeId: String, cpf: String, flag: Int? = null, callback: (error: Error?, oldEventBill: OldEventBill?) -> Unit) 
       fun tryToStartRefundPrePaidTransaction(request: RefundTransactionRequest, flag: Int? = null, callback: (error: Error?, RefundUser: ArrayList<RefundUser>?) -> Unit) 
       fun finishActivityAtEvent(eventId: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun checkIn(user: String, place: String, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun checkOut(user: String, place: String, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun checkZigIn(authorization: String, place: String, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun checkZigOut(authorization: String, place: String, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun checkZigOutAndDetachZig(authorization: String, place: String, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun sellEntrancesWithZigMachine(placeId: String, userId: String, entranceIds: ArrayList<String>, payments: ArrayList<PostPayment>, flag: Int? = null, callback: (error: Error?, userState: UserState?) -> Unit) 
       fun sellEntrancesWithStoneZigMachine(placeId: String, userId: String, entranceIds: ArrayList<String>, payments: ArrayList<StonePostPayment>, flag: Int? = null, callback: (error: Error?, userState: UserState?) -> Unit) 
       fun getAvaiableEntrances(placeId: String, flag: Int? = null, callback: (error: Error?, Entrance: ArrayList<Entrance>?) -> Unit) 
       fun sellEntrances(placeId: String, userId: String, entranceIds: ArrayList<String>, payments: ArrayList<Payment>, flag: Int? = null, callback: (error: Error?, userState: UserState?) -> Unit) 
       fun checkIfEntrancePaid(placeId: String, authorization: String, flag: Int? = null, callback: (error: Error?, entranceList: EntranceList?) -> Unit) 
       fun issueInvoicesForUser(eventId: String, userCpf: String, cpf: String?, cnpj: String?, flag: Int? = null, callback: (error: Error?, issueResult: IssueResult?) -> Unit) 
       fun getInvoicesFromUser(eventId: String, userCpf: String, flag: Int? = null, callback: (error: Error?, Invoice: ArrayList<Invoice>?) -> Unit) 
       fun printInvoice(invoiceId: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun hasPrinter(invoiceId: String, flag: Int? = null, callback: (error: Error?, boolean: Boolean?) -> Unit) 
       fun logIn(username: String, password: String, flag: Int? = null, callback: (error: Error?, employee: Employee?) -> Unit) 
       fun logOut(flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getCurrentEmployee(flag: Int? = null, callback: (error: Error?, employee: Employee?) -> Unit) 
       fun logAcquirerError(acquirer: String, date: Calendar, placeId: String, eventId: String, cpf: String, error: String, transactionJson: String?, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun rechargeZig(authorization: String, recharge: Recharge, place: String, paymentMethod: PostMethod, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun rechargeZigWithReason(authorization: String, recharge: Recharge, place: String, paymentMethod: PostMethod, reason: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun rechargeUser(userId: String, zigId: String, recharge: Recharge, place: String, paymentMethod: PostMethod, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun rechargeWithMPos(placeId: String, authorization: String, payment: ZigPayment, flag: Int? = null, callback: (error: Error?, zigMachineAnswer: ZigMachineAnswer?) -> Unit) 
       fun rechargeWithStoneMpos(placeId: String, authorization: String, stoneTransactionJson: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getRechargeOfflineBackup(placeId: String, flag: Int? = null, callback: (error: Error?, RechargeBackup: ArrayList<RechargeBackup>?) -> Unit) 
       fun getPlaceRelations(placeId: String, flag: Int? = null, callback: (error: Error?, PlaceRelation: ArrayList<PlaceRelation>?) -> Unit) 
       fun getMyBarReport(flag: Int? = null, callback: (error: Error?, barProductReport: BarProductReport?) -> Unit) 
       fun getMyBarSellerReport(flag: Int? = null, callback: (error: Error?, barSellerReport: BarSellerReport?) -> Unit) 
       fun sell(products: ArrayList<SellingProduct>, tip: Int, placeId: String, buyers: ArrayList<Buyer>, obs: String?, flag: Int? = null, callback: (error: Error?, sellResult: SellResult?) -> Unit) 
       fun groupedSell(products: ArrayList<SellingProduct>, tip: Int, placeId: String, buyers: ArrayList<Buyer>, obs: String?, flag: Int? = null, callback: (error: Error?, sellResult: SellResult?) -> Unit) 
       fun finishGroupedSell(transactionIds: ArrayList<String>, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun finishGroupedSellBec(transactionIds: ArrayList<String>, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getProducts(place: String, flag: Int? = null, callback: (error: Error?, Product: ArrayList<Product>?) -> Unit) 
       fun getCombos(place: String, flag: Int? = null, callback: (error: Error?, Combo: ArrayList<Combo>?) -> Unit) 
       fun getSellableThings(place: String, flag: Int? = null, callback: (error: Error?, sellableThings: SellableThings?) -> Unit) 
       fun getSellableThings2(placeId: String, flag: Int? = null, callback: (error: Error?, sellableThings2: SellableThings2?) -> Unit) 
       fun getAllProducts(placeId: String, flag: Int? = null, callback: (error: Error?, CompleteProduct: ArrayList<CompleteProduct>?) -> Unit) 
       fun removeStock(placeId: String, authorization: String, deliverId: String, productId: String, quantity: Int, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun addStock(placeId: String, authorization: String, receiverId: String, productId: String, quantity: Int, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getStockTransfers(placeId: String, flag: Int? = null, callback: (error: Error?, ProductTransfer: ArrayList<ProductTransfer>?) -> Unit) 
       fun getSoldTickets(placeId: String, cpf: String, name: String, flag: Int? = null, callback: (error: Error?, UserTicket: ArrayList<UserTicket>?) -> Unit) 
       fun getSellableTickets(placeId: String, flag: Int? = null, callback: (error: Error?, Ticket: ArrayList<Ticket>?) -> Unit) 
       fun getTickets(placeId: String, authorization: String, flag: Int? = null, callback: (error: Error?, Ticket: ArrayList<Ticket>?) -> Unit) 
       fun getLastTransactions(limit: Int, place: String, flag: Int? = null, callback: (error: Error?, SellerTransaction: ArrayList<SellerTransaction>?) -> Unit) 
       fun getCashierClosing(placeId: String, flag: Int? = null, callback: (error: Error?, cashierClosing: CashierClosing?) -> Unit) 
       fun getReserves(placeId: String, flag: Int? = null, callback: (error: Error?, Reserve: ArrayList<Reserve>?) -> Unit) 
       fun addObsToReserve(reserveId: String, obs: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getReservesAndPromotions(placeId: String, flag: Int? = null, callback: (error: Error?, reserveAndPromotion: ReserveAndPromotion?) -> Unit) 
       fun getDeliveryProducts(type: String, uid: String, barId: String, placeId: String, flag: Int? = null, callback: (error: Error?, DeliveryTransaction: ArrayList<DeliveryTransaction>?) -> Unit) 
       fun deliveryProducts(deliveryInstances: ArrayList<DeliveryInstance>, placeId: String, barId: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun deliverProducts(instanceIds: ArrayList<String>, placeId: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getDeliveryBarProducts(placeId: String, flag: Int? = null, callback: (error: Error?, DeliveryTransaction: ArrayList<DeliveryTransaction>?) -> Unit) 
       fun getMyDeliveries(placeId: String, flag: Int? = null, callback: (error: Error?, DeliveryTransaction: ArrayList<DeliveryTransaction>?) -> Unit) 
       fun removeFromDelivery(transactionId: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getProductsToBeDelivered(placeId: String, flag: Int? = null, callback: (error: Error?, ProductsToDelivery: ArrayList<ProductsToDelivery>?) -> Unit) 
       fun getTransactionPrintout(transactionId: String, flag: Int? = null, callback: (error: Error?, value: ByteArray?) -> Unit) 
       fun getBarTransactionPrintout(transactionId: String, mandatory: Boolean, flag: Int? = null, callback: (error: Error?, value: ByteArray?) -> Unit) 
       fun alertUserThatProductIsReady(transactionId: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun deliverProductsWithPush(deliveryInstances: ArrayList<DeliveryInstance>, placeId: String, barId: String, sendPush: Boolean, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getPostPaidOwnedValue(authorization: String, placeId: String, flag: Int? = null, callback: (error: Error?, postPaidOwnedValue: PostPaidOwnedValue?) -> Unit) 
       fun payMultipleUserPostPaidTransactionsWithTipAndZigMachine(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payments: ArrayList<PostPayment>, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun payMultipleUserPostPaidPartialTransactions(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payments: ArrayList<PostPayment>, flag: Int? = null, callback: (error: Error?, partialPayReturn: PartialPayReturn?) -> Unit) 
       fun makeCardTransactionAndPayPartialTransactions(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payment: ZigPayment, flag: Int? = null, callback: (error: Error?, zigMachinePayment: ZigMachinePayment?) -> Unit) 
       fun payMultipleUserPostPaidPartialTransactionsStone(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payments: ArrayList<StonePostPayment>, flag: Int? = null, callback: (error: Error?, partialPayReturn: PartialPayReturn?) -> Unit) 
       fun getUserTransactions(authorization: String, place: String, flag: Int? = null, callback: (error: Error?, Transaction: ArrayList<Transaction>?) -> Unit) 
       fun getUserPostPaidPayments(authorization: String, place: String, flag: Int? = null, callback: (error: Error?, PostPaidTransaction: ArrayList<PostPaidTransaction>?) -> Unit) 
       fun payMultipleUserPostPaidTransactions(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payments: ArrayList<Payment>, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun payMultipleUserPostPaidTransactionsWithTip(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payments: ArrayList<Payment>, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getOpenedBillsBackup(placeId: String, flag: Int? = null, callback: (error: Error?, OpenedBillBackup: ArrayList<OpenedBillBackup>?) -> Unit) 
       fun refundTransaction(authorization: String, transaction: String, place: String, obs: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun refundTransactionWithLogin(login: String, password: String, transactionId: String, placeId: String, obs: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun refundTransactionProduct(authorization: String, transactionId: String, placeId: String, productId: String, count: Int, obs: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun cancelTransaction(authorization: String, transaction: String, place: String, obs: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun cancelTransactionProduct(authorization: String, transactionId: String, placeId: String, productId: String, count: Int, obs: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun refundRecharge(authorization: String, rechargeId: String, obs: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun consumeRecharge(placeId: String, authorization: String, rechargeId: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun refundMachineTransaction(machineTransactionId: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getProductsForRefundActivation(placeId: String, flag: Int? = null, callback: (error: Error?, RefundActivationProduct: ArrayList<RefundActivationProduct>?) -> Unit) 
       fun createUser(setup: UserSetup, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun createUserWithAvatar(setup: UserSetup, avatar: ByteArray?, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun getUser(cpf: String, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun getUserWithCpfData(cpf: String, flag: Int? = null, callback: (error: Error?, cpfDataOrUser: CpfDataOrUser?) -> Unit) 
       fun getUserWithForeign(cpf: String, foreign: Boolean, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun getClientZigLine(authorization: String, placeId: String, flag: Int? = null, callback: (error: Error?, Action: ArrayList<Action>?) -> Unit) 
       fun getClientZigLineByCpf(cpf: String, placeId: String, flag: Int? = null, callback: (error: Error?, Action: ArrayList<Action>?) -> Unit) 
       fun getClientZigLineByCpfFromDate(cpf: String, placeId: String, from: Calendar, flag: Int? = null, callback: (error: Error?, Action: ArrayList<Action>?) -> Unit) 
       fun getClientHistoryInPlace(authorization: String, placeId: String, flag: Int? = null, callback: (error: Error?, HistoryAction: ArrayList<HistoryAction>?) -> Unit) 
       fun getClientHistoryInPlaceByCpf(cpf: String, placeId: String, flag: Int? = null, callback: (error: Error?, HistoryAction: ArrayList<HistoryAction>?) -> Unit) 
       fun getClientHistoryInPlaceByCpfFromDate(cpf: String, placeId: String, from: Calendar, flag: Int? = null, callback: (error: Error?, HistoryAction: ArrayList<HistoryAction>?) -> Unit) 
       fun getAge(cpf: String, placeId: String, flag: Int? = null, callback: (error: Error?, datetime: Calendar??) -> Unit) 
       fun increaseLimit(cpf: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun userState(cpf: String, place: String, flag: Int? = null, callback: (error: Error?, userState: UserState?) -> Unit) 
       fun getAllUpdatedUsers(placeId: String, since: Calendar, flag: Int? = null, callback: (error: Error?, String: ArrayList<String>?) -> Unit) 
       fun makeCardTransaction(placeId: String, authorization: String, payment: ZigPayment, flag: Int? = null, callback: (error: Error?, zigMachineAnswer: ZigMachineAnswer?) -> Unit) 
       fun cancelStoneTransaction(id: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun sendAllNotUsedMachineTransactions(ids: ArrayList<String>, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun startCipurseRead(uid: String, keyRandom: String, challengeRandom: String, secret: String, flag: Int? = null, callback: (error: Error?, cipurseChallenge: CipurseChallenge?) -> Unit) 
       fun startDesfireRead(uid: String, encryptedRandom: String, secret: String, flag: Int? = null, callback: (error: Error?, desfireChallenge: DesfireChallenge?) -> Unit) 
       fun getZig(authorization: String, place: String, flag: Int? = null, callback: (error: Error?, zigDevice: ZigDevice?) -> Unit) 
       fun blockZig(cpf: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun detachZig(authorization: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun setupZig(user: String, zigId: String, place: String, flag: Int? = null, callback: (error: Error?, zigDevice: ZigDevice?) -> Unit) 
       fun setupZigAuth(user: String, authorization: String, place: String, flag: Int? = null, callback: (error: Error?, zigDevice: ZigDevice?) -> Unit) 
       fun setupZigAuthAndRelation(user: String, authorization: String, place: String, flag: Int? = null, callback: (error: Error?, zigAndRelation: ZigAndRelation?) -> Unit) 
       fun getBlockedZigUids(placeId: String, flag: Int? = null, callback: (error: Error?, String: ArrayList<String>?) -> Unit) 
       fun enrollFace(cpf: String, _data: ByteArray, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun startFaceBiometry(placeId: String, _data: ByteArray, flag: Int? = null, callback: (error: Error?, user: User?) -> Unit) 
       fun sleep(ms: Int, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun getKey(uid: String, secret: String, flag: Int? = null, callback: (error: Error?, value: String?) -> Unit) 
       fun donate(cpf: String, email: String?, stoneJson: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
       fun ping(flag: Int? = null, callback: (error: Error?, value: String?) -> Unit) 
       fun setPushToken(token: String, flag: Int? = null, callback: (error: Error?, result: Boolean?) -> Unit) 
    }

	companion object {
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
      
      const val BASE_URL = "api.zigcore.com.br/pdv"
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

    data class ImageUrl(
        var url: String, 
        var width: Int, 
        var height: Int 
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

    data class SellingProduct(
        var id: String, 
        var quantity: Int, 
        var value: Int, 
        var type: SellingProductType, 
        var name: String?, 
        var image: ImageUrl?, 
        var obs: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellingProduct {
                return gson.fromJson(jsonToParse.toString(), SellingProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellingProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellingProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineAction(
        var id: String, 
        var type: OfflineActionType, 
        var deviceId: String?, 
        var date: Calendar, 
        var employeeId: String, 
        var eventId: String, 
        var zigDeviceUid: String, 
        var createUser: OfflineCreateUserAction?, 
        var recharge: OfflineRechargeAction?, 
        var transaction: OfflineTransactionAction?, 
        var attach: OfflineAttachAction?, 
        var detach: OfflineDetachAction?, 
        var refundRecharge: OfflineRefundRecharge?, 
        var consumeRecharge: OfflineConsumeRecharge?, 
        var entranceAction: OfflineEntranceAction?, 
        var detachAndRefundActivation: OfflineDetachAndRefundActivation?, 
        var postPayment: OfflinePostPayment?, 
        var checkout: OfflineCheckoutAction?, 
        var refundNotPaidTransactionAction: RefundTransactionRequest?, 
        var refundMultipleNotPaidTransactionsAction: RefundMultipleTransactionsRequest?, 
        var groupedSell: OfflineGroupedSell?, 
        var payOldEventsBill: OldEventBillPaymentAction?, 
        var confirmRefundPrePaidTransaction: ConfirmRefundPrePaidTransactionAction?, 
        var discount: OfflineDiscountAction?, 
        var attachToReserve: OfflineAttachToReserveAction?, 
        var completeRefund: OfflineCompleteRefundAction?, 
        var transferUnpaidTransactions: OfflineTransferUnpaidTransactionsAction?, 
        var sellWithoutCard: OfflineSellWithoutCardAction?, 
        var tipRemoval: OfflineTipRemovalAction?, 
        var attachPromotion: OfflineAttachPromotion? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineAction {
                return gson.fromJson(jsonToParse.toString(), OfflineAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineAttachPromotion(
        var cpf: String, 
        var promotionInfoId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineAttachPromotion {
                return gson.fromJson(jsonToParse.toString(), OfflineAttachPromotion::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineAttachPromotion> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineAttachPromotion>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineTipRemovalAction(
        var cpf: String, 
        var reason: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineTipRemovalAction {
                return gson.fromJson(jsonToParse.toString(), OfflineTipRemovalAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineTipRemovalAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineTipRemovalAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineSellWithoutCardAction(
        var id: String, 
        var type: OfflineSellWithoutCardActionType, 
        var buyers: ArrayList<OfflineSellWithoutCardActionBuyers>, 
        var tip: Int, 
        var products: ArrayList<OfflineSellWithoutCardActionProducts>, 
        var combos: ArrayList<OfflineSellWithoutCardActionCombos>, 
        var obs: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineSellWithoutCardAction {
                return gson.fromJson(jsonToParse.toString(), OfflineSellWithoutCardAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineSellWithoutCardAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineSellWithoutCardAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineSellWithoutCardActionBuyers(
        var cpf: String, 
        var name: String?, 
        var payments: ArrayList<OfflineSellWithoutCardActionBuyersPayments>, 
        var rappiObject: OfflineSellWithoutCardActionBuyersRappiObject? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineSellWithoutCardActionBuyers {
                return gson.fromJson(jsonToParse.toString(), OfflineSellWithoutCardActionBuyers::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineSellWithoutCardActionBuyers> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineSellWithoutCardActionBuyers>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineSellWithoutCardActionBuyersPayments(
        var id: String?, 
        var cpf: String, 
        var isRelativeToTip: Boolean?, 
        var method: PaymentMethod, 
        var stoneTransactionJson: String?, 
        var value: Int, 
        var isBonus: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineSellWithoutCardActionBuyersPayments {
                return gson.fromJson(jsonToParse.toString(), OfflineSellWithoutCardActionBuyersPayments::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineSellWithoutCardActionBuyersPayments> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineSellWithoutCardActionBuyersPayments>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineSellWithoutCardActionBuyersRappiObject(
        var paymentId: String, 
        var referenceId: String, 
        var amount: Int, 
        var currencyCode: String, 
        var storeId: String, 
        var message: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineSellWithoutCardActionBuyersRappiObject {
                return gson.fromJson(jsonToParse.toString(), OfflineSellWithoutCardActionBuyersRappiObject::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineSellWithoutCardActionBuyersRappiObject> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineSellWithoutCardActionBuyersRappiObject>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineSellWithoutCardActionProducts(
        var id: String, 
        var relativeId: Int?, 
        var count: Int, 
        var value: Int, 
        var discountValue: Int, 
        var promotionId: String?, 
        var offlinePromotionId: String?, 
        var offlinePromotionUserCpf: String?, 
        var obs: String, 
        var bar: String?, 
        var deliveredAt: Calendar?, 
        var deliveredBy: String?, 
        var name: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineSellWithoutCardActionProducts {
                return gson.fromJson(jsonToParse.toString(), OfflineSellWithoutCardActionProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineSellWithoutCardActionProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineSellWithoutCardActionProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineSellWithoutCardActionCombos(
        var id: String, 
        var relativeId: Int?, 
        var count: Int, 
        var value: Int, 
        var promotionId: String?, 
        var offlinePromotionId: String?, 
        var offlinePromotionUserCpf: String?, 
        var discountValue: Int, 
        var obs: String, 
        var bar: String?, 
        var deliveredAt: Calendar?, 
        var deliveredBy: String?, 
        var name: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineSellWithoutCardActionCombos {
                return gson.fromJson(jsonToParse.toString(), OfflineSellWithoutCardActionCombos::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineSellWithoutCardActionCombos> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineSellWithoutCardActionCombos>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineTransferUnpaidTransactionsAction(
        var date: Calendar, 
        var fromCpf: String, 
        var toCpf: String, 
        var transactionIds: ArrayList<String>, 
        var totalValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineTransferUnpaidTransactionsAction {
                return gson.fromJson(jsonToParse.toString(), OfflineTransferUnpaidTransactionsAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineTransferUnpaidTransactionsAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineTransferUnpaidTransactionsAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineCompleteRefundAction(
        var transactionId: String, 
        var products: ArrayList<OfflineCompleteRefundActionProducts>, 
        var combos: ArrayList<OfflineCompleteRefundActionCombos>, 
        var reason: String, 
        var cpf: String, 
        var refundAll: Boolean, 
        var shouldReturnToStock: Boolean, 
        var newTransactionId: String?, 
        var fakeRecharge: OfflineCompleteRefundActionFakeRecharge 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineCompleteRefundAction {
                return gson.fromJson(jsonToParse.toString(), OfflineCompleteRefundAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineCompleteRefundAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineCompleteRefundAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineCompleteRefundActionProducts(
        var id: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineCompleteRefundActionProducts {
                return gson.fromJson(jsonToParse.toString(), OfflineCompleteRefundActionProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineCompleteRefundActionProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineCompleteRefundActionProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineCompleteRefundActionCombos(
        var id: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineCompleteRefundActionCombos {
                return gson.fromJson(jsonToParse.toString(), OfflineCompleteRefundActionCombos::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineCompleteRefundActionCombos> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineCompleteRefundActionCombos>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineCompleteRefundActionFakeRecharge(
        var id: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineCompleteRefundActionFakeRecharge {
                return gson.fromJson(jsonToParse.toString(), OfflineCompleteRefundActionFakeRecharge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineCompleteRefundActionFakeRecharge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineCompleteRefundActionFakeRecharge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineAttachToReserveAction(
        var cpf: String, 
        var reserveId: String, 
        var minimumConsumptionValue: Int, 
        var rechargeId: String?, 
        var promotionIds: ArrayList<String> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineAttachToReserveAction {
                return gson.fromJson(jsonToParse.toString(), OfflineAttachToReserveAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineAttachToReserveAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineAttachToReserveAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineDiscountAction(
        var cpf: String, 
        var transactions: ArrayList<OfflineDiscountActionTransactions> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineDiscountAction {
                return gson.fromJson(jsonToParse.toString(), OfflineDiscountAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineDiscountAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineDiscountAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineDiscountActionTransactions(
        var id: String, 
        var discountValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineDiscountActionTransactions {
                return gson.fromJson(jsonToParse.toString(), OfflineDiscountActionTransactions::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineDiscountActionTransactions> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineDiscountActionTransactions>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ConfirmRefundPrePaidTransactionAction(
        var newTransactionId: String?, 
        var refundRequest: RefundTransactionRequest 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ConfirmRefundPrePaidTransactionAction {
                return gson.fromJson(jsonToParse.toString(), ConfirmRefundPrePaidTransactionAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ConfirmRefundPrePaidTransactionAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ConfirmRefundPrePaidTransactionAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OldEventBillPaymentAction(
        var cpf: String, 
        var method: PaymentMethod, 
        var reason: String?, 
        var stoneTransactionJson: String?, 
        var value: Int, 
        var isBonus: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OldEventBillPaymentAction {
                return gson.fromJson(jsonToParse.toString(), OldEventBillPaymentAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OldEventBillPaymentAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OldEventBillPaymentAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineGroupedSell(
        var transactions: ArrayList<OfflineTransactionAction>, 
        var groupedId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineGroupedSell {
                return gson.fromJson(jsonToParse.toString(), OfflineGroupedSell::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineGroupedSell> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineGroupedSell>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineCheckoutAction(
        var cpf: String, 
        var detach: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineCheckoutAction {
                return gson.fromJson(jsonToParse.toString(), OfflineCheckoutAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineCheckoutAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineCheckoutAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflinePostPayment(
        var cpf: String, 
        var transactions: ArrayList<OfflinePostPaymentTransactions>, 
        var entrances: ArrayList<OfflinePostPaymentEntrances>, 
        var postPaymentId: String, 
        var method: OfflinePostPaymentMethod, 
        var stoneTransactionJson: String?, 
        var rechargeId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflinePostPayment {
                return gson.fromJson(jsonToParse.toString(), OfflinePostPayment::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflinePostPayment> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflinePostPayment>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflinePostPaymentTransactions(
        var tipValue: Int, 
        var value: Int, 
        var id: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflinePostPaymentTransactions {
                return gson.fromJson(jsonToParse.toString(), OfflinePostPaymentTransactions::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflinePostPaymentTransactions> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflinePostPaymentTransactions>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflinePostPaymentEntrances(
        var count: Int, 
        var paidValue: Int, 
        var tipValue: Int, 
        var relativeId: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflinePostPaymentEntrances {
                return gson.fromJson(jsonToParse.toString(), OfflinePostPaymentEntrances::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflinePostPaymentEntrances> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflinePostPaymentEntrances>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineDetachAndRefundActivation(
        var type: OfflineDetachAndRefundActivationType, 
        var value: Int?, 
        var productId: String?, 
        var cpf: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineDetachAndRefundActivation {
                return gson.fromJson(jsonToParse.toString(), OfflineDetachAndRefundActivation::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineDetachAndRefundActivation> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineDetachAndRefundActivation>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class EntranceConsumedRecharge(
        var usedValue: Int, 
        var rechargeId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EntranceConsumedRecharge {
                return gson.fromJson(jsonToParse.toString(), EntranceConsumedRecharge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EntranceConsumedRecharge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EntranceConsumedRecharge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineEntrance(
        var count: Int, 
        var value: Int, 
        var id: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineEntrance {
                return gson.fromJson(jsonToParse.toString(), OfflineEntrance::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineEntrance> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineEntrance>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineEntranceAction(
        var recharges: ArrayList<OfflineRechargeEntranceAction>, 
        var entrances: ArrayList<OfflineEntrance>, 
        var cpf: String, 
        var transactionId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineEntranceAction {
                return gson.fromJson(jsonToParse.toString(), OfflineEntranceAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineEntranceAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineEntranceAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineRefundRecharge(
        var cpf: String, 
        var rechargeId: String, 
        var amount: Int, 
        var obs: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineRefundRecharge {
                return gson.fromJson(jsonToParse.toString(), OfflineRefundRecharge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineRefundRecharge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineRefundRecharge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineConsumeRecharge(
        var cpf: String, 
        var rechargeId: String, 
        var amount: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineConsumeRecharge {
                return gson.fromJson(jsonToParse.toString(), OfflineConsumeRecharge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineConsumeRecharge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineConsumeRecharge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineAttachAction(
        var cpf: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineAttachAction {
                return gson.fromJson(jsonToParse.toString(), OfflineAttachAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineAttachAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineAttachAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineDetachAction(
        var cpf: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineDetachAction {
                return gson.fromJson(jsonToParse.toString(), OfflineDetachAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineDetachAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineDetachAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineCreateUserAction(
        var cpf: String, 
        var name: String, 
        var gender: Gender, 
        var phone: String, 
        var isForeign: Boolean, 
        var avatar: String?, 
        var email: String?, 
        var birthdate: Calendar? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineCreateUserAction {
                return gson.fromJson(jsonToParse.toString(), OfflineCreateUserAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineCreateUserAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineCreateUserAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineRechargeAction(
        var id: String, 
        var cpf: String, 
        var method: PaymentMethod?, 
        var cardTransactionId: String?, 
        var stoneTransactionJson: String?, 
        var isBonus: Boolean, 
        var reason: String?, 
        var value: Int, 
        var date: Calendar, 
        var expiresAt: Calendar?, 
        var isMinimalConsumption: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineRechargeAction {
                return gson.fromJson(jsonToParse.toString(), OfflineRechargeAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineRechargeAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineRechargeAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineRechargeEntranceAction(
        var usedValue: Int, 
        var id: String, 
        var cpf: String, 
        var method: PaymentMethod?, 
        var cardTransactionId: String?, 
        var stoneTransactionJson: String?, 
        var isBonus: Boolean, 
        var reason: String?, 
        var value: Int, 
        var date: Calendar, 
        var expiresAt: Calendar?, 
        var isMinimalConsumption: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineRechargeEntranceAction {
                return gson.fromJson(jsonToParse.toString(), OfflineRechargeEntranceAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineRechargeEntranceAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineRechargeEntranceAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineTransactionAction(
        var id: String, 
        var type: OfflineTransactionActionType, 
        var buyers: ArrayList<OfflineTransactionActionBuyers>, 
        var tip: Int, 
        var products: ArrayList<OfflineTransactionActionProducts>, 
        var combos: ArrayList<OfflineTransactionActionCombos>, 
        var obs: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineTransactionAction {
                return gson.fromJson(jsonToParse.toString(), OfflineTransactionAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineTransactionAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineTransactionAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineTransactionActionBuyers(
        var cpf: String, 
        var name: String?, 
        var zigUid: String?, 
        var payments: ArrayList<OfflineTransactionActionBuyersPayments> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineTransactionActionBuyers {
                return gson.fromJson(jsonToParse.toString(), OfflineTransactionActionBuyers::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineTransactionActionBuyers> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineTransactionActionBuyers>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineTransactionActionBuyersPayments(
        var type: OfflineTransactionActionBuyersPaymentsType, 
        var id: String?, 
        var value: Int, 
        var paid: Boolean, 
        var isRelativeToTip: Boolean?, 
        var prePaidType: OfflineTransactionActionBuyersPaymentsPrePaidType? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineTransactionActionBuyersPayments {
                return gson.fromJson(jsonToParse.toString(), OfflineTransactionActionBuyersPayments::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineTransactionActionBuyersPayments> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineTransactionActionBuyersPayments>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineTransactionActionProducts(
        var id: String, 
        var relativeId: Int?, 
        var count: Int, 
        var value: Int, 
        var discountValue: Int, 
        var promotionId: String?, 
        var offlinePromotionId: String?, 
        var offlinePromotionUserCpf: String?, 
        var obs: String, 
        var bar: String?, 
        var deliveredBy: String?, 
        var deliveredAt: Calendar?, 
        var name: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineTransactionActionProducts {
                return gson.fromJson(jsonToParse.toString(), OfflineTransactionActionProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineTransactionActionProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineTransactionActionProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineTransactionActionCombos(
        var id: String, 
        var relativeId: Int?, 
        var count: Int, 
        var value: Int, 
        var promotionId: String?, 
        var offlinePromotionId: String?, 
        var offlinePromotionUserCpf: String?, 
        var discountValue: Int, 
        var obs: String, 
        var bar: String?, 
        var deliveredBy: String?, 
        var deliveredAt: Calendar?, 
        var name: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineTransactionActionCombos {
                return gson.fromJson(jsonToParse.toString(), OfflineTransactionActionCombos::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineTransactionActionCombos> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineTransactionActionCombos>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SignedOfflineAction(
        var action: String, 
        var sign: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SignedOfflineAction {
                return gson.fromJson(jsonToParse.toString(), SignedOfflineAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SignedOfflineAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SignedOfflineAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineActionError(
        var actionId: String, 
        var err: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineActionError {
                return gson.fromJson(jsonToParse.toString(), OfflineActionError::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineActionError> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineActionError>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PPUAuthorization(
        var id: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PPUAuthorization {
                return gson.fromJson(jsonToParse.toString(), PPUAuthorization::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PPUAuthorization> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PPUAuthorization>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ValidRecharge(
        var id: String, 
        var offlineId: String, 
        var value: Int, 
        var isBonus: Boolean, 
        var date: Calendar, 
        var expiresAt: Calendar?, 
        var placeId: String?, 
        var eventId: String?, 
        var rechargeScope: String, 
        var isMinimalConsumption: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ValidRecharge {
                return gson.fromJson(jsonToParse.toString(), ValidRecharge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ValidRecharge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ValidRecharge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MqttPlaceInfo(
        var msgId: String?, 
        var currentEventInfo: MqttEventInfo?, 
        var hasTip: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MqttPlaceInfo {
                return gson.fromJson(jsonToParse.toString(), MqttPlaceInfo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MqttPlaceInfo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MqttPlaceInfo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MqttDeviceStatus(
        var online: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MqttDeviceStatus {
                return gson.fromJson(jsonToParse.toString(), MqttDeviceStatus::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MqttDeviceStatus> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MqttDeviceStatus>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineUserTransaction(
        var id: String, 
        var totalValue: Int, 
        var hadTip: Boolean, 
        var paidValue: Int, 
        var noTipPaidValue: Int, 
        var tipOnlyPaidValue: Int, 
        var noTipRemainingValueToPay: Int, 
        var tipRemainingValueToPay: Int, 
        var buyersQnt: Int, 
        var buyersValue: Int, 
        var date: Calendar, 
        var isRefunded: Boolean, 
        var transactionType: OfflineUserTransactionTransactionType, 
        var products: ArrayList<OfflineUserTransactionProducts>, 
        var combos: ArrayList<OfflineUserTransactionCombos> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineUserTransaction {
                return gson.fromJson(jsonToParse.toString(), OfflineUserTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineUserTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineUserTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineUserTransactionProducts(
        var id: String, 
        var value: Int, 
        var count: Int, 
        var discountValue: Int, 
        var relativeId: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineUserTransactionProducts {
                return gson.fromJson(jsonToParse.toString(), OfflineUserTransactionProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineUserTransactionProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineUserTransactionProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineUserTransactionCombos(
        var id: String, 
        var value: Int, 
        var count: Int, 
        var discountValue: Int, 
        var relativeId: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineUserTransactionCombos {
                return gson.fromJson(jsonToParse.toString(), OfflineUserTransactionCombos::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineUserTransactionCombos> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineUserTransactionCombos>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineUserPayments(
        var id: String, 
        var offlineId: String, 
        var value: Int, 
        var date: Calendar, 
        var transactions: ArrayList<OfflineUserPaymentsTransactions>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineUserPayments {
                return gson.fromJson(jsonToParse.toString(), OfflineUserPayments::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineUserPayments> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineUserPayments>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineUserPaymentsTransactions(
        var value: Int, 
        var id: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineUserPaymentsTransactions {
                return gson.fromJson(jsonToParse.toString(), OfflineUserPaymentsTransactions::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineUserPaymentsTransactions> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineUserPaymentsTransactions>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineUserState(
        var id: String, 
        var name: String?, 
        var cpf: String, 
        var gender: Gender, 
        var isForeign: Boolean, 
        var hasPaidCouvert: Boolean, 
        var recharges: ArrayList<ValidRecharge>, 
        var transactions: ArrayList<OfflineUserTransaction>, 
        var payments: ArrayList<OfflineUserPayments>?, 
        var shouldChargeForZigTag: Boolean, 
        var ppuAuthorization: PPUAuthorization?, 
        var shouldSynchronize: Boolean, 
        var notPaidBillsFromOldEvents: Int, 
        var ppuError: String?, 
        var promotions: ArrayList<OfflineUserStatePromotions> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineUserState {
                return gson.fromJson(jsonToParse.toString(), OfflineUserState::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineUserState> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineUserState>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OfflineUserStatePromotions(
        var id: String, 
        var offlineId: String, 
        var uses: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OfflineUserStatePromotions {
                return gson.fromJson(jsonToParse.toString(), OfflineUserStatePromotions::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OfflineUserStatePromotions> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OfflineUserStatePromotions>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ZigTagBlockStatus(
        var readyCount: Int, 
        var totalCount: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigTagBlockStatus {
                return gson.fromJson(jsonToParse.toString(), ZigTagBlockStatus::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigTagBlockStatus> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigTagBlockStatus>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BlockedZigTag(
        var uid: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BlockedZigTag {
                return gson.fromJson(jsonToParse.toString(), BlockedZigTag::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BlockedZigTag> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BlockedZigTag>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTransactionProduct(
        var id: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTransactionProduct {
                return gson.fromJson(jsonToParse.toString(), RefundTransactionProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTransactionProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTransactionProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTransactionCombo(
        var id: String, 
        var count: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTransactionCombo {
                return gson.fromJson(jsonToParse.toString(), RefundTransactionCombo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTransactionCombo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTransactionCombo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTransaction(
        var combos: ArrayList<RefundTransactionCombo>?, 
        var products: ArrayList<RefundTransactionProduct>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTransaction {
                return gson.fromJson(jsonToParse.toString(), RefundTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTransactionRequest(
        var transactionId: String, 
        var products: ArrayList<RefundTransactionProduct>, 
        var combos: ArrayList<RefundTransactionCombo>, 
        var reason: String, 
        var cpf: String, 
        var name: String?, 
        var refundAll: Boolean, 
        var shouldReturnToStock: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTransactionRequest {
                return gson.fromJson(jsonToParse.toString(), RefundTransactionRequest::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTransactionRequest> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTransactionRequest>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundMultipleTransactionsRequest(
        var reason: String, 
        var cpf: String, 
        var transactions: ArrayList<RefundTransaction>?, 
        var name: String?, 
        var shouldReturnToStock: Boolean, 
        var transactionIds: ArrayList<String> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundMultipleTransactionsRequest {
                return gson.fromJson(jsonToParse.toString(), RefundMultipleTransactionsRequest::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundMultipleTransactionsRequest> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundMultipleTransactionsRequest>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTransactionResult(
        var request: RefundTransactionRequest, 
        var incrementPpuAuths: ArrayList<RefundTransactionResultIncrementPpuAuths>, 
        var incrementRecharges: ArrayList<RefundTransactionResultIncrementRecharges>, 
        var newRecharges: ArrayList<ValidRecharge>, 
        var newTransactionId: String?, 
        var newTransaction: RefundTransactionResultNewTransaction? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTransactionResult {
                return gson.fromJson(jsonToParse.toString(), RefundTransactionResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTransactionResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTransactionResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTransactionResultIncrementPpuAuths(
        var id: String, 
        var incrementValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTransactionResultIncrementPpuAuths {
                return gson.fromJson(jsonToParse.toString(), RefundTransactionResultIncrementPpuAuths::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTransactionResultIncrementPpuAuths> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTransactionResultIncrementPpuAuths>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTransactionResultIncrementRecharges(
        var id: String, 
        var incrementValue: Int, 
        var isBonus: Boolean, 
        var date: Calendar, 
        var expiresAt: Calendar?, 
        var placeId: String?, 
        var eventId: String?, 
        var rechargeScope: String, 
        var isMinimalConsumption: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTransactionResultIncrementRecharges {
                return gson.fromJson(jsonToParse.toString(), RefundTransactionResultIncrementRecharges::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTransactionResultIncrementRecharges> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTransactionResultIncrementRecharges>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTransactionResultNewTransaction(
        var offlineId: String, 
        var buyerValue: Int, 
        var date: Calendar, 
        var isSplitted: Boolean, 
        var shouldChargeTip: Boolean, 
        var type: TransactionInfoType, 
        var remainingValueToPay: Int, 
        var products: ArrayList<RefundTransactionResultNewTransactionProducts> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTransactionResultNewTransaction {
                return gson.fromJson(jsonToParse.toString(), RefundTransactionResultNewTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTransactionResultNewTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTransactionResultNewTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTransactionResultNewTransactionProducts(
        var relativeId: Int, 
        var value: Int, 
        var count: Int, 
        var usedPromotion: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTransactionResultNewTransactionProducts {
                return gson.fromJson(jsonToParse.toString(), RefundTransactionResultNewTransactionProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTransactionResultNewTransactionProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTransactionResultNewTransactionProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundUser(
        var cpf: String, 
        var refundTransactionResult: RefundTransactionResult 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundUser {
                return gson.fromJson(jsonToParse.toString(), RefundUser::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundUser> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundUser>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UsedPromotion(
        var id: String, 
        var discountValue: Int, 
        var usedProductId: String, 
        var promotionName: String 
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

    data class CreditAsker(
        var cpf: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CreditAsker {
                return gson.fromJson(jsonToParse.toString(), CreditAsker::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CreditAsker> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CreditAsker>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserWithPpu(
        var cpf: String, 
        var creditAuthorizationId: String?, 
        var value: Int?, 
        var err: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserWithPpu {
                return gson.fromJson(jsonToParse.toString(), UserWithPpu::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserWithPpu> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserWithPpu>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTip(
        var incrementPpuAuths: ArrayList<RefundTipIncrementPpuAuths>, 
        var incrementRecharges: ArrayList<RefundTipIncrementRecharges> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTip {
                return gson.fromJson(jsonToParse.toString(), RefundTip::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTip> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTip>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTipIncrementPpuAuths(
        var id: String, 
        var incrementValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTipIncrementPpuAuths {
                return gson.fromJson(jsonToParse.toString(), RefundTipIncrementPpuAuths::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTipIncrementPpuAuths> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTipIncrementPpuAuths>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundTipIncrementRecharges(
        var id: String, 
        var incrementValue: Int, 
        var isBonus: Boolean, 
        var date: Calendar, 
        var expiresAt: Calendar?, 
        var placeId: String?, 
        var eventId: String?, 
        var rechargeScope: String, 
        var isMinimalConsumption: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundTipIncrementRecharges {
                return gson.fromJson(jsonToParse.toString(), RefundTipIncrementRecharges::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundTipIncrementRecharges> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundTipIncrementRecharges>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OldEventBill(
        var noTipOwedValue: Int, 
        var tipOwedValue: Int, 
        var products: ArrayList<OldEventBillProducts> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OldEventBill {
                return gson.fromJson(jsonToParse.toString(), OldEventBill::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OldEventBill> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OldEventBill>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OldEventBillProducts(
        var id: String, 
        var unitValue: Int, 
        var count: Int, 
        var name: String, 
        var category: String, 
        var date: Calendar 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OldEventBillProducts {
                return gson.fromJson(jsonToParse.toString(), OldEventBillProducts::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OldEventBillProducts> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OldEventBillProducts>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PrePaidRefundResult(
        var cpf: String, 
        var incrementPpuAuths: ArrayList<PrePaidRefundResultIncrementPpuAuths>, 
        var incrementRecharges: ArrayList<PrePaidRefundResultIncrementRecharges> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PrePaidRefundResult {
                return gson.fromJson(jsonToParse.toString(), PrePaidRefundResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PrePaidRefundResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PrePaidRefundResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PrePaidRefundResultIncrementPpuAuths(
        var id: String, 
        var incrementValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PrePaidRefundResultIncrementPpuAuths {
                return gson.fromJson(jsonToParse.toString(), PrePaidRefundResultIncrementPpuAuths::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PrePaidRefundResultIncrementPpuAuths> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PrePaidRefundResultIncrementPpuAuths>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PrePaidRefundResultIncrementRecharges(
        var id: String, 
        var incrementValue: Int, 
        var isBonus: Boolean, 
        var date: Calendar, 
        var expiresAt: Calendar?, 
        var placeId: String?, 
        var eventId: String?, 
        var rechargeScope: String, 
        var isMinimalConsumption: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PrePaidRefundResultIncrementRecharges {
                return gson.fromJson(jsonToParse.toString(), PrePaidRefundResultIncrementRecharges::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PrePaidRefundResultIncrementRecharges> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PrePaidRefundResultIncrementRecharges>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Entrance(
        var id: String, 
        var relativeEventId: Int?, 
        var relativeId: Int?, 
        var name: String, 
        var minimumConsumption: Int, 
        var value: Int, 
        var image: ImageUrl? 
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

    data class EntranceList(
        var user: String, 
        var entrances: ArrayList<String> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): EntranceList {
                return gson.fromJson(jsonToParse.toString(), EntranceList::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<EntranceList> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<EntranceList>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Invoice(
        var id: String, 
        var issuedAt: Calendar, 
        var imgUrl: String?, 
        var printData: ArrayList<ByteArray>? 
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

    data class Recharge(
        var id: String?, 
        var value: Int?, 
        var expiresAt: Calendar?, 
        var rechargeType: RechargeType, 
        var method: String?, 
        var refunded: Boolean? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Recharge {
                return gson.fromJson(jsonToParse.toString(), Recharge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Recharge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Recharge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RechargeBackup(
        var id: String, 
        var offlineId: String, 
        var remainingValue: Int, 
        var isMinimumConsumption: Boolean, 
        var isBonus: Boolean, 
        var userCpf: String, 
        var userName: String, 
        var date: Calendar, 
        var expiresAt: Calendar? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RechargeBackup {
                return gson.fromJson(jsonToParse.toString(), RechargeBackup::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RechargeBackup> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RechargeBackup>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PlaceRelation(
        var createdBy: User?, 
        var placeId: String, 
        var user: User?, 
        var cpf: String?, 
        var email: String?, 
        var phone: String?, 
        var zigDevice: String?, 
        var description: String, 
        var employee: Employee?, 
        var couvertPercentage: Int? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PlaceRelation {
                return gson.fromJson(jsonToParse.toString(), PlaceRelation::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PlaceRelation> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PlaceRelation>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SoldProduct(
        var name: String, 
        var quantity: Int, 
        var totalValue: Int, 
        var totalDiscount: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SoldProduct {
                return gson.fromJson(jsonToParse.toString(), SoldProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SoldProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SoldProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class BarProductReport(
        var barName: String, 
        var productsSold: ArrayList<SoldProduct> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BarProductReport {
                return gson.fromJson(jsonToParse.toString(), BarProductReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BarProductReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BarProductReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellerReport(
        var name: String, 
        var quantity: Int, 
        var totalValue: Int 
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

    data class BarSellerReport(
        var barName: String, 
        var sellerReport: ArrayList<SellerReport> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): BarSellerReport {
                return gson.fromJson(jsonToParse.toString(), BarSellerReport::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<BarSellerReport> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<BarSellerReport>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Buyer(
        var zigAuthorization: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Buyer {
                return gson.fromJson(jsonToParse.toString(), Buyer::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Buyer> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Buyer>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Sell(
        var buyers: ArrayList<Buyer>, 
        var partialTransaction: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Sell {
                return gson.fromJson(jsonToParse.toString(), Sell::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Sell> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Sell>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Fail(
        var zigCode: String, 
        var name: String?, 
        var reason: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Fail {
                return gson.fromJson(jsonToParse.toString(), Fail::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Fail> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Fail>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PromotionResult(
        var product: String, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PromotionResult {
                return gson.fromJson(jsonToParse.toString(), PromotionResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PromotionResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PromotionResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellResult(
        var fails: ArrayList<Fail>?, 
        var success: String?, 
        var promotion: PromotionResult?, 
        var status: TransactionStatus, 
        var states: ArrayList<UserState>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellResult {
                return gson.fromJson(jsonToParse.toString(), SellResult::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellResult> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellResult>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProportionalValue(
        var value: Int, 
        var quantity: Int, 
        var unit: String 
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

    data class Product(
        var id: String, 
        var name: String, 
        var category: String, 
        var value: Int, 
        var quantity: Int, 
        var image: ImageUrl?, 
        var sellPoints: Int?, 
        var relativeId: Int?, 
        var placeId: String?, 
        var deliveredByName: String?, 
        var barIds: ArrayList<String>?, 
        var proportionalValue: ProportionalValue? 
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

    data class Combo(
        var id: String, 
        var products: ArrayList<Product>, 
        var value: Int, 
        var name: String, 
        var category: String?, 
        var quantity: Int, 
        var description: String?, 
        var image: ImageUrl?, 
        var relativeId: Int?, 
        var placeId: String?, 
        var deliveredByName: String?, 
        var barIds: ArrayList<String>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Combo {
                return gson.fromJson(jsonToParse.toString(), Combo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Combo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Combo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellableThings(
        var products: ArrayList<Product>, 
        var combos: ArrayList<Combo> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellableThings {
                return gson.fromJson(jsonToParse.toString(), SellableThings::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellableThings> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellableThings>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellablesCategory(
        var id: String, 
        var name: String, 
        var description: String?, 
        var priority: Int, 
        var image: ImageUrl?, 
        var products: ArrayList<Product>, 
        var combos: ArrayList<Combo>, 
        var subCategories: ArrayList<SellablesCategorySubCategories> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellablesCategory {
                return gson.fromJson(jsonToParse.toString(), SellablesCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellablesCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellablesCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellablesCategorySubCategories(
        var id: String, 
        var name: String, 
        var description: String?, 
        var priority: Int, 
        var image: ImageUrl?, 
        var products: ArrayList<Product>, 
        var combos: ArrayList<Combo>, 
        var subCategories: ArrayList<SellablesCategorySubCategoriesSubCategories> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellablesCategorySubCategories {
                return gson.fromJson(jsonToParse.toString(), SellablesCategorySubCategories::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellablesCategorySubCategories> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellablesCategorySubCategories>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellablesCategorySubCategoriesSubCategories(
        var id: String, 
        var name: String, 
        var description: String?, 
        var priority: Int, 
        var image: ImageUrl?, 
        var products: ArrayList<Product>, 
        var combos: ArrayList<Combo> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellablesCategorySubCategoriesSubCategories {
                return gson.fromJson(jsonToParse.toString(), SellablesCategorySubCategoriesSubCategories::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellablesCategorySubCategoriesSubCategories> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellablesCategorySubCategoriesSubCategories>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class FavoritesCategory(
        var products: ArrayList<Product>, 
        var combos: ArrayList<Combo> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): FavoritesCategory {
                return gson.fromJson(jsonToParse.toString(), FavoritesCategory::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<FavoritesCategory> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<FavoritesCategory>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class SellableThings2(
        var categories: ArrayList<SellablesCategory>, 
        var favorites: FavoritesCategory 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): SellableThings2 {
                return gson.fromJson(jsonToParse.toString(), SellableThings2::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<SellableThings2> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<SellableThings2>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CompleteProduct(
        var id: String, 
        var name: String, 
        var category: String, 
        var relativeId: Int?, 
        var value: Int, 
        var isActive: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CompleteProduct {
                return gson.fromJson(jsonToParse.toString(), CompleteProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CompleteProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CompleteProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class StockTransfers(
        var date: Calendar, 
        var stocker: String, 
        var employee: String?, 
        var quantity: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): StockTransfers {
                return gson.fromJson(jsonToParse.toString(), StockTransfers::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<StockTransfers> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<StockTransfers>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductTransfer(
        var id: String, 
        var name: String, 
        var category: String, 
        var transfers: ArrayList<StockTransfers> 
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

    data class Ticket(
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Ticket {
                return gson.fromJson(jsonToParse.toString(), Ticket::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Ticket> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Ticket>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserTicket(
        var name: String?, 
        var cpf: String?, 
        var tickets: ArrayList<Ticket> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserTicket {
                return gson.fromJson(jsonToParse.toString(), UserTicket::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserTicket> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserTicket>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Bar(
        var id: String, 
        var name: String, 
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

    data class Permission(
        var slug: String, 
        var name: String 
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

    data class Employee(
        var id: String, 
        var name: String, 
        var username: String, 
        var organization: Organization, 
        var places: ArrayList<Place>, 
        var bar: Bar?, 
        var permissions: ArrayList<Permission>?, 
        var zigReadPermissionKey: String?, 
        var avatar: ImageUrl?, 
        var currentEvents: ArrayList<CurrentEvent>?, 
        var signKey: String, 
        var currentDate: Calendar? 
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

    data class SellerTransaction(
        var id: String, 
        var date: Calendar, 
        var products: ArrayList<Product>, 
        var combos: ArrayList<Combo>, 
        var buyers: ArrayList<User>, 
        var refunded: Boolean, 
        var value: Int, 
        var obs: String? 
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

    data class CashierSingleClosing(
        var credit: Int, 
        var cash: Int, 
        var postEvent: Int, 
        var debit: Int, 
        var zigCredit: Int, 
        var zigDebit: Int, 
        var voucher: Int, 
        var dashboard: Int, 
        var anticipated: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CashierSingleClosing {
                return gson.fromJson(jsonToParse.toString(), CashierSingleClosing::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CashierSingleClosing> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CashierSingleClosing>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CashierClosing(
        var rechargeMoneyReceived: CashierSingleClosing, 
        var postPaidMoneyReceived: CashierSingleClosing 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CashierClosing {
                return gson.fromJson(jsonToParse.toString(), CashierClosing::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CashierClosing> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CashierClosing>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MqttEventInfo(
        var msgId: String?, 
        var id: String, 
        var hasTip: Boolean, 
        var rechargeExpirationHours: Int?, 
        var offlineKey: String, 
        var zigTagProduct: Product?, 
        var hasActivePromotion: Boolean, 
        var getUserDataInBackground: Boolean, 
        var postPaidLimit: Int, 
        var startDate: Calendar, 
        var maleCouvert: MqttEventInfoMaleCouvert?, 
        var femaleCouvert: MqttEventInfoFemaleCouvert?, 
        var features: ArrayList<String> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MqttEventInfo {
                return gson.fromJson(jsonToParse.toString(), MqttEventInfo::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MqttEventInfo> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MqttEventInfo>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MqttEventInfoMaleCouvert(
        var id: String, 
        var relativeId: Int, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MqttEventInfoMaleCouvert {
                return gson.fromJson(jsonToParse.toString(), MqttEventInfoMaleCouvert::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MqttEventInfoMaleCouvert> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MqttEventInfoMaleCouvert>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MqttEventInfoFemaleCouvert(
        var id: String, 
        var relativeId: Int, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MqttEventInfoFemaleCouvert {
                return gson.fromJson(jsonToParse.toString(), MqttEventInfoFemaleCouvert::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MqttEventInfoFemaleCouvert> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MqttEventInfoFemaleCouvert>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CurrentEvent(
        var placeId: String, 
        var eventId: String, 
        var mqttEventInfo: MqttEventInfo? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CurrentEvent {
                return gson.fromJson(jsonToParse.toString(), CurrentEvent::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CurrentEvent> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CurrentEvent>::class.java).toList())
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
        var offlineId: String, 
        var name: String 
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

    data class PromotionInfo(
        var id: String, 
        var offlineId: String, 
        var name: String, 
        var productRelativeIds: ArrayList<Int>, 
        var discount: Float, 
        var maxUses: Int 
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

    data class ReserveAndPromotion(
        var reserves: ArrayList<Reserve>, 
        var promotions: ArrayList<PromotionInfo> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ReserveAndPromotion {
                return gson.fromJson(jsonToParse.toString(), ReserveAndPromotion::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ReserveAndPromotion> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ReserveAndPromotion>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Waiter(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Waiter {
                return gson.fromJson(jsonToParse.toString(), Waiter::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Waiter> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Waiter>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class DeliveryInstance(
        var type: SellingProductType, 
        var product: Product?, 
        var combo: Combo?, 
        var obs: String?, 
        var transactionId: String, 
        var waiter: Waiter 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): DeliveryInstance {
                return gson.fromJson(jsonToParse.toString(), DeliveryInstance::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<DeliveryInstance> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<DeliveryInstance>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class DeliveryTransaction(
        var id: String, 
        var deliveryInstances: ArrayList<DeliveryInstance>, 
        var waiter: Waiter, 
        var date: Calendar, 
        var obs: String?, 
        var clientName: String?, 
        var refunded: Boolean?, 
        var relativeRefundedTransaction: String?, 
        var numbering: String?, 
        var groupId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): DeliveryTransaction {
                return gson.fromJson(jsonToParse.toString(), DeliveryTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<DeliveryTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<DeliveryTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ProductsToDelivery(
        var id: String, 
        var name: String, 
        var category: String, 
        var quantity: Int, 
        var soldTime: Calendar, 
        var readyTime: Calendar?, 
        var delivered: Boolean, 
        var buyers: String, 
        var refunded: Boolean, 
        var prodObs: String?, 
        var transactionObs: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ProductsToDelivery {
                return gson.fromJson(jsonToParse.toString(), ProductsToDelivery::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ProductsToDelivery> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ProductsToDelivery>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Organization(
        var id: String, 
        var name: String, 
        var description: String?, 
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

    data class Place(
        var id: String, 
        var name: String, 
        var description: String?, 
        var image: ImageUrl?, 
        var postPaidLimit: Int?, 
        var hasTip: Boolean?, 
        var tip: Float, 
        var location: PlaceLocation?, 
        var sellVisualizationFormat: PlaceSellVisualizationFormat, 
        var features: ArrayList<PlaceFeatures>, 
        var bars: ArrayList<Bar>, 
        var generalPassword: String? 
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

    data class PlaceLocation(
        var latitude: Float?, 
        var longitude: Float? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PlaceLocation {
                return gson.fromJson(jsonToParse.toString(), PlaceLocation::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PlaceLocation> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PlaceLocation>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PlaceFeatures(
        var id: String, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PlaceFeatures {
                return gson.fromJson(jsonToParse.toString(), PlaceFeatures::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PlaceFeatures> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PlaceFeatures>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PostPaidOwnedValue(
        var currentEvent: Int, 
        var oldEvents: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PostPaidOwnedValue {
                return gson.fromJson(jsonToParse.toString(), PostPaidOwnedValue::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PostPaidOwnedValue> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PostPaidOwnedValue>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PartialPayReturn(
        var notPaidTip: Int, 
        var notPaidTransaction: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PartialPayReturn {
                return gson.fromJson(jsonToParse.toString(), PartialPayReturn::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PartialPayReturn> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PartialPayReturn>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ZigMachinePayment(
        var notPaidTip: Int, 
        var notPaidTransaction: Int, 
        var acquirerResponseCode: String, 
        var cardEmvResponse: String?, 
        var cardPinMode: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigMachinePayment {
                return gson.fromJson(jsonToParse.toString(), ZigMachinePayment::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigMachinePayment> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigMachinePayment>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class TransactionBuyer(
        var name: String, 
        var cpf: String, 
        var paidValue: Int, 
        var avatar: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): TransactionBuyer {
                return gson.fromJson(jsonToParse.toString(), TransactionBuyer::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<TransactionBuyer> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<TransactionBuyer>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Transaction(
        var id: String, 
        var offlineId: String?, 
        var date: Calendar, 
        var products: ArrayList<Product>, 
        var combos: ArrayList<Combo>, 
        var seller: Seller, 
        var paid: Boolean, 
        var refunded: Boolean, 
        var value: Int, 
        var obs: String?, 
        var buyers: ArrayList<TransactionBuyer>? 
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

    data class PostPaidTransaction(
        var id: String, 
        var date: Calendar, 
        var products: ArrayList<Product>, 
        var combos: ArrayList<Combo>, 
        var seller: Seller, 
        var buyers: ArrayList<TransactionBuyer>, 
        var refunded: Boolean, 
        var value: Int, 
        var tip: Int, 
        var totalValue: Int, 
        var paid: Boolean 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PostPaidTransaction {
                return gson.fromJson(jsonToParse.toString(), PostPaidTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PostPaidTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PostPaidTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class OpenedBillBackup(
        var zigCode: String?, 
        var userId: String, 
        var value: Int, 
        var userName: String, 
        var userCpf: String, 
        var eventId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): OpenedBillBackup {
                return gson.fromJson(jsonToParse.toString(), OpenedBillBackup::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<OpenedBillBackup> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<OpenedBillBackup>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RefundActivationProduct(
        var id: String, 
        var refundPoints: Int, 
        var name: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RefundActivationProduct {
                return gson.fromJson(jsonToParse.toString(), RefundActivationProduct::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RefundActivationProduct> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RefundActivationProduct>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserSetup(
        var foreign: Boolean?, 
        var gender: Gender?, 
        var cpf: String, 
        var phone: String, 
        var name: String?, 
        var password: String?, 
        var address: String?, 
        var birthdate: Calendar? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserSetup {
                return gson.fromJson(jsonToParse.toString(), UserSetup::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserSetup> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserSetup>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ZigAndRelation(
        var zig: ZigDevice, 
        var relations: ArrayList<String> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigAndRelation {
                return gson.fromJson(jsonToParse.toString(), ZigAndRelation::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigAndRelation> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigAndRelation>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CpfDataOrUser(
        var name: String?, 
        var user: User? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CpfDataOrUser {
                return gson.fromJson(jsonToParse.toString(), CpfDataOrUser::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CpfDataOrUser> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CpfDataOrUser>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PaidTransaction(
        var id: String, 
        var paidValue: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PaidTransaction {
                return gson.fromJson(jsonToParse.toString(), PaidTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PaidTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PaidTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class UserRecharge(
        var id: String, 
        var value: Int, 
        var usedValue: Int, 
        var type: RechargeType, 
        var expiresAt: Calendar?, 
        var places: ArrayList<Place>? 
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

    data class ZigLineEvent(
        var id: String, 
        var date: Calendar, 
        var type: ZigLineEventType, 
        var zigCode: String?, 
        var place: Place?, 
        var recharge: UserRecharge?, 
        var transaction: Transaction?, 
        var paidTransactions: ArrayList<PaidTransaction>? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigLineEvent {
                return gson.fromJson(jsonToParse.toString(), ZigLineEvent::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigLineEvent> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigLineEvent>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CheckIn(
        var eventId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CheckIn {
                return gson.fromJson(jsonToParse.toString(), CheckIn::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CheckIn> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CheckIn>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class CheckOut(
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CheckOut {
                return gson.fromJson(jsonToParse.toString(), CheckOut::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CheckOut> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CheckOut>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Capture(
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Capture {
                return gson.fromJson(jsonToParse.toString(), Capture::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Capture> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Capture>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PPUCardAuthorization(
        var value: Int, 
        var cardId: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PPUCardAuthorization {
                return gson.fromJson(jsonToParse.toString(), PPUCardAuthorization::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PPUCardAuthorization> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PPUCardAuthorization>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Information(
        var title: String, 
        var description: String, 
        var image: ImageUrl? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Information {
                return gson.fromJson(jsonToParse.toString(), Information::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Information> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Information>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class RechargeAction(
        var id: String?, 
        var offlineId: String?, 
        var value: Int?, 
        var usedValue: Int?, 
        var expiresAt: Calendar?, 
        var rechargeType: RechargeType, 
        var method: RechargeActionMethod?, 
        var author: String?, 
        var refunded: Boolean? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): RechargeAction {
                return gson.fromJson(jsonToParse.toString(), RechargeAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<RechargeAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<RechargeAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PaidPostPaidTransaction(
        var id: String, 
        var offlineId: String?, 
        var date: Calendar, 
        var payers: ArrayList<String>, 
        var transactions: ArrayList<PaidPostPaidTransactionTransactions> 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PaidPostPaidTransaction {
                return gson.fromJson(jsonToParse.toString(), PaidPostPaidTransaction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PaidPostPaidTransaction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PaidPostPaidTransaction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PaidPostPaidTransactionTransactions(
        var transactionId: String, 
        var eventId: String, 
        var type: PaidPostPaidTransactionTransactionsType, 
        var value: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PaidPostPaidTransactionTransactions {
                return gson.fromJson(jsonToParse.toString(), PaidPostPaidTransactionTransactions::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PaidPostPaidTransactionTransactions> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PaidPostPaidTransactionTransactions>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Action(
        var id: String, 
        var date: Calendar, 
        var type: ActionType, 
        var zigCode: String?, 
        var info: Information?, 
        var recharge: RechargeAction?, 
        var transaction: Transaction?, 
        var checkOut: CheckOut?, 
        var capture: Capture?, 
        var ppuCardAuthorization: PPUCardAuthorization? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): Action {
                return gson.fromJson(jsonToParse.toString(), Action::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<Action> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<Action>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class HistoryAction(
        var id: String, 
        var date: Calendar, 
        var type: ActionType, 
        var zigCode: String?, 
        var info: Information?, 
        var recharge: RechargeAction?, 
        var transaction: Transaction?, 
        var checkIn: CheckIn?, 
        var checkOut: CheckOut?, 
        var capture: Capture?, 
        var ppuCardAuthorization: PPUCardAuthorization?, 
        var paidPostPaid: PaidPostPaidTransaction? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): HistoryAction {
                return gson.fromJson(jsonToParse.toString(), HistoryAction::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<HistoryAction> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<HistoryAction>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class User(
        var id: String, 
        var name: String?, 
        var phone: String, 
        var cpf: String, 
        var email: String?, 
        var avatar: ImageUrl?, 
        var gender: Gender?, 
        var birthDate: Calendar?, 
        var foreign: Boolean?, 
        var relations: ArrayList<String>?, 
        var isCheckedIn: Boolean 
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

    data class UserState(
        var user: User, 
        var ppuCard: Boolean, 
        var prePaidValidHere: Int 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): UserState {
                return gson.fromJson(jsonToParse.toString(), UserState::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<UserState> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<UserState>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class MqttSyncUser(
        var action: MqttSyncUserAction, 
        var userCpf: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): MqttSyncUser {
                return gson.fromJson(jsonToParse.toString(), MqttSyncUser::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<MqttSyncUser> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<MqttSyncUser>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ZigPayment(
        var type: ZigPaymentType, 
        var value: Int, 
        var cardHash: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigPayment {
                return gson.fromJson(jsonToParse.toString(), ZigPayment::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigPayment> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigPayment>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ZigMachineAnswer(
        var acquirerResponseCode: String, 
        var cardEmvResponse: String?, 
        var cardPinMode: String?, 
        var partialPaymentId: String? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigMachineAnswer {
                return gson.fromJson(jsonToParse.toString(), ZigMachineAnswer::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigMachineAnswer> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigMachineAnswer>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class PostPayment(
        var cardTransactionId: String?, 
        var value: Int?, 
        var method: PostMethod 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): PostPayment {
                return gson.fromJson(jsonToParse.toString(), PostPayment::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<PostPayment> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<PostPayment>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class StonePostPayment(
        var stoneTransactionJson: String?, 
        var value: Int, 
        var method: PostMethod 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): StonePostPayment {
                return gson.fromJson(jsonToParse.toString(), StonePostPayment::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<StonePostPayment> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<StonePostPayment>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class Payment(
        var method: ZigMachinePaymentMethod, 
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

    data class CipurseChallenge(
        var id: String, 
        var expiresAt: Calendar, 
        var challengeRandom: String, 
        var keyRandom: String, 
        var serverChallenge: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): CipurseChallenge {
                return gson.fromJson(jsonToParse.toString(), CipurseChallenge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<CipurseChallenge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<CipurseChallenge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class DesfireChallenge(
        var id: String, 
        var expiresAt: Calendar, 
        var encryptedRandomPair: String 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): DesfireChallenge {
                return gson.fromJson(jsonToParse.toString(), DesfireChallenge::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<DesfireChallenge> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<DesfireChallenge>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    data class ZigDevice(
        var user: User?, 
        var zigCode: String, 
        var paid: Boolean? 
    ): Serializable {

        companion object {
            fun fromJson(jsonToParse : JSONObject): ZigDevice {
                return gson.fromJson(jsonToParse.toString(), ZigDevice::class.java)
            }

            fun fromJsonArray(jsonArrayToParse : JSONArray): ArrayList<ZigDevice> {
                return ArrayList(gson.fromJson(jsonArrayToParse.toString(), Array<ZigDevice>::class.java).toList())
            }
        }
        fun toJson(): JSONObject {
            return JSONObject(gson.toJson(this))    }}

    enum class Gender {
    Male, 
    Female, 
    Other
    }

    enum class SellingProductType {
    Product, 
    Combo
    }

    enum class PaymentMethod {
    ZigPosCredit, 
    ZigPosDebit, 
    CreditCard, 
    DebitCard, 
    Cash, 
    App, 
    Voucher
    }

    enum class OfflineActionType {
    createUser, 
    recharge, 
    transaction, 
    attach, 
    detach, 
    refundRecharge, 
    consumeRecharge, 
    sellEntrance, 
    detachAndRefundActivation, 
    postPayment, 
    checkout, 
    refundNotPaidTransaction, 
    refundMultipleNotPaidTransactions, 
    groupedSell, 
    payOldEventsBill, 
    discount, 
    attachToReserve, 
    completeRefund, 
    transferUnpaidTransactions, 
    sellWithoutCard, 
    tipRemoval, 
    attachPromotion
    }

    enum class OfflineSellWithoutCardActionType {
    Normal, 
    Couvert, 
    ZigCard, 
    Entrance, 
    MinimumConsumption, 
    Tip
    }

    enum class OfflinePostPaymentMethod {
    ZigPosCredit, 
    ZigPosDebit, 
    CreditCard, 
    DebitCard, 
    Cash, 
    Recharge, 
    App, 
    Voucher
    }

    enum class OfflineDetachAndRefundActivationType {
    Cash, 
    Product
    }

    enum class OfflineTransactionActionType {
    Normal, 
    Couvert, 
    ZigCard, 
    Entrance, 
    MinimumConsumption, 
    Tip
    }

    enum class OfflineTransactionActionBuyersPaymentsType {
    PrePaid, 
    PPU, 
    PostPaid
    }

    enum class OfflineTransactionActionBuyersPaymentsPrePaidType {
    MinimumConsumption, 
    Bonus, 
    FromEvent, 
    FromOtherEvent, 
    FromApp
    }

    enum class OfflineUserTransactionTransactionType {
    Normal, 
    Couvert, 
    ZigCard, 
    Entrance, 
    MinimumConsumption, 
    Tip
    }

    enum class TransactionInfoType {
    Normal, 
    Couvert, 
    ZigCard, 
    Entrance, 
    MinimumConsumption, 
    Tip
    }

    enum class RechargeType {
    Bonus, 
    Refund, 
    CreditViaApp, 
    CreditViaPlace
    }

    enum class Category {
    Drink, 
    Food, 
    Desert, 
    Ticket
    }

    enum class TransactionStatus {
    Delivered, 
    NotDelivered
    }

    enum class KnownFeature {
    backoffice, 
    listSell, 
    hasImageInRegister
    }

    enum class DeepLink {
    None, 
    DeliveredProducts, 
    ProductsDone
    }

    enum class PlaceSellVisualizationFormat {
    Grid, 
    List
    }

    enum class ZigLineEventType {
    Transaction, 
    Information, 
    Recharge, 
    Refund, 
    CheckIn, 
    CheckOut, 
    SetupZig, 
    DetachZig, 
    BlockZig, 
    UnblockZig, 
    Welcome, 
    PaidPostPaidTransaction, 
    PPUCardChange, 
    PPUCardAuthorization, 
    PPUCapture, 
    GenericStateChange, 
    UpdateTransaction
    }

    enum class ActionType {
    Transaction, 
    Information, 
    Recharge, 
    SetupZig, 
    DetachZig, 
    BlockZig, 
    UnblockZig, 
    PaidPostPaidTransaction, 
    PPUCardChange, 
    CheckIn, 
    CheckOut
    }

    enum class RechargeActionMethod {
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

    enum class PaidPostPaidTransactionTransactionsType {
    Normal, 
    Couvert, 
    ZigCard, 
    Entrance, 
    MinimumConsumption, 
    Tip
    }

    enum class MqttSyncUserAction {
    add, 
    remove
    }

    enum class ZigPaymentType {
    ZigPosDebit, 
    ZigPosCredit
    }

    enum class PostMethod {
    ZigPosDebit, 
    ZigPosCredit, 
    CreditCard, 
    DebitCard, 
    Cash, 
    Voucher
    }

    enum class ZigMachinePaymentMethod {
    CreditCard, 
    DebitCard, 
    Cash, 
    Voucher
    }

    enum class ErrorType {
    NotLoggedIn, 
    LacksPermission, 
    InvalidArgument, 
    WrongLogin, 
    DoesntExist, 
    NoCreditCardAttached, 
    NotAuthorized, 
    BackofficeError, 
    Fatal, 
    Connection, 
    Serialization
    }

var calls = object: Calls { 
         override fun sendOfflineActions(list: ArrayList<SignedOfflineAction>, flag: Int?, callback: (error: Error?, OfflineActionError: ArrayList<OfflineActionError>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("list", JSONArray().apply { 
            list.forEach { item -> put(gson.toJson(item)) }
          })
              }
              makeRequest("sendOfflineActions", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<OfflineActionError>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<OfflineActionError>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getPpuAuthorization(placeId: String, cpf: String, minval: Int, flag: Int?, callback: (error: Error?, pPUAuthorization: PPUAuthorization?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("cpf", cpf)
    put("minval", minval)
              }
              makeRequest("getPpuAuthorization", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), PPUAuthorization::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getAppPostPayment(placeId: String, cpf: String, billValue: Int, flag: Int?, callback: (error: Error?, pPUAuthorization: PPUAuthorization?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("cpf", cpf)
    put("billValue", billValue)
              }
              makeRequest("getAppPostPayment", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), PPUAuthorization::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getOfflinePrefixId(flag: Int?, callback: (error: Error?, value: String?) -> Unit) {
              makeRequest("getOfflinePrefixId", null, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = json?.getString("result")
                   callback(null, response)
                  }
              })
         }
         override fun makeCardTransactionOffline(placeId: String, payment: ZigPayment, flag: Int?, callback: (error: Error?, zigMachineAnswer: ZigMachineAnswer?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("payment", gson.toJson(payment))
              }
              makeRequest("makeCardTransactionOffline", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), ZigMachineAnswer::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getAvailableEntrancesOffline(placeId: String, userCpf: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("userCpf", userCpf)
              }
              makeRequest("getAvailableEntrancesOffline", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getOfflineUserStateByUid(zigTagUid: String, placeId: String, flag: Int?, callback: (error: Error?, offlineUserState: OfflineUserState??) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("zigTagUid", zigTagUid)
    put("placeId", placeId)
              }
              makeRequest("getOfflineUserStateByUid", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), OfflineUserState::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getOfflineUserState(zigTagUid: String, updatedAt: Calendar?, ppuUpdatedAt: Calendar?, cpf: String?, placeId: String, minPpuValue: Int?, flag: Int?, callback: (error: Error?, offlineUserState: OfflineUserState??) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("zigTagUid", zigTagUid)
    put("updatedAt", updatedAt?.let { dateTimeFormat.format(updatedAt) })
    put("ppuUpdatedAt", ppuUpdatedAt?.let { dateTimeFormat.format(ppuUpdatedAt) })
    put("cpf", cpf?.let { cpf })
    put("placeId", placeId)
    put("minPpuValue", minPpuValue?.let { minPpuValue })
              }
              makeRequest("getOfflineUserState", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), OfflineUserState::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getOfflineCurrentEvent(placeId: String, flag: Int?, callback: (error: Error?, mqttEventInfo: MqttEventInfo?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getOfflineCurrentEvent", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), MqttEventInfo::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getCurrentEventDontCreateNew(placeId: String, flag: Int?, callback: (error: Error?, mqttEventInfo: MqttEventInfo??) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getCurrentEventDontCreateNew", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), MqttEventInfo::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun offlineRefundTransaction(request: RefundTransactionRequest, flag: Int?, callback: (error: Error?, refundTransactionResult: RefundTransactionResult?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("request", gson.toJson(request))
              }
              makeRequest("offlineRefundTransaction", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), RefundTransactionResult::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun offlineRefundTransactionMultipleUsers(request: RefundTransactionRequest, flag: Int?, callback: (error: Error?, RefundUser: ArrayList<RefundUser>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("request", gson.toJson(request))
              }
              makeRequest("offlineRefundTransactionMultipleUsers", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<RefundUser>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RefundUser>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun offlineUndoRefundTransaction(result: RefundTransactionResult, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("result", gson.toJson(result))
              }
              makeRequest("offlineUndoRefundTransaction", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun tryToUsePromotion(placeId: String, zigTagUid: String, cpf: String, products: ArrayList<SellingProduct>, userCredit: Int, flag: Int?, callback: (error: Error?, UsedPromotion: ArrayList<UsedPromotion>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("zigTagUid", zigTagUid)
    put("cpf", cpf)
    put("products", JSONArray().apply { 
            products.forEach { item -> put(gson.toJson(item)) }
          })
    put("userCredit", userCredit)
              }
              makeRequest("tryToUsePromotion", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<UsedPromotion>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UsedPromotion>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun undoUsedPromotion(promotionId: String, productId: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("promotionId", promotionId)
    put("productId", productId)
              }
              makeRequest("undoUsedPromotion", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun authorizeCreditForUsers(placeId: String, users: ArrayList<CreditAsker>, flag: Int?, callback: (error: Error?, UserWithPpu: ArrayList<UserWithPpu>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("users", JSONArray().apply { 
            users.forEach { item -> put(gson.toJson(item)) }
          })
              }
              makeRequest("authorizeCreditForUsers", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<UserWithPpu>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UserWithPpu>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun sendCsv(csv: ByteArray, email: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("csv", Base64.encodeToString(csv, Base64.DEFAULT))
    put("email", email)
              }
              makeRequest("sendCsv", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun removeTipFromPrePaid(placeId: String, cpf: String, flag: Int?, callback: (error: Error?, refundTip: RefundTip?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("cpf", cpf)
              }
              makeRequest("removeTipFromPrePaid", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), RefundTip::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun removeTipFromPrePaidAlternative(placeId: String, cpf: String, flag: Int?, callback: (error: Error?, refundTip: RefundTip?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("cpf", cpf)
              }
              makeRequest("removeTipFromPrePaidAlternative", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), RefundTip::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getOldEventsBill(placeId: String, cpf: String, flag: Int?, callback: (error: Error?, oldEventBill: OldEventBill?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("cpf", cpf)
              }
              makeRequest("getOldEventsBill", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), OldEventBill::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun tryToStartRefundPrePaidTransaction(request: RefundTransactionRequest, flag: Int?, callback: (error: Error?, RefundUser: ArrayList<RefundUser>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("request", gson.toJson(request))
              }
              makeRequest("tryToStartRefundPrePaidTransaction", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<RefundUser>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RefundUser>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun finishActivityAtEvent(eventId: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
              }
              makeRequest("finishActivityAtEvent", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun checkIn(user: String, place: String, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("user", user)
    put("place", place)
              }
              makeRequest("checkIn", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), User::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun checkOut(user: String, place: String, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("user", user)
    put("place", place)
              }
              makeRequest("checkOut", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), User::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun checkZigIn(authorization: String, place: String, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("place", place)
              }
              makeRequest("checkZigIn", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), User::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun checkZigOut(authorization: String, place: String, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("place", place)
              }
              makeRequest("checkZigOut", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), User::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun checkZigOutAndDetachZig(authorization: String, place: String, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("place", place)
              }
              makeRequest("checkZigOutAndDetachZig", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), User::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun sellEntrancesWithZigMachine(placeId: String, userId: String, entranceIds: ArrayList<String>, payments: ArrayList<PostPayment>, flag: Int?, callback: (error: Error?, userState: UserState?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("userId", userId)
    put("entranceIds", JSONArray().apply { 
            entranceIds.forEach { item -> put(item) }
          })
    put("payments", JSONArray().apply { 
            payments.forEach { item -> put(gson.toJson(item)) }
          })
              }
              makeRequest("sellEntrancesWithZigMachine", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), UserState::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun sellEntrancesWithStoneZigMachine(placeId: String, userId: String, entranceIds: ArrayList<String>, payments: ArrayList<StonePostPayment>, flag: Int?, callback: (error: Error?, userState: UserState?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("userId", userId)
    put("entranceIds", JSONArray().apply { 
            entranceIds.forEach { item -> put(item) }
          })
    put("payments", JSONArray().apply { 
            payments.forEach { item -> put(gson.toJson(item)) }
          })
              }
              makeRequest("sellEntrancesWithStoneZigMachine", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), UserState::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getAvaiableEntrances(placeId: String, flag: Int?, callback: (error: Error?, Entrance: ArrayList<Entrance>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getAvaiableEntrances", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Entrance>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Entrance>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun sellEntrances(placeId: String, userId: String, entranceIds: ArrayList<String>, payments: ArrayList<Payment>, flag: Int?, callback: (error: Error?, userState: UserState?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("userId", userId)
    put("entranceIds", JSONArray().apply { 
            entranceIds.forEach { item -> put(item) }
          })
    put("payments", JSONArray().apply { 
            payments.forEach { item -> put(gson.toJson(item)) }
          })
              }
              makeRequest("sellEntrances", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), UserState::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun checkIfEntrancePaid(placeId: String, authorization: String, flag: Int?, callback: (error: Error?, entranceList: EntranceList?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("authorization", authorization)
              }
              makeRequest("checkIfEntrancePaid", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), EntranceList::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun issueInvoicesForUser(eventId: String, userCpf: String, cpf: String?, cnpj: String?, flag: Int?, callback: (error: Error?, issueResult: IssueResult?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("userCpf", userCpf)
    put("cpf", cpf?.let { cpf })
    put("cnpj", cnpj?.let { cnpj })
              }
              makeRequest("issueInvoicesForUser", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), IssueResult::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getInvoicesFromUser(eventId: String, userCpf: String, flag: Int?, callback: (error: Error?, Invoice: ArrayList<Invoice>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("eventId", eventId)
    put("userCpf", userCpf)
              }
              makeRequest("getInvoicesFromUser", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Invoice>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Invoice>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun printInvoice(invoiceId: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("invoiceId", invoiceId)
              }
              makeRequest("printInvoice", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun hasPrinter(invoiceId: String, flag: Int?, callback: (error: Error?, boolean: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("invoiceId", invoiceId)
              }
              makeRequest("hasPrinter", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = json?.getBoolean("result")
                   callback(null, response)
                  }
              })
         }
         override fun logIn(username: String, password: String, flag: Int?, callback: (error: Error?, employee: Employee?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("username", username)
    put("password", password)
              }
              makeRequest("logIn", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), Employee::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun logOut(flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              makeRequest("logOut", null, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getCurrentEmployee(flag: Int?, callback: (error: Error?, employee: Employee?) -> Unit) {
              makeRequest("getCurrentEmployee", null, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), Employee::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun logAcquirerError(acquirer: String, date: Calendar, placeId: String, eventId: String, cpf: String, error: String, transactionJson: String?, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("acquirer", acquirer)
    put("date", dateTimeFormat.format(date))
    put("placeId", placeId)
    put("eventId", eventId)
    put("cpf", cpf)
    put("error", error)
    put("transactionJson", transactionJson?.let { transactionJson })
              }
              makeRequest("logAcquirerError", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun rechargeZig(authorization: String, recharge: Recharge, place: String, paymentMethod: PostMethod, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("recharge", gson.toJson(recharge))
    put("place", place)
    put("paymentMethod", paymentMethod.name)
              }
              makeRequest("rechargeZig", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun rechargeZigWithReason(authorization: String, recharge: Recharge, place: String, paymentMethod: PostMethod, reason: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("recharge", gson.toJson(recharge))
    put("place", place)
    put("paymentMethod", paymentMethod.name)
    put("reason", reason)
              }
              makeRequest("rechargeZigWithReason", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun rechargeUser(userId: String, zigId: String, recharge: Recharge, place: String, paymentMethod: PostMethod, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("userId", userId)
    put("zigId", zigId)
    put("recharge", gson.toJson(recharge))
    put("place", place)
    put("paymentMethod", paymentMethod.name)
              }
              makeRequest("rechargeUser", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun rechargeWithMPos(placeId: String, authorization: String, payment: ZigPayment, flag: Int?, callback: (error: Error?, zigMachineAnswer: ZigMachineAnswer?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("authorization", authorization)
    put("payment", gson.toJson(payment))
              }
              makeRequest("rechargeWithMPos", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), ZigMachineAnswer::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun rechargeWithStoneMpos(placeId: String, authorization: String, stoneTransactionJson: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("authorization", authorization)
    put("stoneTransactionJson", stoneTransactionJson)
              }
              makeRequest("rechargeWithStoneMpos", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getRechargeOfflineBackup(placeId: String, flag: Int?, callback: (error: Error?, RechargeBackup: ArrayList<RechargeBackup>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getRechargeOfflineBackup", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<RechargeBackup>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RechargeBackup>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getPlaceRelations(placeId: String, flag: Int?, callback: (error: Error?, PlaceRelation: ArrayList<PlaceRelation>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getPlaceRelations", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<PlaceRelation>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PlaceRelation>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getMyBarReport(flag: Int?, callback: (error: Error?, barProductReport: BarProductReport?) -> Unit) {
              makeRequest("getMyBarReport", null, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), BarProductReport::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getMyBarSellerReport(flag: Int?, callback: (error: Error?, barSellerReport: BarSellerReport?) -> Unit) {
              makeRequest("getMyBarSellerReport", null, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), BarSellerReport::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun sell(products: ArrayList<SellingProduct>, tip: Int, placeId: String, buyers: ArrayList<Buyer>, obs: String?, flag: Int?, callback: (error: Error?, sellResult: SellResult?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("products", JSONArray().apply { 
            products.forEach { item -> put(gson.toJson(item)) }
          })
    put("tip", tip)
    put("placeId", placeId)
    put("buyers", JSONArray().apply { 
            buyers.forEach { item -> put(gson.toJson(item)) }
          })
    put("obs", obs?.let { obs })
              }
              makeRequest("sell", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), SellResult::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun groupedSell(products: ArrayList<SellingProduct>, tip: Int, placeId: String, buyers: ArrayList<Buyer>, obs: String?, flag: Int?, callback: (error: Error?, sellResult: SellResult?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("products", JSONArray().apply { 
            products.forEach { item -> put(gson.toJson(item)) }
          })
    put("tip", tip)
    put("placeId", placeId)
    put("buyers", JSONArray().apply { 
            buyers.forEach { item -> put(gson.toJson(item)) }
          })
    put("obs", obs?.let { obs })
              }
              makeRequest("groupedSell", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), SellResult::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun finishGroupedSell(transactionIds: ArrayList<String>, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("transactionIds", JSONArray().apply { 
            transactionIds.forEach { item -> put(item) }
          })
              }
              makeRequest("finishGroupedSell", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun finishGroupedSellBec(transactionIds: ArrayList<String>, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("transactionIds", JSONArray().apply { 
            transactionIds.forEach { item -> put(item) }
          })
              }
              makeRequest("finishGroupedSellBec", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getProducts(place: String, flag: Int?, callback: (error: Error?, Product: ArrayList<Product>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("place", place)
              }
              makeRequest("getProducts", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Product>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Product>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getCombos(place: String, flag: Int?, callback: (error: Error?, Combo: ArrayList<Combo>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("place", place)
              }
              makeRequest("getCombos", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Combo>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Combo>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getSellableThings(place: String, flag: Int?, callback: (error: Error?, sellableThings: SellableThings?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("place", place)
              }
              makeRequest("getSellableThings", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), SellableThings::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getSellableThings2(placeId: String, flag: Int?, callback: (error: Error?, sellableThings2: SellableThings2?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getSellableThings2", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), SellableThings2::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getAllProducts(placeId: String, flag: Int?, callback: (error: Error?, CompleteProduct: ArrayList<CompleteProduct>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getAllProducts", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<CompleteProduct>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<CompleteProduct>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun removeStock(placeId: String, authorization: String, deliverId: String, productId: String, quantity: Int, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("authorization", authorization)
    put("deliverId", deliverId)
    put("productId", productId)
    put("quantity", quantity)
              }
              makeRequest("removeStock", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun addStock(placeId: String, authorization: String, receiverId: String, productId: String, quantity: Int, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("authorization", authorization)
    put("receiverId", receiverId)
    put("productId", productId)
    put("quantity", quantity)
              }
              makeRequest("addStock", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getStockTransfers(placeId: String, flag: Int?, callback: (error: Error?, ProductTransfer: ArrayList<ProductTransfer>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getStockTransfers", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<ProductTransfer>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductTransfer>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getSoldTickets(placeId: String, cpf: String, name: String, flag: Int?, callback: (error: Error?, UserTicket: ArrayList<UserTicket>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("cpf", cpf)
    put("name", name)
              }
              makeRequest("getSoldTickets", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<UserTicket>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<UserTicket>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getSellableTickets(placeId: String, flag: Int?, callback: (error: Error?, Ticket: ArrayList<Ticket>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getSellableTickets", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Ticket>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Ticket>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getTickets(placeId: String, authorization: String, flag: Int?, callback: (error: Error?, Ticket: ArrayList<Ticket>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("authorization", authorization)
              }
              makeRequest("getTickets", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Ticket>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Ticket>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getLastTransactions(limit: Int, place: String, flag: Int?, callback: (error: Error?, SellerTransaction: ArrayList<SellerTransaction>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("limit", limit)
    put("place", place)
              }
              makeRequest("getLastTransactions", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<SellerTransaction>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<SellerTransaction>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getCashierClosing(placeId: String, flag: Int?, callback: (error: Error?, cashierClosing: CashierClosing?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getCashierClosing", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), CashierClosing::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getReserves(placeId: String, flag: Int?, callback: (error: Error?, Reserve: ArrayList<Reserve>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getReserves", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Reserve>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Reserve>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun addObsToReserve(reserveId: String, obs: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("reserveId", reserveId)
    put("obs", obs)
              }
              makeRequest("addObsToReserve", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getReservesAndPromotions(placeId: String, flag: Int?, callback: (error: Error?, reserveAndPromotion: ReserveAndPromotion?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getReservesAndPromotions", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), ReserveAndPromotion::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getDeliveryProducts(type: String, uid: String, barId: String, placeId: String, flag: Int?, callback: (error: Error?, DeliveryTransaction: ArrayList<DeliveryTransaction>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("type", type)
    put("uid", uid)
    put("barId", barId)
    put("placeId", placeId)
              }
              makeRequest("getDeliveryProducts", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<DeliveryTransaction>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<DeliveryTransaction>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun deliveryProducts(deliveryInstances: ArrayList<DeliveryInstance>, placeId: String, barId: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("deliveryInstances", JSONArray().apply { 
            deliveryInstances.forEach { item -> put(gson.toJson(item)) }
          })
    put("placeId", placeId)
    put("barId", barId)
              }
              makeRequest("deliveryProducts", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun deliverProducts(instanceIds: ArrayList<String>, placeId: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("instanceIds", JSONArray().apply { 
            instanceIds.forEach { item -> put(item) }
          })
    put("placeId", placeId)
              }
              makeRequest("deliverProducts", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getDeliveryBarProducts(placeId: String, flag: Int?, callback: (error: Error?, DeliveryTransaction: ArrayList<DeliveryTransaction>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getDeliveryBarProducts", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<DeliveryTransaction>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<DeliveryTransaction>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getMyDeliveries(placeId: String, flag: Int?, callback: (error: Error?, DeliveryTransaction: ArrayList<DeliveryTransaction>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getMyDeliveries", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<DeliveryTransaction>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<DeliveryTransaction>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun removeFromDelivery(transactionId: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("transactionId", transactionId)
              }
              makeRequest("removeFromDelivery", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getProductsToBeDelivered(placeId: String, flag: Int?, callback: (error: Error?, ProductsToDelivery: ArrayList<ProductsToDelivery>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getProductsToBeDelivered", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<ProductsToDelivery>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<ProductsToDelivery>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getTransactionPrintout(transactionId: String, flag: Int?, callback: (error: Error?, value: ByteArray?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("transactionId", transactionId)
              }
              makeRequest("getTransactionPrintout", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = Base64.decode(json?.getString("result"), Base64.DEFAULT)
                   callback(null, response)
                  }
              })
         }
         override fun getBarTransactionPrintout(transactionId: String, mandatory: Boolean, flag: Int?, callback: (error: Error?, value: ByteArray?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("transactionId", transactionId)
    put("mandatory", mandatory)
              }
              makeRequest("getBarTransactionPrintout", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = Base64.decode(json?.getString("result"), Base64.DEFAULT)
                   callback(null, response)
                  }
              })
         }
         override fun alertUserThatProductIsReady(transactionId: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("transactionId", transactionId)
              }
              makeRequest("alertUserThatProductIsReady", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun deliverProductsWithPush(deliveryInstances: ArrayList<DeliveryInstance>, placeId: String, barId: String, sendPush: Boolean, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("deliveryInstances", JSONArray().apply { 
            deliveryInstances.forEach { item -> put(gson.toJson(item)) }
          })
    put("placeId", placeId)
    put("barId", barId)
    put("sendPush", sendPush)
              }
              makeRequest("deliverProductsWithPush", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getPostPaidOwnedValue(authorization: String, placeId: String, flag: Int?, callback: (error: Error?, postPaidOwnedValue: PostPaidOwnedValue?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("placeId", placeId)
              }
              makeRequest("getPostPaidOwnedValue", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), PostPaidOwnedValue::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun payMultipleUserPostPaidTransactionsWithTipAndZigMachine(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payments: ArrayList<PostPayment>, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("mainUserAuthId", mainUserAuthId)
    put("otherAuths", JSONArray().apply { 
            otherAuths.forEach { item -> put(item) }
          })
    put("transactionIds", JSONArray().apply { 
            transactionIds.forEach { item -> put(item) }
          })
    put("placeId", placeId)
    put("payments", JSONArray().apply { 
            payments.forEach { item -> put(gson.toJson(item)) }
          })
              }
              makeRequest("payMultipleUserPostPaidTransactionsWithTipAndZigMachine", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun payMultipleUserPostPaidPartialTransactions(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payments: ArrayList<PostPayment>, flag: Int?, callback: (error: Error?, partialPayReturn: PartialPayReturn?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("mainUserAuthId", mainUserAuthId)
    put("otherAuths", JSONArray().apply { 
            otherAuths.forEach { item -> put(item) }
          })
    put("transactionIds", JSONArray().apply { 
            transactionIds.forEach { item -> put(item) }
          })
    put("placeId", placeId)
    put("payments", JSONArray().apply { 
            payments.forEach { item -> put(gson.toJson(item)) }
          })
              }
              makeRequest("payMultipleUserPostPaidPartialTransactions", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), PartialPayReturn::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun makeCardTransactionAndPayPartialTransactions(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payment: ZigPayment, flag: Int?, callback: (error: Error?, zigMachinePayment: ZigMachinePayment?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("mainUserAuthId", mainUserAuthId)
    put("otherAuths", JSONArray().apply { 
            otherAuths.forEach { item -> put(item) }
          })
    put("transactionIds", JSONArray().apply { 
            transactionIds.forEach { item -> put(item) }
          })
    put("placeId", placeId)
    put("payment", gson.toJson(payment))
              }
              makeRequest("makeCardTransactionAndPayPartialTransactions", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), ZigMachinePayment::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun payMultipleUserPostPaidPartialTransactionsStone(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payments: ArrayList<StonePostPayment>, flag: Int?, callback: (error: Error?, partialPayReturn: PartialPayReturn?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("mainUserAuthId", mainUserAuthId)
    put("otherAuths", JSONArray().apply { 
            otherAuths.forEach { item -> put(item) }
          })
    put("transactionIds", JSONArray().apply { 
            transactionIds.forEach { item -> put(item) }
          })
    put("placeId", placeId)
    put("payments", JSONArray().apply { 
            payments.forEach { item -> put(gson.toJson(item)) }
          })
              }
              makeRequest("payMultipleUserPostPaidPartialTransactionsStone", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), PartialPayReturn::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getUserTransactions(authorization: String, place: String, flag: Int?, callback: (error: Error?, Transaction: ArrayList<Transaction>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("place", place)
              }
              makeRequest("getUserTransactions", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Transaction>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Transaction>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getUserPostPaidPayments(authorization: String, place: String, flag: Int?, callback: (error: Error?, PostPaidTransaction: ArrayList<PostPaidTransaction>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("place", place)
              }
              makeRequest("getUserPostPaidPayments", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<PostPaidTransaction>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<PostPaidTransaction>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun payMultipleUserPostPaidTransactions(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payments: ArrayList<Payment>, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("mainUserAuthId", mainUserAuthId)
    put("otherAuths", JSONArray().apply { 
            otherAuths.forEach { item -> put(item) }
          })
    put("transactionIds", JSONArray().apply { 
            transactionIds.forEach { item -> put(item) }
          })
    put("placeId", placeId)
    put("payments", JSONArray().apply { 
            payments.forEach { item -> put(gson.toJson(item)) }
          })
              }
              makeRequest("payMultipleUserPostPaidTransactions", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun payMultipleUserPostPaidTransactionsWithTip(mainUserAuthId: String, otherAuths: ArrayList<String>, transactionIds: ArrayList<String>, placeId: String, payments: ArrayList<Payment>, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("mainUserAuthId", mainUserAuthId)
    put("otherAuths", JSONArray().apply { 
            otherAuths.forEach { item -> put(item) }
          })
    put("transactionIds", JSONArray().apply { 
            transactionIds.forEach { item -> put(item) }
          })
    put("placeId", placeId)
    put("payments", JSONArray().apply { 
            payments.forEach { item -> put(gson.toJson(item)) }
          })
              }
              makeRequest("payMultipleUserPostPaidTransactionsWithTip", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getOpenedBillsBackup(placeId: String, flag: Int?, callback: (error: Error?, OpenedBillBackup: ArrayList<OpenedBillBackup>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getOpenedBillsBackup", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<OpenedBillBackup>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<OpenedBillBackup>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun refundTransaction(authorization: String, transaction: String, place: String, obs: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("transaction", transaction)
    put("place", place)
    put("obs", obs)
              }
              makeRequest("refundTransaction", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun refundTransactionWithLogin(login: String, password: String, transactionId: String, placeId: String, obs: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("login", login)
    put("password", password)
    put("transactionId", transactionId)
    put("placeId", placeId)
    put("obs", obs)
              }
              makeRequest("refundTransactionWithLogin", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun refundTransactionProduct(authorization: String, transactionId: String, placeId: String, productId: String, count: Int, obs: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("transactionId", transactionId)
    put("placeId", placeId)
    put("productId", productId)
    put("count", count)
    put("obs", obs)
              }
              makeRequest("refundTransactionProduct", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun cancelTransaction(authorization: String, transaction: String, place: String, obs: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("transaction", transaction)
    put("place", place)
    put("obs", obs)
              }
              makeRequest("cancelTransaction", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun cancelTransactionProduct(authorization: String, transactionId: String, placeId: String, productId: String, count: Int, obs: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("transactionId", transactionId)
    put("placeId", placeId)
    put("productId", productId)
    put("count", count)
    put("obs", obs)
              }
              makeRequest("cancelTransactionProduct", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun refundRecharge(authorization: String, rechargeId: String, obs: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("rechargeId", rechargeId)
    put("obs", obs)
              }
              makeRequest("refundRecharge", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun consumeRecharge(placeId: String, authorization: String, rechargeId: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("authorization", authorization)
    put("rechargeId", rechargeId)
              }
              makeRequest("consumeRecharge", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun refundMachineTransaction(machineTransactionId: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("machineTransactionId", machineTransactionId)
              }
              makeRequest("refundMachineTransaction", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getProductsForRefundActivation(placeId: String, flag: Int?, callback: (error: Error?, RefundActivationProduct: ArrayList<RefundActivationProduct>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getProductsForRefundActivation", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<RefundActivationProduct>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<RefundActivationProduct>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun createUser(setup: UserSetup, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("setup", gson.toJson(setup))
              }
              makeRequest("createUser", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), User::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun createUserWithAvatar(setup: UserSetup, avatar: ByteArray?, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("setup", gson.toJson(setup))
    put("avatar", avatar?.let { Base64.encodeToString(avatar, Base64.DEFAULT) })
              }
              makeRequest("createUserWithAvatar", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), User::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getUser(cpf: String, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
              }
              makeRequest("getUser", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), User::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getUserWithCpfData(cpf: String, flag: Int?, callback: (error: Error?, cpfDataOrUser: CpfDataOrUser?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
              }
              makeRequest("getUserWithCpfData", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), CpfDataOrUser::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getUserWithForeign(cpf: String, foreign: Boolean, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
    put("foreign", foreign)
              }
              makeRequest("getUserWithForeign", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), User::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getClientZigLine(authorization: String, placeId: String, flag: Int?, callback: (error: Error?, Action: ArrayList<Action>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("placeId", placeId)
              }
              makeRequest("getClientZigLine", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Action>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Action>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getClientZigLineByCpf(cpf: String, placeId: String, flag: Int?, callback: (error: Error?, Action: ArrayList<Action>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
    put("placeId", placeId)
              }
              makeRequest("getClientZigLineByCpf", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Action>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Action>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getClientZigLineByCpfFromDate(cpf: String, placeId: String, from: Calendar, flag: Int?, callback: (error: Error?, Action: ArrayList<Action>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
    put("placeId", placeId)
    put("from", dateTimeFormat.format(from))
              }
              makeRequest("getClientZigLineByCpfFromDate", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<Action>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<Action>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getClientHistoryInPlace(authorization: String, placeId: String, flag: Int?, callback: (error: Error?, HistoryAction: ArrayList<HistoryAction>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("placeId", placeId)
              }
              makeRequest("getClientHistoryInPlace", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<HistoryAction>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<HistoryAction>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getClientHistoryInPlaceByCpf(cpf: String, placeId: String, flag: Int?, callback: (error: Error?, HistoryAction: ArrayList<HistoryAction>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
    put("placeId", placeId)
              }
              makeRequest("getClientHistoryInPlaceByCpf", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<HistoryAction>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<HistoryAction>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getClientHistoryInPlaceByCpfFromDate(cpf: String, placeId: String, from: Calendar, flag: Int?, callback: (error: Error?, HistoryAction: ArrayList<HistoryAction>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
    put("placeId", placeId)
    put("from", dateTimeFormat.format(from))
              }
              makeRequest("getClientHistoryInPlaceByCpfFromDate", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<HistoryAction>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<HistoryAction>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun getAge(cpf: String, placeId: String, flag: Int?, callback: (error: Error?, datetime: Calendar??) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
    put("placeId", placeId)
              }
              makeRequest("getAge", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = Calendar.getInstance().apply { time = dateTimeFormat.parse(json?.getString("result")) }
                   callback(null, response)
                  }
              })
         }
         override fun increaseLimit(cpf: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
              }
              makeRequest("increaseLimit", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun userState(cpf: String, place: String, flag: Int?, callback: (error: Error?, userState: UserState?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
    put("place", place)
              }
              makeRequest("userState", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), UserState::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getAllUpdatedUsers(placeId: String, since: Calendar, flag: Int?, callback: (error: Error?, String: ArrayList<String>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("since", dateTimeFormat.format(since))
              }
              makeRequest("getAllUpdatedUsers", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<String>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<String>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun makeCardTransaction(placeId: String, authorization: String, payment: ZigPayment, flag: Int?, callback: (error: Error?, zigMachineAnswer: ZigMachineAnswer?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("authorization", authorization)
    put("payment", gson.toJson(payment))
              }
              makeRequest("makeCardTransaction", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), ZigMachineAnswer::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun cancelStoneTransaction(id: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("id", id)
              }
              makeRequest("cancelStoneTransaction", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun sendAllNotUsedMachineTransactions(ids: ArrayList<String>, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("ids", JSONArray().apply { 
            ids.forEach { item -> put(item) }
          })
              }
              makeRequest("sendAllNotUsedMachineTransactions", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun startCipurseRead(uid: String, keyRandom: String, challengeRandom: String, secret: String, flag: Int?, callback: (error: Error?, cipurseChallenge: CipurseChallenge?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("uid", uid)
    put("keyRandom", keyRandom)
    put("challengeRandom", challengeRandom)
    put("secret", secret)
              }
              makeRequest("startCipurseRead", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), CipurseChallenge::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun startDesfireRead(uid: String, encryptedRandom: String, secret: String, flag: Int?, callback: (error: Error?, desfireChallenge: DesfireChallenge?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("uid", uid)
    put("encryptedRandom", encryptedRandom)
    put("secret", secret)
              }
              makeRequest("startDesfireRead", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), DesfireChallenge::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getZig(authorization: String, place: String, flag: Int?, callback: (error: Error?, zigDevice: ZigDevice?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
    put("place", place)
              }
              makeRequest("getZig", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), ZigDevice::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun blockZig(cpf: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
              }
              makeRequest("blockZig", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun detachZig(authorization: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("authorization", authorization)
              }
              makeRequest("detachZig", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun setupZig(user: String, zigId: String, place: String, flag: Int?, callback: (error: Error?, zigDevice: ZigDevice?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("user", user)
    put("zigId", zigId)
    put("place", place)
              }
              makeRequest("setupZig", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), ZigDevice::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun setupZigAuth(user: String, authorization: String, place: String, flag: Int?, callback: (error: Error?, zigDevice: ZigDevice?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("user", user)
    put("authorization", authorization)
    put("place", place)
              }
              makeRequest("setupZigAuth", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), ZigDevice::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun setupZigAuthAndRelation(user: String, authorization: String, place: String, flag: Int?, callback: (error: Error?, zigAndRelation: ZigAndRelation?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("user", user)
    put("authorization", authorization)
    put("place", place)
              }
              makeRequest("setupZigAuthAndRelation", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), ZigAndRelation::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun getBlockedZigUids(placeId: String, flag: Int?, callback: (error: Error?, String: ArrayList<String>?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
              }
              makeRequest("getBlockedZigUids", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson<ArrayList<String>>(json?.getJSONArray("result")?.toString(), object : TypeToken<ArrayList<String>>() { }.type) 
                   callback(null, response)
                  }
              })
         }
         override fun enrollFace(cpf: String, _data: ByteArray, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
    put("data", Base64.encodeToString(_data, Base64.DEFAULT))
              }
              makeRequest("enrollFace", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun startFaceBiometry(placeId: String, _data: ByteArray, flag: Int?, callback: (error: Error?, user: User?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("placeId", placeId)
    put("data", Base64.encodeToString(_data, Base64.DEFAULT))
              }
              makeRequest("startFaceBiometry", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = gson.fromJson(json?.getJSONObject("result")?.toString(), User::class.java)
                   callback(null, response)
                  }
              })
         }
         override fun sleep(ms: Int, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("ms", ms)
              }
              makeRequest("sleep", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun getKey(uid: String, secret: String, flag: Int?, callback: (error: Error?, value: String?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("uid", uid)
    put("secret", secret)
              }
              makeRequest("getKey", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = json?.getString("result")
                   callback(null, response)
                  }
              })
         }
         override fun donate(cpf: String, email: String?, stoneJson: String, flag: Int?, callback: (error: Error?, result: Boolean?) -> Unit) {
              val bodyArgs = JSONObject().apply {
                  put("cpf", cpf)
    put("email", email?.let { email })
    put("stoneJson", stoneJson)
              }
              makeRequest("donate", bodyArgs, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = null
                   callback(null, response)
                  }
              })
         }
         override fun ping(flag: Int?, callback: (error: Error?, value: String?) -> Unit) {
              makeRequest("ping", null, { error, json -> 
                  if (error != null) {
                      callback(error, null)
                  } else {
        val response = json?.getString("result")
                   callback(null, response)
                  }
              })
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
                   callback(null, response)
                  }
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

        private inline fun makeRequest(functionName: String, bodyArgs: JSONObject?, crossinline callback: (error: Error?, result: JSONObject?) -> Unit, timeoutSeconds: Int = 15) {
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