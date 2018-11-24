package fr2018.defense.innovation.forum.flightention.presenter

import android.arch.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import fr2018.defense.innovation.forum.flightention.contract.FlightsMapContract
import fr2018.defense.innovation.forum.flightention.model.Flight
import fr2018.defense.innovation.forum.flightention.repo.FlightRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FlightsMapPresenter(
    private val presentedView: FlightsMapContract.View,
    private val flightRepository: FlightRepository
) : FlightsMapContract.Presenter {

    private val presenterJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + presenterJob)

    private val flightMarkers = mutableMapOf<String, Marker>()

    override fun start() {
        presentedView.initMapFragment()
    }

    override fun onMapReady(map: GoogleMap) {
        presentedView.setMap(map)
        presentedView.moveAndZoomToCDG()
        uiScope.launch { fetchFlights() }
    }

    private suspend fun fetchFlights() {
        flightRepository.getAllFlights().observe(presentedView, Observer<List<Flight>> { flights ->
            displayAndMoveMarkers(flights)
            displayFlightCategories(flights)
        })
    }

    private fun displayAndMoveMarkers(flights: List<Flight>?) {
        flights?.forEach { flight ->
            flightMarkers[flight.callSign]?.let { marker ->
                moveFlightMarker(marker, flight)
            } ?: addFlightMarker(flight)
        }
    }

    private fun displayFlightCategories(flights: List<Flight>?) {
        val flightsOk = flights?.filter { it.dangerInPercents <= 20 } ?: listOf()
        val flightsAlert = flights?.filter { it.dangerInPercents in 21..80 } ?: listOf()
        val flightsCritical = flights?.filter { it.dangerInPercents > 80 } ?: listOf()
        presentedView.displayFlightCategories(flightsOk, flightsAlert, flightsCritical)
    }

    private fun moveFlightMarker(marker: Marker, flight: Flight) {
        marker.position = LatLng(flight.latitude, flight.longitude)
    }

    private fun addFlightMarker(flight: Flight) {
        val marker = presentedView.displayFlight(flight.toMarkerOptions())
        flightMarkers += Pair(flight.callSign, marker)
    }

    private fun Flight.toMarkerOptions(): MarkerOptions {
        val latLng = LatLng(latitude, longitude)
        return MarkerOptions().position(latLng).title(callSign)
    }

}
