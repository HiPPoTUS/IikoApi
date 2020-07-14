package com.example.iikoapi.startapp.networking

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.iikoapi.general.GeneralActivity
import com.example.iikoapi.startapp.DialogFragmentErrorSavedata
import com.example.iikoapi.startapp.datatype.Login
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.IOException
import java.io.OutputStreamWriter
import java.security.AccessController.getContext
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*


lateinit var menu: MenuResponse
lateinit var restr: DeliveryRestrictionsResponse
class NetworkInteraction(val S: MySingleton, val context: Context, val progressBar: ProgressBar) {

    private val url = "https://iiko.biz:9900/api/0/"
    private var login: Login =
        Login(

            null
        )
    lateinit var orgs: OrgsResponse

    fun start() {
        val link = "auth/access_token?user_id=${login.user_id}&user_secret=${login.user_secret}"
        val Request = StringRequest(
            Request.Method.GET, url+link,
            Response.Listener { response ->
                login.access= response.filterNot {
                    it=='"'
                }
                Log.d("login access", login.toString())
                getOrgsList()
            },
            Response.ErrorListener { error ->
                Log.d("tag", "volley arror: $error")
                DialogFragmentErrorSavedata(context, isInternetAvailable(context), progressBar).show((context as AppCompatActivity).supportFragmentManager, "tag")
            }
        )
        S.addToRequestQueue(Request)
    }

    fun getOrgsList()
    {
        val mapper  = ObjectMapper().registerModule(KotlinModule(nullIsSameAsDefault=true))
        val link = "organization/list?access_token=${login.access}"
        val Request = StringRequest(
            Request.Method.GET, url+link,
            Response.Listener { response ->
                this.orgs = mapper.readValue(" {\"organisations\": $response}", OrgsResponse::class.java)
                Log.d("orgs",orgs.toString())
                getRestr(orgs.organisations.last().id)
                getMenu(orgs.organisations.last().id)
            },
            Response.ErrorListener { error ->
                Log.d("tag", "volley arror: $error")
                DialogFragmentErrorSavedata(context, isInternetAvailable(context), progressBar).show((context as AppCompatActivity).supportFragmentManager, "tag")
            }
        )
        S.addToRequestQueue(Request)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    fun getRestr(OrgID:String)
    {
        val mapper  = ObjectMapper().registerModule(KotlinModule(nullIsSameAsDefault=true))
//        val link = "/regions/regions?access_token=${login.access}&organization=${OrgID}"
        val link = "deliverySettings/getDeliveryRestrictions?access_token=${login.access}&organization=${OrgID}"
        val Request = StringRequest(
            Request.Method.GET, url+link,
            Response.Listener { response ->
                restr = mapper.readValue(response, DeliveryRestrictionsResponse::class.java)
                Log.d("restr",response.toString())
            },
            Response.ErrorListener { error ->
                Log.d("tag", "volley arror: $error")
                DialogFragmentErrorSavedata(context, isInternetAvailable(context), progressBar).show((context as AppCompatActivity).supportFragmentManager, "tag")
            }
        )
        S.addToRequestQueue(Request)
    }

    private fun writeToFile(
        data: String,
        context: Context
    ) {
        try {
            val outputStreamWriter = OutputStreamWriter(
                context.openFileOutput(
                    "config2.txt",
                    Context.MODE_PRIVATE
                )
            )
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    fun getMenu(OrgID:String)
    {
        val mapper  = ObjectMapper().registerModule(KotlinModule(nullIsSameAsDefault=true))

        val link = "nomenclature/$OrgID?access_token=${login.access}"
        val Request = StringRequest(
            Request.Method.GET, url+link,
            Response.Listener { response ->
//                this.menu = mapper.readValue(response)
                menu = mapper.readValue(response)
                context.startActivity(Intent(context, GeneralActivity::class.java))
            },
            Response.ErrorListener { error ->
                Log.d("tag", "volley arror: $error")
                DialogFragmentErrorSavedata(context, isInternetAvailable(context), progressBar).show((context as AppCompatActivity).supportFragmentManager, "tag")
            }

        )
        S.addToRequestQueue(Request)
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

class NukeSSLCerts {
    internal val TAG = "NukeSSLCerts"
    fun nuke() {
        try {
            val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                object : X509TrustManager {
                    val acceptedIssuers: Array<Any?>
                        get() = arrayOfNulls(0)

                    override fun checkClientTrusted(
                        certs: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun checkServerTrusted(
                        certs: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        TODO("Not yet implemented")
                    }
                }
            )
            val sc: SSLContext = SSLContext.getInstance("SSL")
            sc.init(null, trustAllCerts, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())
            HttpsURLConnection.setDefaultHostnameVerifier(object : HostnameVerifier {
                override fun verify(arg0: String?, arg1: SSLSession?): Boolean {
                    return true
                }
            })
        } catch (e: Exception) {
        }
    }
}