package models

// Assuming 'Formula1' and 'Team' are also defined somewhere in your code.
data class Driver(
    var driverId: Int = 0,
    val driverName: String,
    var driverNationality: String,
    val country: String = "",
    var formula1Id: Int = 0,
    val id: Int = 0,
    var podiums: Int = 0,
    var wasDriverAdded: Boolean = true,
    var trophies: Int = 0,
    var isDriverInSystem: Boolean = false,
    val driverTeam: String
) {

    // Override toString to display driver status
    override fun toString(): String {
        return if (isDriverInSystem)
            "$driverId: $driverName (Complete)"
        else
            "$driverId: $driverName (TODO)"
    }

    // Example of how you might initialize a list of drivers to pass to Formula1API
    companion object {
        fun createDriverList(): List<Driver> {
            // Directly passing team names instead of referring to a non-existent 'team'
            return listOf(
                Driver(
                    driverId = 1,
                    driverName = "Lewis Hamilton",
                    driverNationality = "British",
                    driverTeam = "Mercedes"
                ),
                Driver(
                    driverId = 2,
                    driverName = "Max Verstappen",
                    driverNationality = "Dutch",
                    driverTeam = "Red Bull Racing"
                ),
                Driver(
                    driverId = 3,
                    driverName = "Charles Leclerc",
                    driverNationality = "Monégasque",
                    driverTeam = "Ferrari"
                )
            )
        }
    }
}
