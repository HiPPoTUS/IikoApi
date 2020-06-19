package com.example.iikoapi.general.contacts

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iikoapi.R
import com.example.iikoapi.startapp.networking.restr
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        var r = MutableList(restr.deliveryZones?.get(0)?.coordinates!!.size){
            LatLng(restr.deliveryZones?.get(0)?.coordinates!![it].latitude, restr.deliveryZones?.get(0)?.coordinates!![it].longitude)
        } as ArrayList

        val polyline1 = googleMap.addPolygon(PolygonOptions().clickable(true).addAll(r))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
            LatLng(restr.deliveryZones?.get(0)?.coordinates?.get(0)?.latitude!!, restr.deliveryZones?.get(0)?.coordinates?.get(0)?.longitude!!), 13f)
        )

        polyline1.strokeWidth = 0f
        polyline1.fillColor = getColorWithAlpha(Color.RED, 0.4f)

//        val first = LatLng(restr.deliveryZones?.get(0)?.coordinates?.get(0)?.latitude!!.toDouble(), restr.deliveryZones?.get(0)?.coordinates?.get(0)?.longitude!!.toDouble())
//        mMap.addMarker(MarkerOptions().position(first).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(first))
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(first, 12.0f));
    }

    fun getColorWithAlpha(color: Int, ratio: Float): Int {
        var newColor = 0
        val alpha = Math.round(Color.alpha(color) * ratio).toInt()
        val r: Int = Color.red(color)
        val g: Int = Color.green(color)
        val b: Int = Color.blue(color)
        newColor = Color.argb(alpha, r, g, b)
        return newColor
    }

}
