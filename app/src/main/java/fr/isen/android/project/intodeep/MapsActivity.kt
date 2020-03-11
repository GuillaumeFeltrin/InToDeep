package fr.isen.android.project.intodeep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomnavigation.BottomNavigationView


class MapsActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //creation de l'icon

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val markerOption = MarkerOptions()
        markerOption.position(sydney).
            title("Marker in Sydney").
            snippet("ceci est une description")

        /*val info = InfoWindowData("title",
            "subtitle",
            "pronfondeur",
            "description"
        )*/

        val customInfoWindow = CustomInfoWindowGoogleMap(this)

        mMap.setInfoWindowAdapter(customInfoWindow)

        val marker = mMap.addMarker(markerOption)

        marker.showInfoWindow()

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.memo_item -> {
                intent= Intent(this, MemoActivity::class.java)
                startActivity(intent)
                //true
            }
            R.id.perso_item -> {
                intent= Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                //true
            }
            R.id.feed_item -> {
                intent= Intent(this, MapsActivity::class.java)
                startActivity(intent)
                //true
            }
            R.id.add_item -> {
                intent= Intent(this, AddSpotActivity::class.java)
                startActivity(intent)
                //true
            }
        }
        false
    }
}
