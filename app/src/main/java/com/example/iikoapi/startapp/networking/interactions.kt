package com.example.iikoapi.startapp.networking
import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.dodocopy.dataTypes.Address
import com.example.iikoapi.startapp.DialogFragmentError
import com.example.iikoapi.startapp.datatype.*
import java.lang.Exception

class Iiko(private val localContext : Context, provider : IikoNetworkService, private var progressBar : ProgressBar?){

    private lateinit var deliveryRestrictionsResponse: DeliveryRestrictionsResponse
    lateinit var menuResponse : MenuResponse
    private val localProvider = provider.iikoApi
    private var token : String? = null
    private var organisationInfo : OrganisationInfo? = null



    fun setProgressBar(pb:ProgressBar){
        this.progressBar = pb
    }

    fun checkOrder(order:OrderRequest):Int {
        val call = localProvider.check_order(token, order)
        return call.execute().body()!!.resultState
    }

    fun checkAddress(address : Address):Boolean {
        val call = localProvider.check_delivery(token,organisationInfo!!.id, address)
        return call.execute().body()!!.addressInZone
    }

    fun authentication() {
        val call = local_provider.auth()!!
        Log.d("token",call.request().url().toString())
        token = call.execute().body()
    }

    fun getOrganisation(position: Int){
        val call = localProvider.organisations(token)!!
        Log.d("orgs",call.request().url().toString())
        organisationInfo = call.execute().body()!![position]
    }

    fun getRestrictions(){
        val call = localProvider.restrictions(token, organisationInfo!!.id)!!
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
        val call = localProvider.menu(organisationInfo!!.id, token)!!
        menuResponse = call.execute().body()!!
        Log.d("menu",call.request().url().toString())
    }

    fun bad(){
        try {
            DialogFragmentError(localContext, true, progressBar!!).show((localContext as AppCompatActivity).supportFragmentManager, "err")
        }catch (e : Exception){}
    }

    fun isAuth():Boolean = !token.isNullOrEmpty()

//    private fun isInternetAvailable(): Boolean {
//        var result = false
//        val connectivityManager =
//            local_context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val networkCapabilities = connectivityManager.activeNetwork ?: return false
//            val actNw =
//                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
//            result = when {
//                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//                else -> false
//            }
//        } else {
//            connectivityManager.run {
//                connectivityManager.activeNetworkInfo?.run {
//                    result = when (type) {
//                        ConnectivityManager.TYPE_WIFI -> true
//                        ConnectivityManager.TYPE_MOBILE -> true
//                        ConnectivityManager.TYPE_ETHERNET -> true
//                        else -> false
//                    }
//                }
//            }
//        }
//        return result
//    }
}

class CP(provider: CpNetworkService){
    private val localProvider = provider.cpApi
    private val CONTENT_TYPE = "application/json"

    fun pay(req:PayRequestArgs):PayApiResponse<Transaction>{
        val call = localProvider.charge(CONTENT_TYPE,req)
        return call.execute().body()!!
    }
    fun post3ds(req: Post3dsRequestArgs):PayApiResponse<Transaction>
    {
        val call = localProvider.post3ds(CONTENT_TYPE,req)
        return call.execute().body()!!
    }
}
