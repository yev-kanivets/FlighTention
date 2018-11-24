package fr2018.defense.innovation.forum.flightention.repo

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import fr2018.defense.innovation.forum.flightention.model.Flight
import java.util.Random
import java.util.Timer
import java.util.TimerTask

class FakeFlightModeler(
    private val handler: Handler,
    private val liveData: MutableLiveData<List<Flight>>,
    private val period: Long
) {

    private val flights = mutableListOf<Flight>()

    fun start() {
        createFlights()

        Timer().schedule(object : TimerTask() {

            private val random = Random()

            override fun run() {
                flights.forEach {
                    val diffLat = it.goalLatitude - it.latitude
                    val diffLon = it.goalLongitude - it.longitude
                    val diffSum = Math.abs(diffLat) + Math.abs(diffLon)

                    if (diffSum < 0.002) {
                        it.velocity = 0.0
                        it.onGround = true
                        return@forEach
                    }

                    if (it.dangerInPercents > 100) {
                        it.velocity = 0.0
                        if (it.lastContact == "Lost!") return@forEach
                        it.lastContact = "Lost"
                        return@forEach
                    }

                    it.latitude += 0.001 * diffLat / diffSum
                    it.longitude += 0.001 * diffLon / diffSum

                    it.lastContact = "${3 + Math.abs(random.nextLong()) % 2}s ago"
                    it.velocity += random.nextLong() % 10

                    if (it.callSign == "AZ609") {
                        it.dangerInPercents += 0.5
                        it.lastContact = "${5 + it.dangerInPercents + Math.abs(random.nextLong()) % 2}s ago"
                        it.velocity = 900 - 3 * it.dangerInPercents
                    }
                }

                handler.post { liveData.value = flights }
            }

        }, 0, period)
    }

    private fun createFlights() {
        flights += Flight("AZ609", "CDG", "BRU", "30s ago", 49.011710, 2.491889, 50.011710, 2.791889, false, 900.0, 0.0)
        flights += Flight("ASA1041", "CDG", "LUX", "30s ago", 49.028559, 2.505236, 51.011710, 10.0, false, 1000.0, 0.0)
        flights += Flight("RPA3461", "CDG", "BER", "30s ago", 49.026492, 2.613687, 40.011710, 100.0, false, 940.0, 0.0)
        flights += Flight("SWA1251", "CDG", "SXB", "30s ago", 48.979897, 2.590483, 50.011710, 10.0, false, 920.0, 0.0)
        flights += Flight(
            "EJA588",
            "TLV",
            "CDG",
            "30s ago",
            48.9560408,
            2.5148014,
            49.0096906,
            2.5479245,
            false,
            967.0,
            0.0
        )
    }

}
