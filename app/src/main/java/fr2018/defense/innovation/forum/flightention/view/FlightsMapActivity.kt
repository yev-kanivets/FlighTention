package fr2018.defense.innovation.forum.flightention.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import fr2018.defense.innovation.forum.flightention.R.id
import fr2018.defense.innovation.forum.flightention.R.layout
import fr2018.defense.innovation.forum.flightention.contract.FlightsMapContract
import fr2018.defense.innovation.forum.flightention.presenter.FlightsMapPresenter
import fr2018.defense.innovation.forum.flightention.repo.FlightRepositoryImpl

class FlightsMapActivity : AppCompatActivity(), FlightsMapContract.View {

    private lateinit var presenter: FlightsMapContract.Presenter

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_maps)

        presenter = FlightsMapPresenter(this, FlightRepositoryImpl())
        presenter.start()
    }

    override fun initMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(id.map) as SupportMapFragment
        mapFragment.getMapAsync(presenter)
    }

    override fun setMap(googleMap: GoogleMap) {
        map = googleMap
    }

    override fun moveAndZoomToCDG() {
        val sydney = LatLng(49.0096906, 2.5479245)
        map.addMarker(MarkerOptions().position(sydney).title("CDG"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))
    }

    override fun displayFlight(markerOptions: MarkerOptions): Marker = map.addMarker(markerOptions)

}
