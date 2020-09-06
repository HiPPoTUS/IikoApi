package com.example.iikoapi.general

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.iikoapi.R

class ContactsFragment(private var contextGeneral: Context) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)

        val mapsButton = view.findViewById<Button>(R.id.maps_button)
//        var zones = restr.deliveryZones?.get(0)?.coordinates?.get(0)
//        Log.d("tag", zones.toString())
//        Log.d("tag", ret().toString())
//        mapsButton.setOnClickListener {
//            startActivity(Intent(contextGeneral, MapsActivity::class.java))
//        }

        return view
    }

}