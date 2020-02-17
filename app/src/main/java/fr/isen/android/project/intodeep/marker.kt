package fr.isen.android.project.intodeep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions










class marker : AppCompatActivity(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mMap: GoogleMap? = null

    private val SYDNEY = LatLng(-33.87365, 151.20689)

    private var mSydney: Marker? = null

    override fun onMapReady(map: GoogleMap?) {
        mMap = map

        // on ajoute les markers
        mSydney = mMap?.addMarker(
            MarkerOptions()
                .position(SYDNEY)
                .title("Sydney")
        )
        mSydney?.tag = 0

        //on met un listener
        mMap?.setOnCircleClickListener{this}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker)



    }
}
