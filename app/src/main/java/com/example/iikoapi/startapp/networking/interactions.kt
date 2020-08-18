package com.example.iikoapi.startapp.networking
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.dodocopy.dataTypes.Address
import com.example.iikoapi.startapp.DialogFragmentErrorSavedata
import com.example.iikoapi.startapp.datatype.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Iiko(context:Context, provider: iiko_NetworkService, pb:ProgressBar?){

    private lateinit var progressBar: ProgressBar
    lateinit var deliveryRestrictionsResponse: DeliveryRestrictionsResponse
    lateinit var menuResponse: MenuResponse
    private val local_context = context
    private val local_provider = provider.iikoApi
    private var token:String? = null
    private var organisationInfo: OrganisationInfo? = null



    fun addProgressBar(pb:ProgressBar){
        progressBar = pb
    }

    fun check_order(order:OrderRequest):Int {
        val call = local_provider.check_order(token, order)
        return call.execute().body()!!.resultState
    }

    fun check_address(addr:Address):Boolean {
        val call = local_provider.check_delivery(token,organisationInfo!!.id, addr)
        return call.execute().body()!!.addressInZone
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

class CP(provider: cp_NetworkService){
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
