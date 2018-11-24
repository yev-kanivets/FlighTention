package fr2018.defense.innovation.forum.flightention.presenter

import android.arch.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
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
            flights?.forEach { presentedView.displayFlight(it) }
        })
    }

}
