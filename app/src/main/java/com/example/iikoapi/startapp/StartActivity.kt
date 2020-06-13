package com.example.iikoapi.startapp

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.iikoapi.R
import com.example.iikoapi.startapp.networking.MySingleton
import com.example.iikoapi.startapp.networking.NetworkInteraction
import com.example.iikoapi.startapp.networking.NukeSSLCerts


class StartActivity : AppCompatActivity() {

    lateinit var interact: NetworkInteraction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val S = MySingleton.getInstance(this)
        NukeSSLCerts().nuke()

        interact = NetworkInteraction(S, this)
        interact.start()


    }
}