package ie.setu

import Team
import controllers.Formula1API
import ie.setu.controllers.TeamAPI
import ie.setu.models.Formula1
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess



private val formula1API = Formula1API()
private val teamAPI = TeamAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addDriver(0)
            2 -> listDriver()
            3 -> updateDriver()
            4 -> deleteDriver()

            //5 -> addAttributesToDriver()
            6 -> listDriverAttributes(formula1API)
            7 -> updateAttributesToDriver()
            8 -> deleteDriverAttributes()
            9 -> searchDriverByCountry("Dutch")
            10 -> markDriverExists()

            11 -> addTeamAndLocation()
            12 -> listTeamLocation()
            13 -> updateTeamDetails()
            14 -> deleteTeam()
            15 -> listTeamDetails()

            17 -> askUserToChooseDriver()
            18 -> searchTeamByCountry("Netherlands")
            //19 -> numberOfTeams()
            20 -> save()
            21 -> load()
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
         >     9) Search Driver by Nationality                 |                               
         > |   10) Mark Driver Exists                          |
         > -----------------------------------------------------  
         > | TEAM MENU                                         | 
         > |   11) Add a Team and It's Location                |
         > |   12) List Team Location                          |
         > |   13) Update Team Details                         |
         > |   14) Delete Team                                 | 
         > |   15) List Team Details                           |
         > |   16)                                             |
         > -----------------------------------------------------
         > | REPORT MENU                                       | 
         > |   17) Search for all drivers (by driver team)     |
         > |   18) Search Driver By Country                    |
         > |   19) .....                                       |
         > |   20) Save Drivers                                |
         > |   21) Load Drivers                                |
         > -----------------------------------------------------  
         > | REPORT MENU FOR ATTRIBUTES                        |                                
         > |   22) Search for all driver (by Podium Number)    |
         > |                                                   |
         > |                                                   |
         > |                                                   |
         > |                                                   |
         > -----------------------------------------------------
         > | REPORT MENU FOR TEAM                              | 
         > |  23) Search Team by Country                       |
         > |  24) Check how many teams added                   |
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

fun addDriver(id: Int) {
    val driverName = readNextLine("Enter a driver's name: ")
    val driverTeam = readNextLine("Enter a driver's team: ")
    val driverNationality = readNextLine("Enter a driver's nationality: ")
    val trophies = readNextInt("Enter number of trophies: ")
    val podiums = readNextInt("Enter number of podiums: ")

    // Now no need to pass all parameters, as default values will be used for those not provided
    val isAdded = formula1API.add(
        Formula1(
            driverName = driverName,
            driverTeam = driverTeam,
            driverNationality = driverNationality,
            trophies = trophies,
            podiums = podiums,
            id = 0 // Added missing id parameter
        )
    )


    if (isAdded) {
        println("Driver added successfully.")
    } else {
        println("Failed to add driver.")
    }
}




