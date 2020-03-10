package fr.isen.android.project.intodeep

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



class GoogleMapInfoWindowActivity : AppCompatActivity(), GoogleMap.OnMapLoadedCallback, GoogleMap.OnMapClickListener, OnMapReadyCallback {
    override fun onMapLoaded() {
        Log.v("_map","on map loaded")
    }

    private var mMap: GoogleMap? = null
    private val databse: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = databse.getReference()
    var table_name = "diving_site"
    var info: InfoWindowData? = null
    var name ="empty"
    var id = "0"
    var long = "6.60222"
    var lat = "43.168611"
    var index = 0
    var list_Loc = mutableListOf<InfoWindowData?>()
    var List_Long = mutableListOf<Double>()
    var List_Lat = mutableListOf<Double>()
    var List_id = mutableListOf<Int>()
    var List_name = mutableListOf<String>()

    var auth: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("_map", "On map create")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
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

                                info = InfoWindowData(
                                    name,
                                    "id = $id",
                                    "long : $long lat : $lat"
                                )
                            }
                            list_Loc.add(index, info)
                            List_Long.add(index, long.toDouble())
                            List_Lat.add(index, lat.toDouble())
                            List_id.add(index, id.toInt())
                            List_name.add(index, name)

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
        /*info = InfoWindowData("title","sub","desc")
        val markerOptions = MarkerOptions()
        markerOptions.position(location)
            .title("Location Details")
            .snippet("I am custom Location Marker.")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

        val customInfoWindow = CustomInfoWindowGoogleMap(this)
        mMap!!.setInfoWindowAdapter(customInfoWindow)

        val marker = mMap!!.addMarker(markerOptions)
        marker.tag = info
        //marker.showInfoWindow()*/

        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(location))
        //Toast.makeText(applicationContext,"Pour load les points de plonger, appuyer sur la carte !", Toast.LENGTH_LONG)
    }

    override fun onMapClick (point : LatLng) {
        //Toast.makeText(applicationContext,"Pour load les points de plonger, appuyer sur la carte !", Toast.LENGTH_LONG)
        Log.v("_map", "On map click")
        Log.v("_map", "$auth")
        if(auth) {
            for (n in 0..3) {
                Log.v("_map", "n : $n")
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

                            Log.v("_map", "marker initialiser")
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
}