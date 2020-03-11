package fr.isen.android.project.intodeep

import android.content.Intent
import android.icu.text.IDNA
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import org.json.JSONObject
import com.google.firebase.database.FirebaseDatabase.getInstance as getInstance1
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView


class GoogleMapInfoWindowActivity : AppCompatActivity(), GoogleMap.OnMapLoadedCallback, GoogleMap.OnMapClickListener, OnMapReadyCallback {
    override fun onMapLoaded() {
        Log.v("_map","on map loaded")
    }
    lateinit var toolbar: ActionBar
    private var mMap: GoogleMap? = null
    private val databse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = databse.getReference()
    var table_name = "diving_site"
    var info: InfoWindowData? = null
    var name ="empty"
    var id = "0"
    var long = "6.60222"
    var lat = "43.168611"
    var deep="0"
    var description ="empty"
    var index = 0
    var list_Loc = mutableListOf<InfoWindowData?>()
    var List_Long = mutableListOf<Double>()
    var List_Lat = mutableListOf<Double>()
    var List_id = mutableListOf<String>()
    var List_name = mutableListOf<String>()
    var List_description = mutableListOf<String>()
    var List_deep = mutableListOf<Double>()

    var auth: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        //Log.v("_map", "On map create")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnap: DataSnapshot) {
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnap: DataSnapshot) {
                        for (oui in dataSnap.child(table_name).children) {
                            for (child in oui.children) {

                                //Log.v("_map", " ${child.key} = ${child.value}")

                                if (child.key == "name") {
                                    name = child.value.toString()
                                }
                                if (child.key == "latitude") {
                                    lat = child.value.toString()
                                }
                                if (child.key == "longitude") {
                                    long = child.value.toString()
                                }
                                if (child.key == "id") {
                                    id = child.value.toString()
                                }
                                if (child.key == "deep") {
                                    deep = child.value.toString()
                                }
                                if (child.key == "description") {
                                    description = child.value.toString()
                                }

                                info = InfoWindowData(
                                    name,
                                    "$lat, $long",
                                    "Profondeur : "+deep+"m",
                                    "$description"
                                )
                            }
                            list_Loc.add(index, info)
                            List_Long.add(index, long.toDouble())
                            List_Lat.add(index, lat.toDouble())
                            List_id.add(index, id)
                            List_name.add(index, name)
                            List_description.add(index,description)
                            List_deep.add(index, deep.toDouble())

                            //Log.v("_map","list_loc : ${list_Loc[index]}")
                            index++


                        }
                        auth = true;
                    }
                    override fun onCancelled(p0: DatabaseError) {
                        Log.v("_map", "Probleme au niveau de firebase")
                    }
                })
            }
            override fun onCancelled(p0: DatabaseError) {
                Log.v("_map", "Probleme au niveau de firebase")
            }
        })
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.setOnMapClickListener { onMapClick(point = LatLng(0.0,0.0) ) }
        Log.v("_map", "On map ready")
        mMap!!.uiSettings.isZoomControlsEnabled = true
        mMap!!.setMinZoomPreference(11f)
        //


        val location = LatLng(lat.toDouble(), long.toDouble())
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(location))
        Toast.makeText(applicationContext,"Pour load les points de plonger, appuyer sur la carte !", Toast.LENGTH_LONG).show()
    }

    override fun onMapClick (point : LatLng) {
        Log.v("_map", "On map click")
        //Log.v("_map", "$auth")
        var lim = index-1
        if(auth) {
            for (n in 0..lim) {
               // Log.v("_map", "n : $n")
                if (list_Loc.isNotEmpty()) {
                    if (List_Lat.isNotEmpty()) {
                        if (List_Long.isNotEmpty()) {
                            /*Log.v(
                                "_map",
                                "lat : ${List_Lat[n]}, long ${List_Long[n]}, info : ${list_Loc[n]}"
                            )*/
                            var location = LatLng(List_Lat[n], List_Long[n])

                            var markerOptions = MarkerOptions()
                            markerOptions.position(location).title("Location detail")
                                .snippet("I am custom Location Marker")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

                            var customInfoWindow = CustomInfoWindowGoogleMap(this)
                            mMap!!.setInfoWindowAdapter(customInfoWindow)

                            val marker = mMap!!.addMarker(markerOptions)
                            marker.tag = list_Loc[n]
                            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(location))
                            //marker.showInfoWindow()

                            //Log.v("_map", "marker initialiser")
                        } else {
                            Log.v("_map", "list_lat est vide")
                        }
                    } else {
                        Log.v("_map", "list_long est vide")
                    }
                } else {
                    Log.v("_map", "list_loc est vide")
                }

            }
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.memo_item -> {
                intent= Intent(this, MemoActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.perso_item -> {
                intent= Intent(this, MemberActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.feed_item -> {
                intent= Intent(this, GoogleMapInfoWindowActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.add_item -> {
                intent= Intent(this, AddSpotActivity::class.java)
                startActivity(intent)
                true
            }
        }
        false
    }
}