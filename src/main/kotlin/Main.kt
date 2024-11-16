package ie.setu

import controllers.Formula1API
import ie.setu.models.Formula1
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess


private val formula1API = Formula1API()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addDriver()
            2 -> listDriver()
            3 -> updateDriver()
            4 -> deleteDriver()
            5 -> addAttributesToDriver()
            6 -> listDriverAttributes()
            7 -> updateAttributesToDriver()
            8 -> deleteDriverAttributes()
            9 -> searchDriverByCountry()
            10 -> markDriverExists()
            11 -> addTeam()
            12 -> addTeamLocation()
            13 -> listTeamLocation()
            14 -> updateTeamDetails()
            15 -> deleteTeam()
            16 -> listTeamDetails()
            17 -> askUserToChooseDriver()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}



fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                  FORMULA 1 MENU APP               |
         > -----------------------------------------------------  
         > | DRIVER MENU                                       |
         > |   1) Add a Driver                                 |
         > |   2) List a Driver Details                        |
         > |   3) Update a Driver Details                      |
         > |   4) Delete a Driver Details                      |
         > -----------------------------------------------------  
         > | ATTRIBUTES MENU                                   | 
         > |   5) Add Attributes to Driver (Trophy & Podium)   |
         > |   6) List Driver Attributes                       |
         > |   7) Update Driver Attributes                     |
         > |   8) Delete Driver Attributes                     | 
         > |                                                   |
         > |   9) Mark Driver Exists                          |
         > -----------------------------------------------------  
         > | TEAM MENU                                         | 
         > |   10) Add a Team                                  |
         > |   11) Add a Team Location                         |
         > |   12) List Team Location                          |
         > |   13) Update Team Details                         | 
         > |   14) Delete Team                                 |
         > |   15) List Team Details                           |
         > -----------------------------------------------------
         > | REPORT MENU FOR DRIVER                            | 
         > |   16) Search for all drivers (by driver team)     |
         > |   17) Search Driver By Country                                       |
         > |   19) .....                                       |
         > |   20) .....                                       |
         > |   21) .....                                       |
         > -----------------------------------------------------  
         > | REPORT MENU FOR ATTRIBUTES                        |                                
         > |   22) Search for all driver (by Podium Number)    |
         > |                                                   |
         > |                                                   |
         > |                                                   |
         > |                                                   |
         > -----------------------------------------------------
         > | REPORT MENU FOR TEAM                              | 
         > |                                                   |
         > |                                                   |
         > |                                                   |
         > |                                                   | 
         > |                                                   |
         > |                                                   |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

//------------------------------------
// DRIVER MENU
//------------------------------------

fun addDriver() {
    val driverName = readNextLine("Enter a driver's name: ")
    val driverTeam = readNextLine("Enter a driver's team: ")
    val driverNationality = readNextLine("Enter a driver's nationality: ")
    val isAdded = formula1API.add(Formula1(driverName = driverName, driverTeam = driverTeam, driverNationality = driverNationality))

    if (isAdded) {
        println("Driver added successfully.")
    } else {
        println("Failed to add driver.")
    }
}

fun listDriver() {
    if (Formula1API.numberOfDrivers() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL Drivers        |
                  > |   2) View Driver's Team      |
                  > |   3) View Nationality        |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> (listAllDrivers())
            2 -> (listDriversTeam())
            3 -> (listDriversByNationality())
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No drivers stored")
    }
}


fun listAllDrivers() = println(formula1API.listAllDrivers())
fun listDriversTeam() = println(formula1API.listDriversTeam())
fun listDriversByNationality() = println(formula1API.listDriversByNationality())




fun updateDriver() {
    listDriver()
    if (formula1API.numberOfDrivers() > 0) {

        val id = readNextInt("Enter the ID of the driver to update: ")
        val driver = formula1API.findDriver(id)

        if (driver != null) {
            // Prompt for new details
            val driverName = readNextLine("Enter new driver name: ")
            val driverTeam = readNextLine("Enter new driver team: ")
            val driverNationality = readNextLine("Enter new driver nationality: ")

            // Update the existing driver object with new details
            driver.driverName = driverName
            driver.driverTeam = driverTeam
            driver.driverNationality = driverNationality

            if (formula1API.update(id, driver)) {
                println("Driver updated successfully.")
            } else {
                println("Failed to update driver.")
            }
        } else {
            println("No driver found with this ID.")
        }
    } else {
        println("No drivers available to update.")
    }
}


fun deleteDriver() {
    listDriver()
    if (formula1API.numberOfDrivers() > 0) {
        val id = readNextInt("Enter the ID of the driver to delete: ")

        if (formula1API.delete(id)) {
            println("Driver deleted successfully.")
        } else {
            println("Failed to delete driver.")
        }
    }
}

//------------------------------------
// ATTRIBUTES MENU
//------------------------------------

fun addAttributesToDriver() {
    println("Feature not implemented yet.")
}

fun listDriverAttributes() {
    println("Feature not implemented yet.")
}

fun updateAttributesToDriver() {
    println("Feature not implemented yet.")
}

fun deleteDriverAttributes() {
    println("Feature not implemented yet.")
}

fun markDriverExists() {
    println("Feature not implemented yet.")
}

//------------------------------------
// TEAM MENU
//------------------------------------

fun addTeam() {
    println("Feature not implemented yet.")
}

fun addTeamLocation() {
    println("Feature not implemented yet.")
}

fun listTeamLocation() {
    println("Feature not implemented yet.")
}

fun updateTeamDetails() {
    println("Feature not implemented yet.")
}

fun deleteTeam() {
    println("Feature not implemented yet.")
}

fun listTeamDetails() {
    println("Feature not implemented yet.")
}

//------------------------------------
//DRIVER REPORTS MENU
//------------------------------------

fun searchDriverByCountry() {
    TODO("Not yet implemented")
}
//------------------------------------
//TEAM REPORTS MENU
//------------------------------------


//------------------------------------
// EXIT APP
//------------------------------------

fun exitApp() {
    println("Exiting the application. Goodbye!")
    exitProcess(0)
}


//------------------------------------
// DRIVER SELECTION HELPER FUNCTION
//------------------------------------



private fun askUserToChooseDriver(): Formula1? {
    listDriversTeam()
    if (formula1API.numberOfDrivers() > 0) {
        val driver = formula1API.findDriver(readNextInt("\nEnter the id of the driver: "))
        if (driver != null) {
            if (!driver.wasDriverAdded) {
                println("Driver is NOT in the system.")
            } else {
                return driver // chosen driver is valid
            }
        } else {
            println("Driver id is not valid")
        }
    }
    return null // selected driver is not valid
}







