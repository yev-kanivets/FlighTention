package fr2018.defense.innovation.forum.flightention.contract

import android.arch.lifecycle.LifecycleOwner
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import fr2018.defense.innovation.forum.flightention.model.Flight

interface FlightsMapContract {

    interface View : LifecycleOwner {

        fun initMapFragment()

        fun setMap(googleMap: GoogleMap)

        fun moveAndZoomToCDG()

        fun displayFlight(flight: Flight)

    }

    interface Presenter : OnMapReadyCallback {

        fun start()

    }

}
