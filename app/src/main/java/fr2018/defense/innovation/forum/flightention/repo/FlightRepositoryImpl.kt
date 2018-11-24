package fr2018.defense.innovation.forum.flightention.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import fr2018.defense.innovation.forum.flightention.model.Flight

class FlightRepositoryImpl : FlightRepository {

    override suspend fun getAllFlights(): LiveData<List<Flight>> {
        val flight1 = Flight("JBU1417", 1543021860, 49.0139128,2.5418305, false, 100.0, 0.0, 10.0, 0)
        val flight2 = Flight("JBU1418", 1543021860, 49.0259128,2.5538305, false, 100.0, 0.0, 10.0, 0)
        val flight3 = Flight("JBU1419", 1543021860, 49.0379128,2.5658305, false, 100.0, 0.0, 10.0, 0)

        val liveData = MutableLiveData<List<Flight>>()
        liveData.value = listOf(flight1, flight2, flight3)

        return liveData
    }

}
