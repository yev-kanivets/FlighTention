package fr2018.defense.innovation.forum.flightention.repo

import android.arch.lifecycle.MutableLiveData
import android.os.Handler
import fr2018.defense.innovation.forum.flightention.model.Flight
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

            override fun run() {
                flights.forEach {
                    val diffLat = it.goalLatitude - it.latitude
                    val diffLon = it.goalLongitude - it.longitude
                    val diffSum = Math.abs(diffLat) + Math.abs(diffLon)

                    if (diffSum < 0.002) return@forEach

                    it.latitude += 0.001 * diffLat / diffSum
                    it.longitude += 0.001 * diffLon / diffSum

                    if (it.callSign == "JBU1411") it.dangerInPercents += 0.5
                }

                handler.post { liveData.value = flights }
            }

        }, 0, period)
    }

    private fun createFlights() {
        flights += Flight("JBU1411", 49.011710, 2.491889, 50.011710, 2.791889, false, 10.0, 0.0)
        flights += Flight("ASA1041", 49.028559, 2.505236, 51.011710, 10.0, false, 10.0, 0.0)
        flights += Flight("RPA3461", 49.026492, 2.613687, 40.011710, 100.0, false, 10.0, 0.0)
        flights += Flight("SWA1251", 48.979897, 2.590483, 50.011710, 10.0, false, 10.0, 0.0)
        flights += Flight("EJA588", 48.9560408, 2.5148014, 49.0096906, 2.5479245, false, 10.0, 0.0)
    }

}
