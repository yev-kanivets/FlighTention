package fr2018.defense.innovation.forum.flightention.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import fr2018.defense.innovation.forum.flightention.model.Flight

class FlightRepositoryImpl : FlightRepository {

    override suspend fun getAllFlights(): LiveData<List<Flight>> {
        val liveData = MutableLiveData<List<Flight>>()

        FakeFlightModeler(Handler(), liveData, 100).start()

        return liveData
    }

}
