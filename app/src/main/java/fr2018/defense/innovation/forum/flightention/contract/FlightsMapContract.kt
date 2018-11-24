package fr2018.defense.innovation.forum.flightention.contract

import android.arch.lifecycle.LifecycleOwner
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

interface FlightsMapContract {

    interface View : LifecycleOwner {

        fun initMapFragment()

        fun setMap(googleMap: GoogleMap)

        fun moveAndZoomToCDG()

        fun displayFlight(markerOptions: MarkerOptions): Marker

    }

    interface Presenter : OnMapReadyCallback {

        fun start()

    }

}
