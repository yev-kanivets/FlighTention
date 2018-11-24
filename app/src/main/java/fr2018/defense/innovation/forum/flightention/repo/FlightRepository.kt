package fr2018.defense.innovation.forum.flightention.repo

import android.arch.lifecycle.LiveData
import fr2018.defense.innovation.forum.flightention.model.Flight

interface FlightRepository {

    suspend fun getAllFlights(): LiveData<List<Flight>>

}
