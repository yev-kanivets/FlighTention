package fr2018.defense.innovation.forum.flightention.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

class FlightsMapActivity : AppCompatActivity(), FlightsMapContract.View {

    private lateinit var presenter: FlightsMapContract.Presenter

    private lateinit var map: GoogleMap
    private lateinit var recyclerView: RecyclerView

    private val sectionAdapter = SectionedRecyclerViewAdapter()
    private val sectionCritical = ExpandableFligtsSection(sectionAdapter, "Anomalie critique", listOf())
    private val sectionAlert = ExpandableFligtsSection(sectionAdapter, "Anomalie détecté", listOf())
    private val sectionOk = ExpandableFligtsSection(sectionAdapter, "R.A.S", listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_maps)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = sectionAdapter
        sectionAdapter.addSection(sectionCritical)
        sectionAdapter.addSection(sectionAlert)
        sectionAdapter.addSection(sectionOk)

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
    }

    override fun moveAndZoomToCDG() {
        val sydney = LatLng(49.0096906, 2.5479245)
        map.addMarker(MarkerOptions().position(sydney).title("CDG"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 9f))
    }

    override fun displayFlight(markerOptions: MarkerOptions): Marker = map.addMarker(markerOptions)

    override fun displayFlightCategories(
        flightsOk: List<Flight>, flightsAlert: List<Flight>, flightsCritical: List<Flight>
    ) {
        sectionCritical.updateFlights(flightsCritical)
        sectionAlert.updateFlights(flightsAlert)
        sectionOk.updateFlights(flightsOk)
    }

}
