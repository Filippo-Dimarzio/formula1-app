package ie.setu.models

data class Formula1(
    var driverName: String,
    var driverTeam: String,
    var driverNationality: String,
    val country: String = "",  // Default value empty string
    var formula1Id: Int = 0,  // Default value 0
    val id: Int = 0,  // Default value 0
    val podium: Int = 0,  // Default value 0
    val wasDriverAdded: Boolean = true  // Default value true


    ) {

    fun formatListString(drivers: List<Formula1>): String =
        drivers.joinToString("\n") { driver ->
            "Driver: ${driver.driverName}, Team: ${driver.driverTeam}, Nationality: ${driver.driverNationality}"
        }





}

