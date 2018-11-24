package fr2018.defense.innovation.forum.flightention.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import fr2018.defense.innovation.forum.flightention.model.Flight
import java.util.Random
import java.util.Timer
import java.util.TimerTask

class FlightRepositoryImpl : FlightRepository {

    override suspend fun getAllFlights(): LiveData<List<Flight>> {
        val liveData = MutableLiveData<List<Flight>>()

        FlightModeler(Handler(), liveData, 100).start()

        return liveData
    }

    class FlightModeler(
        private val handler: Handler,
        private val liveData: MutableLiveData<List<Flight>>,
        private val period: Long
    ) {

        private val flights = mutableListOf<Flight>()

        fun start() {
            createFlights()

            Timer().schedule(object : TimerTask() {

                override fun run() {
                    val random = Random(System.currentTimeMillis())

                    flights.forEach {
                        it.latitude += Math.abs(random.nextLong() % 2) * 0.001
                        it.longitude += Math.abs(random.nextLong() % 2) * 0.001
                    }

                    handler.post { liveData.value = flights }
                }

            }, 0, period)
        }

        private fun createFlights() {
            flights += Flight("JBU1411", 1543021860, 49.011710, 2.491889, false, 100.0, 0.0, 10.0, 0)
            flights += Flight("JBU1412", 1543021860, 49.028559, 2.505236, false, 100.0, 0.0, 10.0, 0)
            flights += Flight("JBU1413", 1543021860, 49.026492, 2.613687, false, 100.0, 0.0, 10.0, 0)
            flights += Flight("JBU1414", 1543021860, 48.979897, 2.590483, false, 100.0, 0.0, 10.0, 0)
            flights += Flight("JBU1415", 1543021860, 48.9560408, 2.5148014, false, 100.0, 0.0, 10.0, 0)
        }

    }

}
