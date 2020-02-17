package fr.isen.android.project.intodeep

import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.marker_test.view.*

class CustomInfoWindowGoogleMap(private val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View {

        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.marker_test, null)
        var mInfoWindow: InfoWindowData? = p0?.tag as InfoWindowData?

        mInfoView.marker_title.text = mInfoWindow?.title
        mInfoView.marker_subtitle.text = mInfoWindow?.subtitle
        mInfoView.marker_description.text = mInfoWindow?.description

        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}