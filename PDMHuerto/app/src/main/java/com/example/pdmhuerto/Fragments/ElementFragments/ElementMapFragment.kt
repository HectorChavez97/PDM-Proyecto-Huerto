package com.example.pdmhuerto.Fragments.ElementFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdmhuerto.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parse.ParseObject
import kotlinx.android.synthetic.main.fragment_element_map.*

class ElementMapFragment(val element: ParseObject) : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_element_map, container, false)
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            mMap = it
            val location = LatLng(element.getParseGeoPoint("geoPoint")!!.latitude,
                                  element.getParseGeoPoint("geoPoint")!!.longitude )
            mMap.addMarker(MarkerOptions().position(location).title("Marker"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f))
        }
    }
}
