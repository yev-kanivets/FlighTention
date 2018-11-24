package fr2018.defense.innovation.forum.flightention.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import fr2018.defense.innovation.forum.flightention.R
import fr2018.defense.innovation.forum.flightention.R.id
import fr2018.defense.innovation.forum.flightention.R.layout
import fr2018.defense.innovation.forum.flightention.contract.FlightsMapContract
import fr2018.defense.innovation.forum.flightention.presenter.FlightsMapPresenter
import fr2018.defense.innovation.forum.flightention.repo.FlightRepositoryImpl
import com.google.android.gms.maps.model.MapStyleOptions
import fr2018.defense.innovation.forum.flightention.model.Flight
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_maps.flightView
import kotlinx.android.synthetic.main.activity_maps.ivClose
import kotlinx.android.synthetic.main.activity_maps.tvAirplaneTitle
import kotlinx.android.synthetic.main.activity_maps.tvCallSign
import kotlinx.android.synthetic.main.activity_maps.tvRoute
import kotlinx.android.synthetic.main.activity_maps.tvVelocity

class FlightsMapActivity : AppCompatActivity(), FlightsMapContract.View {

    private lateinit var presenter: FlightsMapContract.Presenter

    private lateinit var map: GoogleMap
    private lateinit var recyclerView: RecyclerView
    private var titleToDisplay: String? = null

    private val sectionAdapter = SectionedRecyclerViewAdapter()
    private val sectionCritical =
        ExpandableFligtsSection(sectionAdapter, "Anomalie critique", R.drawable.ic_flight_critical, listOf())
    private val sectionAlert =
        ExpandableFligtsSection(sectionAdapter, "Anomalie détecté", R.drawable.ic_flight_alert, listOf())
    private val sectionOk = ExpandableFligtsSection(sectionAdapter, "R.A.S", R.drawable.ic_flight_ok, listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_maps)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = sectionAdapter
        sectionAdapter.addSection(sectionCritical)
        sectionAdapter.addSection(sectionAlert)
        sectionAdapter.addSection(sectionOk)

        ivClose.setOnClickListener {
            flightView.visibility = View.GONE
            titleToDisplay = null
        }

        presenter = FlightsMapPresenter(this, FlightRepositoryImpl())
        presenter.start()
    }

    override fun getContext(): Context = baseContext

    override fun initMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(id.map) as SupportMapFragment
        mapFragment.getMapAsync(presenter)
    }

    override fun setMap(googleMap: GoogleMap) {
        map = googleMap
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))
        map.setOnInfoWindowClickListener {
            titleToDisplay = it.title
            flightView.visibility = View.VISIBLE
        }
    }

    override fun moveAndZoomToCDG() {
        val cdg = LatLng(49.0096906, 2.5479245)
        map.addMarker(MarkerOptions().position(cdg).title("CDG"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cdg, 10f))
    }

    override fun displayFlight(markerOptions: MarkerOptions): Marker = map.addMarker(markerOptions)

    override fun displayFlightCategories(
        flightsOk: List<Flight>, flightsAlert: List<Flight>, flightsCritical: List<Flight>
    ) {
        sectionCritical.updateFlights(flightsCritical)
        sectionAlert.updateFlights(flightsAlert)
        sectionOk.updateFlights(flightsOk)

        (flightsOk + flightsAlert + flightsCritical).find { it.callSign == titleToDisplay }?.let {
            tvAirplaneTitle.text = it.callSign
            tvRoute.text = "${it.fromAirport} --> ${it.toAirport}"
            tvCallSign.text = "Callsign: ${it.callSign}"
            tvVelocity.text = "Velocity: ${it.velocity} km/h"
        }
    }

}
