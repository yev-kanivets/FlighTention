package fr2018.defense.innovation.forum.flightention.contract

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import fr2018.defense.innovation.forum.flightention.model.Flight

interface FlightsMapContract {

    interface View : LifecycleOwner {

        fun getContext(): Context

        fun initMapFragment()

        fun setMap(googleMap: GoogleMap)

        fun moveAndZoomToCDG()

        fun displayFlight(markerOptions: MarkerOptions): Marker

        fun displayFlightCategories(flightsOk: List<Flight>, flightsAlert: List<Flight>, flightsCritical: List<Flight>)

    }

    interface Presenter : OnMapReadyCallback {

        fun start()

    }

}
