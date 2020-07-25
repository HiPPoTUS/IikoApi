package com.example.iikoapi.startapp

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.iikoapi.R
import com.example.iikoapi.general.GeneralActivity
import com.example.iikoapi.startapp.networking.DeliveryRestrictionsResponse
import com.example.iikoapi.startapp.networking.Iiko
import com.example.iikoapi.startapp.networking.MenuResponse
import com.example.iikoapi.startapp.networking.NetworkService
import kotlinx.android.synthetic.main.activity_start.*
import kotlin.concurrent.thread


lateinit var menu: MenuResponse
lateinit var restr: DeliveryRestrictionsResponse
class StartActivity : AppCompatActivity() {

    val iiko = Iiko(this,provider = NetworkService.instance!!,pb = null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        dat(iiko, this, progressBar).execute()

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

class dat(var iiko : Iiko, var context: Context, var progressBar: ProgressBar) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {

        iiko.authentication()
        iiko.getOrganisation(0)
        iiko.getMenu()
        menu = iiko.menuResponse
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        context.startActivity(Intent(context, GeneralActivity::class.java))
    }
}