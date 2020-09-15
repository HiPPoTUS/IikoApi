package com.example.iikoapi.startapp.networking
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.dodocopy.dataTypes.Address
import com.example.dodocopy.dataTypes.CityWithStreets
import com.example.iikoapi.startapp.DialogFragmentErrorSavedata
import com.example.iikoapi.startapp.datatype.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Iiko(context:Context, provider: IikoNetworkService, pb:ProgressBar?){

    private lateinit var progressBar: ProgressBar
    lateinit var streets: CityWithStreets
    lateinit var Ptypes: paymentTypesResponse
    lateinit var deliveryRestrictionsResponse: DeliveryRestrictionsResponse
    lateinit var menuResponse: MenuResponse
    private val local_context = context
    private val local_provider = provider.iikoApi
    private var token:String? = null
    var organisationInfo: OrganisationInfo? = null



    fun addProgressBar(pb:ProgressBar){
        progressBar = pb
    }

    fun check_order(order:OrderRequest):OrderChecksCreationResult? {
        val call = local_provider.check_order(token, order)
        Log.d("BODY",call.request().body().toString())
        return call.execute().body()
    }

    fun check_address(addr:Address):AddressCheckResult {
        val call = local_provider.check_delivery(token,organisationInfo!!.id, addr)
        return call.execute().body()!!
    }

    fun authentication() {
        val call = local_provider.auth()!!
        Log.d("token",call.request().url().toString())
        token = call.execute().body()
    }

    fun getOrganisation(position: Int){
        val call = local_provider.organisations(token)!!
        Log.d("orgs",call.request().url().toString())
        organisationInfo = call.execute().body()!![position]
    }
    fun getStreets(){
        val call = local_provider.get_streets(token,organisationInfo!!.id)!!
        streets = call.execute().body()!!.elementAt(0)
//        call.execute(object : Callback<ArrayList<CityWithStreets>?> {
//            override fun onFailure(call: Call<ArrayList<CityWithStreets>?>, t: Throwable) {
//                Log.d("SR",call.request().url().toString())
//            }
//
//            override fun onResponse(
//                call: Call<ArrayList<CityWithStreets>?>,
//                response: Response<ArrayList<CityWithStreets>?>
//            ) {
//                streets = response.body()!!.elementAt(0)
//            }
//        })
    }
    fun getPTypes(){
        val call = local_provider.get_payment_types(token,organisationInfo!!.id)
        Ptypes = call.execute().body()!!
    }

    fun getRestrictions(){
        val call = local_provider.restrictions(token, organisationInfo!!.id)!!
        deliveryRestrictionsResponse = call.execute().body()!!
//        call.enqueue(object: Callback<DeliveryRestrictionsResponse?> {
//            override fun onFailure(call: Call<DeliveryRestrictionsResponse?>, t: Throwable) {
//            }
//
//            override fun onResponse(call: Call<DeliveryRestrictionsResponse?>, response: Response<DeliveryRestrictionsResponse?>) {
//                deliveryRestrictionsResponse = response.body()!!
//            }
//        })
    }

    fun getMenu(){
        val call = local_provider.menu(organisationInfo!!.id, token)!!
        menuResponse = call.execute().body()!!
        Log.d("menu",call.request().url().toString())
    }

    fun bad(){
        DialogFragmentErrorSavedata(local_context, isInternetAvailable(), progressBar).show((local_context as AppCompatActivity).supportFragmentManager, "tag")
    }

    fun isAuth():Boolean = !token.isNullOrEmpty()

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            local_context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }
}

class CP(provider: CpNetworkService){
    private val local_provider = provider.cpApi
    private val CONTENT_TYPE = "application/json"

    fun pay(req:PayRequestArgs):PayApiResponse<Transaction>{
        val call = local_provider.charge(CONTENT_TYPE,req)
        return call.execute().body()!!
    }
    fun post3ds(req: Post3dsRequestArgs):PayApiResponse<Transaction>
    {
        val call = local_provider.post3ds(CONTENT_TYPE,req)
        return call.execute().body()!!
    }
}
