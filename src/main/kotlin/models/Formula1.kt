package ie.setu.models

data class Formula1(
    var driverName: String,
    var driverTeam: String,
    var driverNationality: String,
    val country: String = "",
    var formula1Id: Int = 0,
    val id: Int = 0,
    var podium: Int = 0,
    var wasDriverAdded: Boolean = true,
    var trophies: Int = 0,
    var podiums: Int = 0
) {

    // Function to format the list of drivers for display
    fun formatListString(drivers: List<Formula1>): String =
        drivers.joinToString("\n") { driver ->
            "Driver: ${driver.driverName}, Team: ${driver.driverTeam}, Nationality: ${driver.driverNationality}, Trophies: ${driver.trophies}, Podiums: ${driver.podiums}"
        }



}


