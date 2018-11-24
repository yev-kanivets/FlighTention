package fr2018.defense.innovation.forum.flightention.model

data class Flight(
    val callSign: String,
    var latitude: Double,
    var longitude: Double,
    var goalLatitude: Double,
    var goalLongitude: Double,
    val onGround: Boolean,
    val velocity: Double,
    val dangerInPercents: Int
)
