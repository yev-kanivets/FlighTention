package fr2018.defense.innovation.forum.flightention.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import fr2018.defense.innovation.forum.flightention.R.id
import fr2018.defense.innovation.forum.flightention.R.layout
import fr2018.defense.innovation.forum.flightention.model.FlightData

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_maps)

        initMapFragment()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        moveAndZoomToCDG()
        addFlights()
    }

    private fun initMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun moveAndZoomToCDG() {
        val sydney = LatLng(49.0096906, 2.5479245)
        mMap.addMarker(MarkerOptions().position(sydney).title("CDG"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))
    }

    private fun addFlights() {
        val flightData = FlightData("JBU1417", 1543021860, 49.0139128,2.5418305, false, 100.0, 0.0, 10.0, 0)

        val latLng = LatLng(flightData.latitude, flightData.longitude)
        mMap.addMarker(MarkerOptions().position(latLng).title(flightData.callSign))
    }

}
