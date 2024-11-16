package models

data class Driver (var driverId: Int = 0,
                   var driverDetails : String,
                   val formula1: Any,
                   val driverName: String,

                   var isDriverInSystem: Boolean = false) {

    override fun toString(): String {

        val driverContents = ""
        if (isDriverInSystem)
            return "$driverId: $driverContents (Complete)"
        else
            return "$driverId: $driverContents (TODO)"
    }



}
