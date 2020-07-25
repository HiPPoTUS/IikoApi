package com.example.iikoapi.startapp.networking
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.iikoapi.R
import com.example.iikoapi.startapp.DialogFragmentErrorSavedata
import com.example.iikoapi.startapp.datatype.OrganisationInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition

class Iiko(context:Context, provider: NetworkService, pb:ProgressBar?){

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

    fun authentication() {
        val call = local_provider.auth()!!
        token = call.execute().body()
    }

    fun getOrganisation(position: Int){
        val call = local_provider.organisations(token)!!
        organisationInfo = call.execute().body()!![0]
    }

    fun getRestrictions(){
        val call = local_provider.restrictions(token, organisationInfo!!.id)!!
        call.enqueue(object: Callback<DeliveryRestrictionsResponse?> {
            override fun onFailure(call: Call<DeliveryRestrictionsResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<DeliveryRestrictionsResponse?>, response: Response<DeliveryRestrictionsResponse?>) {
                deliveryRestrictionsResponse = response.body()!!
            }
        })
    }

    fun getMenu(){
        val call = local_provider.menu(organisationInfo!!.id, token)!!
        menuResponse = call.execute().body()!!
        Log.d("menu",menuResponse.revision.toString())
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