fun listDriver() {
    if (formula1API.numberOfDrivers() > 0) {
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

/*fun addAttributesToDriver() {
    val driver = askUserToChooseDriver() // Get the driver object
    if (driver != null) {
        if (driver.id != null) {  // Check if the driver's ID is not null
            // Update the driver's attributes
            driver.trophies = readNextInt(""Enter the number of trophies: ")
            driver.podiums = readNextInt("Enter the number of podiums: ")

            // Pass the updated driver object to the update() method
            if (formula1API.update(driver)) {
                println("Driver attributes added successfully.")
            } else {
                println("Failed to add driver attributes.")
            }
        } else {
            println("Driver ID is invalid.")
        }
    } else {
        println("No driver selected.")
    }
}

*/




fun listDriverAttributes(formula1API: Formula1API) {
    val drivers = formula1API.listAllDrivers() // Get the list of drivers
    if (drivers.isNotEmpty()) {
        drivers.forEach { driver ->
            println("Driver: ${driver.driverName}")
            println("Trophies: ${driver.trophies}")
            println("Podiums: ${driver.podiums}")
            println("---")
        }
    } else {
        println("No drivers found.")
    }
}

fun updateAttributesToDriver() {
    val driver = askUserToChooseDriver()
    if (driver != null) {
        driver.trophies = readNextInt("Enter the number of trophies: ")
        driver.podiums = readNextInt("Enter the number of podiums: ")

        if (formula1API.update(driver.id, driver)) { // Pass both id and driver object
            println("Driver attributes added successfully.")
        } else {
            println("Failed to add driver attributes.")
        }
    }
}


fun deleteDriverAttributes() {
    val driver = askUserToChooseDriver()
    if (driver != null) {
        driver.trophies = 0
        driver.podiums = 0

        if (formula1API.update(driver.id, driver)) {
            println("Driver attributes deleted successfully.")
        } else {
            println("Failed to delete driver attributes.")
        }
    }
}

fun markDriverExists() {

    val driver = askUserToChooseDriver()
    if (driver != null) {
        driver.wasDriverAdded = true

        if (formula1API.update(driver.id, driver)) {
            println("Driver marked as existing.")
        } else {
            println("Failed to mark driver as existing.")
        }
    }
}


//------------------------------------
// TEAM MENU
//------------------------------------

fun addTeamAndLocation() {
    val teamName = readNextLine("Enter the team name: ")
    val teamLocation = readNextLine("Enter the team location: ")

    // Create and add the team
    val newTeam = Team(teamName = teamName, teamLocation = teamLocation,)
    val isAdded = teamAPI.addTeam(newTeam) // Use TeamAPI to add the team

    if (isAdded) {
        println("Team added successfully.")
    } else {
        println("Failed to add team.")
    }
}


fun listTeamLocation() {
    val teams = teamAPI.getAllTeams() // Fetch all teams using TeamAPI
    if (teams.isNotEmpty()) {
        println("Teams and their locations:")
        teams.forEach {
            println("Team: ${it.teamName}, Location: ${it.teamLocation}")
        }
    } else {
        println("No teams found.")
    }
}


fun updateTeamDetails() {
    listTeamDetails() // List all teams
    val teamId = readNextInt("Enter the team ID to update: ")
    val team = teamAPI.findTeamById(teamId) // Use TeamAPI to find the team

    if (team != null) {
        val newTeamName = readNextLine("Enter new team name: ")
        val newLocation = readNextLine("Enter new location: ")

        // Create a copy or update directly if properties are mutable
        team.apply {
            var teamName = newTeamName
            var teamLocation = newLocation
        }

        // Update the team in the teamAPI
        val updated = teamAPI.updateTeam(teamId, team,)

        if (updated) {
            println("Team updated successfully.")
        } else {
            println("Failed to update the team.")
        }
    } else {
        println("Team with ID $teamId not found.")
    }
}


fun deleteTeam() {
    listTeamDetails() // List all teams
    val teamId = readNextInt("Enter the team ID to delete: ")

    if (teamAPI.deleteTeam(teamId)) { // Use TeamAPI to delete the team
        println("Team deleted successfully.")
    } else {
        println("Failed to delete team. Team with ID $teamId not found.")
    }
}

fun listTeamDetails() {
    val teams = teamAPI.getAllTeams() // Use TeamAPI to get all teams

    if (teams.isNotEmpty()) {
        for (team in teams) { // Use a traditional for loop
            println("Team: ${team.teamName}, Location: ${team.teamLocation}")
        }
    } else {
        println("No teams found.")
    }
}


//------------------------------------
//DRIVER REPORTS MENU
//------------------------------------
// Method to search drivers by country

fun searchDriverByCountry(searchString: String) {
    // Sample list of Formula1 drivers
    val formulas1 = listOf(
        Formula1("Lewis Hamilton", "Mercedes", "British", id = 0),
        Formula1("Max Verstappen", "Red Bull Racing", "Dutch", id = 0),
        Formula1("Charles Leclerc", "Ferrari", "Monégasque", id = 0)
    )

    // Filter and format the result in one step
    val result = formulas1.filter { formula1 ->
        formula1.driverNationality.contains(searchString, ignoreCase = true)
    }.joinToString("\n") {
        "Driver: ${it.driverName}, Team: ${it.driverTeam}, Nationality: ${it.driverNationality}"
    }

    // Print the result
    if (result.isNotEmpty()) {
        println(result)
    } else {
        println("No drivers found with nationality $searchString")
    }
}


//------------------------------------
//TEAM REPORTS MENU
//------------------------------------

fun searchTeamByCountry(country: String) {
    // Get all teams from the API
    val teams = formula1API.listAllTeams()

    // Ensure teams list is not null
    if (teams == null || teams.isEmpty()) {
        println("No teams found in the database.")
        return
    }

    // Filter teams based on location
    val filteredTeams = teams.filter { it.teamLocation.equals(country, ignoreCase = true) }

    // Check if any teams match the criteria
    if (filteredTeams.isNotEmpty()) {
        println("Teams found in $country:")
        for (team in filteredTeams) {
            println("Team: ${team.teamName}, Location: ${team.teamLocation}")
        }
    } else {
        println("No teams found in $country.")
    }
}

fun teamExists(teamId: Int): Boolean {
    val team = teamAPI.findTeamById(teamId)
    return team != null
}

fun listAllTeams() {
    val teams = teamAPI.getAllTeams() // Get all teams from the teamAPI

    if (teams.isNotEmpty()) {
        println("All Teams Report:")
        println("-------------------")
        teams.forEach { team ->
            println("Team ID: ${team.teamId}")
            println("Team Name: ${team.teamName}")
            println("Location: ${team.teamLocation}")
            println("Achievements: ${team.teamAchievements}")
            println() // Adding a blank line between teams
        }
    } else {
        println("No teams found.")
    }
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




//Save and Load methods
fun save() {
    try {
        // Corrected file name as a string literal
        formula1API.save("formula1.xml")
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        // You may need to pass a filename here if required
        formula1API.load("formula1.xml")
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}




//------------------------------------
// EXIT APP
//------------------------------------

fun exitApp() {
    println("Exiting the application. Goodbye!")
    exitProcess(0)
}


