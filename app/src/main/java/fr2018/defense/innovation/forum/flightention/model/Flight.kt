package fr2018.defense.innovation.forum.flightention.model

data class Flight(
    val callSign: String,
    val lastContactInS: Long,
    var latitude: Double,
    var longitude: Double,
    val onGround: Boolean,
    val velocity: Double,
    val trueTrack: Double?,
    val verticalRate: Double,
    val dangerInPercents: Int
) {

    override fun equals(other: Any?): Boolean {
        return if (other is Flight) {
            other.callSign == callSign
        } else false
    }

}
