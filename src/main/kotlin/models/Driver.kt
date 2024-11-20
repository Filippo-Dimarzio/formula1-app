package models

data class Driver (var driverId: Int = 0,
                   var driverDetails : String,
                   val formula1: Any,
                   val driverName: String,

                   //var driverTeam: String,
                   var driverNationality: String,
                   val country: String = "",
                   var formula1Id: Int = 0,
                   val id: Int = 0,
                   var podium: Int = 0,
                   var wasDriverAdded: Boolean = true,
                   var trophies: Int = 0,
                   var podiums: Int = 0
                   var isDriverInSystem: Boolean = false) {

    override fun toString(): String {

        val driverContents = ""
        if (isDriverInSystem)
            return "$driverId: $driverContents (Complete)"
        else
            return "$driverId: $driverContents (TODO)"
    }



}
