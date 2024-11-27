package models

data class Driver(
    var driverId: Int = 0,
    var driverName: String = "",
    var driverNationality: String = "",
    var country: String = "",
    var teamId: Int = 0,
    var id: Int = 0,
    var podiums: Int = 0,
    var wasDriverAdded: Boolean = true,
    var trophies: Int = 0,
    var isDriverInSystem: Boolean = false,
    var teamName: String,
    var teamLocation: String,
    val driverTeam: String,
    ) {
    // Override toString to display driver status
    override fun toString(): String {
        return if (isDriverInSystem)
            "$driverId: $driverName (Complete)"
        else
            "$driverId: $driverName (TODO)"
    }
}
