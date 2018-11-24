package fr2018.defense.innovation.forum.flightention.model

data class FlightData(
    val callSign: String,
    val lastContactInS: Long,
    val latitude: Double,
    val longitude: Double,
    val onGround: Boolean,
    val velocity: Double,
    val trueTrack: Double?,
    val verticalRate: Double,
    val dangerInPercents: Int
)
