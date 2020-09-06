package com.example.iikoapi.startapp

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.iikoapi.R
import com.example.iikoapi.general.GeneralActivity
import com.example.iikoapi.startapp.networking.*
import com.example.iikoapi.utils.isInternetAvailable
import kotlinx.android.synthetic.main.activity_start.*


lateinit var menu: MenuResponse
lateinit var deliveryRestrictionsResponse: DeliveryRestrictionsResponse

class StartActivity : AppCompatActivity() {

    val iiko = Iiko(localContext = this, provider = IikoNetworkService.instance!!, progressBar = null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        progressBar
        iiko.setProgressBar(progressBar)

        if(isInternetAvailable(this))
            Initialisation(iiko, this, progressBar).execute()
        else
            DialogFragmentError(this, false, progressBar).show(supportFragmentManager, "err")

//        dat(iiko, this, progressBar).execute()

//        thread {   iiko.authentication()
//            iiko.getOrganisation(0)
//            iiko.getMenu()
//            menu = iiko.menuResponse }.join()
//        NukeSSLCerts().nuke()
//        iiko.getOrganisation(0)
//        iiko.getRestrictions("")
//        iiko.getMenu("")
    }
}

class Initialisation(private var iiko : Iiko, private val context: Context, private val progressBar: ProgressBar) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {

        return try {
            iiko.authentication()
            iiko.getOrganisation(0)
            iiko.getMenu()
            menu = iiko.menuResponse
            true
        }catch (e : Exception){
            DialogFragmentError(context, true, progressBar).show((context as AppCompatActivity).supportFragmentManager, "err")
            false
        }
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        if(result)
            context.startActivity(Intent(context, GeneralActivity::class.java))
    }
}

