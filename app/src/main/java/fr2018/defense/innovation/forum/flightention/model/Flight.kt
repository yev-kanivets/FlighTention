package fr2018.defense.innovation.forum.flightention.model

data class Flight(
    val callSign: String,
    val fromAirport: String,
    val toAirport: String,
    var lastContact: String,
    var latitude: Double,
    var longitude: Double,
    var goalLatitude: Double,
    var goalLongitude: Double,
    var onGround: Boolean,
    var velocity: Double,
    var dangerInPercents: Double
)
