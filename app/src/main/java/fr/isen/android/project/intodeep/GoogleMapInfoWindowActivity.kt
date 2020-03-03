package fr.isen.android.project.intodeep

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapInfoWindowActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap!!.uiSettings.isZoomControlsEnabled = true
        mMap!!.setMinZoomPreference(11f)

        val islamabad = LatLng(33.738045, 73.084488)

        val markerOptions = MarkerOptions()
        markerOptions.position(islamabad)
            .title("Location Details")
            .snippet("I am custom Location Marker.")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

        val info = InfoWindowData("Un titre",
            "un sous titre",
            "une description"
            )


        val customInfoWindow = CustomInfoWindowGoogleMap(this)
        mMap!!.setInfoWindowAdapter(customInfoWindow)

        val marker = mMap!!.addMarker(markerOptions)
        marker.tag = info
        marker.showInfoWindow()

        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(islamabad))
    }
}