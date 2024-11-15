import controllers.Formula1API
import ie.setu.models.Formula1
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addDriver()
            2 -> listDriver()
            3 -> updateDriver()
            4 -> deleteDriver()
            5->  listDriverDetails()

            5 -> addAttributesToDriver()
            6 -> listDriverAttributes
            7 -> updateAttributesToDriver()
            8 -> deleteDriverAttributes()

            10 -> markDriverExists()

            11 -> addTeam()
            12 -> addTeamLocation()
            13 -> listTeamLocation()
            14 -> updateTeamDetails()
            15 -> deleteTeam()
            16 -> listTeamDetails()
            17-> searchDriverByCountry()
            // -> ""
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


//DRIVER MENU
fun addDriver() {
    val driverName = readNextLine("Enter a driver's name: ")
    val driverTeam = readNextInt("Enter a driver's team: ")
    val driverNationality = readNextLine("Enter a driver's nationality: ")
    val careerLength = readNextLine( "Enter career length by number of years:")
    val isAdded = Formula1API.add(Formula(driverTitle = driverTitle, driverTeam = driverTeam, driverNationality = driverNationality, careerLength = careerLength))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
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
            1 -> listAllDrivers()
            2 -> listDriversTeam()
            3 -> listDriverDetails()
            4 -> listCareerLength()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listAllDrivers() = println(Formula1API.listAllDrivers())
fun listDriversTeam() = println(Formula1API.listDriversTeam())
fun listDriverDetails() = println(Formula1API.listDriverDetails())
fun listCareerLength() = println(Formula1API.listCareerLength())


fun updateDriver() {
    listDriver()
    if (Formula1API.numberOfDrivers() > 0) {
        // only ask the user to choose the note if notes exist
        val id = readNextInt("Enter the id of the note to update: ")
        if (Formula1API.findDriver(id) != null) {
            val driverName = readNextLine("Enter a driver's name: ")
            val driverTeam = readNextInt("Enter a driver's team: ")
            val driverNationality = readNextLine("Enter a driver nationality: ")

            // pass the index of the driver and the new driver details to Formula1API
            if (Formula1API.update(id, Formula1(0, driverName, driverTeam, driverNationality))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no drivers for this index number")
        }
    }
}

fun deleteDriver() {
    listDriver()
    if (Formula1API.numberOfDrivers() > 0) {
        // only ask the user to choose the note to delete if notes exist
        val id = readNextInt("Enter the id of the driver to delete: ")
        // pass the index of the driver to NoteAPI for deleting and check for success.
        val formula1ToDelete = Formula1API.delete(id)
        if (formula1ToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

//-------------------------------------------
//ATTRIBUTES MENU (only available for active notes)
//-------------------------------------------





//------------------------------------
//TEAMS MENU
//------------------------------------



//------------------------------------
//DRIVER REPORTS MENU
//------------------------------------
fun searchDriverByCountry() {
    val driverName = readNextLine("Enter the description to search by: ")
    val searchResults = Formula1API.searchDriverByName(driverName)
    if (searchResults.isEmpty()) {
        println("No notes found")
    } else {
        println(searchResults)
    }
}

//------------------------------------
//ATTRIBUTES REPORTS MENU
//------------------------------------


//------------------------------------
//TEAM REPORTS MENU
//------------------------------------

//------------------------------------
// Exit App
//------------------------------------
fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}

//------------------------------------
//HELPER FUNCTIONS
//------------------------------------

private fun askUserToChooseDriver(): Formula1? {
    listDriverDetails()
    if (Formula1API.numberOfDrivers() > 0) {
        val note = Formula1API.findDriver(readNextInt("\nEnter the id of the driver: "))
        if (note != null) {
            if (formula1.isDriverInTheSystem) {
                println("Driver is in the System")
            } else {
                return driver
            }
        } else {
            println("Driver id is not valid")
        }
    }
    return null
}
