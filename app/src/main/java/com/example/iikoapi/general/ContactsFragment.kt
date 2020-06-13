package com.example.iikoapi.general

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.example.iikoapi.general.contacts.MapsActivity

class ContactsFragment(var contextGeneral: Context) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_contacts, container, false)

        var mapsButton = view.findViewById<Button>(R.id.maps_button)
        mapsButton.setOnClickListener {
            startActivity(Intent(contextGeneral, MapsActivity::class.java))
        }

        return view
    }
}