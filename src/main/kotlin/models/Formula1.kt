package ie.setu.models

data class Formula1(
    val country: Any,
    val podium: Any,
    var driverName: String,
    var driverNationality: String,
    var driverTeam: String,
    var formula1Id: Int,
    val id: Int,


    ) {

    fun formatListString(drivers: List<Formula1>): String =
        drivers.joinToString("\n") { driver ->
            "Driver: ${driver.driverName}, Team: ${driver.driverTeam}, Nationality: ${driver.driverNationality}"
        }





}

