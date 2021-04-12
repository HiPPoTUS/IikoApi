package com.example.iikoapi.general

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.iikoapi.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import kotlinx.android.synthetic.main.fragment_contacts.view.*


class ContactsFragment(private var contextGeneral: Context) : Fragment(R.layout.fragment_contacts), UserLocationObjectListener,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var mapView: MapView
    private lateinit var mapKit: MapKit
    private lateinit var positionButton: ImageView
    private lateinit var zoomUp: TextView
    private lateinit var zoomDown: TextView
    private var isFirstTime = true

    private lateinit var userLocationLayer: UserLocationLayer

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
//            Toast.makeText(context, "granted", Toast.LENGTH_SHORT).show()
            if(userLocationLayer.cameraPosition() != null){
                mapView.map.move(
                    userLocationLayer.cameraPosition()!!,
                    Animation(Animation.Type.SMOOTH, 0.3f),
                    null
                )
            }

//            setUpUserLayer()
        } else {
//            Toast.makeText(context, "not granted", Toast.LENGTH_SHORT).show()
//            // Explain to the user that the feature is unavailable because the
//            // features requires a permission that the user has denied. At the
//            // same time, respect the user's decision. Don't link to system
//            // settings in an effort to convince the user to change their
//            // decision.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(context)
        mapKit = MapKitFactory.getInstance()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setUpUserLayer()

        mapView.map.move(
            CameraPosition(Point(61.904428, 32.347221), 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )

        view.zoomUp.setOnClickListener{
            mapView.map.move(
                CameraPosition(
                    mapView.map.cameraPosition.target,
                    mapView.map.cameraPosition.zoom + 1,
                    mapView.map.cameraPosition.azimuth,
                    mapView.map.cameraPosition.tilt
                ),
                Animation(Animation.Type.SMOOTH, 0.3f),
                null
            )
        }
        view.zoomDown.setOnClickListener{
            mapView.map.move(
                CameraPosition(
                    mapView.map.cameraPosition.target,
                    mapView.map.cameraPosition.zoom - 1,
                    mapView.map.cameraPosition.azimuth,
                    mapView.map.cameraPosition.tilt
                ),
                Animation(Animation.Type.SMOOTH, 0.3f),
                null
            )
        }
    }

    private fun initViews(view: View){
        mapView = view.mapView
        positionButton = view.positionButton
        zoomUp = view.zoomUp
        zoomDown = view.zoomDown

        positionButton.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onStart() {
        super.onStart()
        mapKit.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapKit.onStop()
        mapView.onStop()
    }


    private fun setUpUserLayer(){
        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer.isVisible = true;
        userLocationLayer.isHeadingEnabled = true;

        userLocationLayer.setObjectListener(this);
    }


    override fun onObjectUpdated(userLocationView: UserLocationView, p1: ObjectEvent) {

        if(userLocationLayer.cameraPosition() != null && isFirstTime){
            isFirstTime = false
            mapView.map.move(
                userLocationLayer.cameraPosition()!!,
                Animation(Animation.Type.SMOOTH, 0.3f),
                null
            )
        }

    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {

        if(userLocationLayer.cameraPosition() != null){
            mapView.map.move(
                CameraPosition(
                    userLocationView.arrow.geometry,
                    18f,
                    userLocationView.arrow.direction,
                    0f
                ),
                Animation(Animation.Type.SMOOTH, 0f),
                null
            )
        }

    }

}