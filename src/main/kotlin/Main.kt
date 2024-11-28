package ie.setu

import Team
import controllers.DriverAPI
import ie.setu.controllers.TeamAPI
import io.github.oshai.kotlinlogging.KotlinLogging
import models.Driver
import persistence.JSONSerializer
import utils.readNextInt
import utils.readNextLine
import java.io.File
import kotlin.system.exitProcess




private val teamAPI = TeamAPI(JSONSerializer(File("team.json")))

private val driver1API = DriverAPI(mutableListOf(), JSONSerializer(File("driver.json")))


fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addDriver(0) //works
            2 -> listDriver() //works
            3 -> updateDriver() //works
            4 -> deleteDriver() //works

            5 -> addAttributesToDriver() //works
            6 -> listDriverAttributes(driver1API) //works
            7 -> updateAttributesToDriver() //works
            8 -> deleteDriverAttributes() //works
            //9 -> searchDriverByCountry()
            10 -> markDriverExists() //works

            11 -> addTeamAndLocation() //works
            12 -> listTeamLocation() //works
            13 -> updateTeamDetails() //works
            14 -> deleteTeam() //works
            15 -> listTeamDetails() //works

            16 -> askUserToChooseDriver()


            17 -> saveDriverFile()
            18 -> loadDriverFile()
            19 -> searchDriverByPodiumNumber(60) //works
            20 -> searchTeamByCountry("UK") //works
            21 -> checkTeamExist(0) //works
            22 -> checkTeamsCount() //works
            23 -> saveTeamFile()
            24 -> loadTeamFile()
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
         > -----------------------------------------------------
         > | REPORT MENU                                       | 
         > |   16) Ask User To Choose Driver (by driver team)  |
         > |   17) Save Drivers                                |
         > |   18) Load Drivers                                |
         > -----------------------------------------------------  
         > | REPORT MENU FOR ATTRIBUTES                        |                                
         > |   19) Search for all driver (by Podium Number)    |
         > |                                                   |
         > -----------------------------------------------------
         > | REPORT MENU FOR TEAM                              | 
         > |  20) Search Team by Country                       |
         > |  21) Check Team exist                             |
         > |  22) Check how many teams added                   |
         > |  23) Save Team                                    |
         > |  24) Load Team                                    |
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
    val teamLocation = readNextLine("Enter the team's location: ")


    driver1API.add(
        Driver(
            driverName = driverName, driverNationality = driverNationality, id = 0, podiums = podiums, trophies = trophies, teamName = driverTeam, teamLocation = teamLocation, driverTeam = "Red Bull Racing"
        )
    )
}

fun listDriver() {
    if (driver1API.numberOfDrivers() > 0) {
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

fun listAllDrivers() = println(driver1API.listAllDrivers())
fun listDriversTeam() = println(driver1API.listDriversTeam())
fun listDriversByNationality() = println(driver1API.listDriversByNationality())


fun updateDriver() {
    listDriver()
    if (driver1API.numberOfDrivers() > 0) {
        val id = readNextInt("Enter the ID of the driver to update from index 3: ")
        val driver = driver1API.findDriver(id)

        if (driver != null) {

            val driverName = readNextLine("Enter new driver name: ")
            val driverTeam = readNextLine("Enter new driver team: ")
            val driverNationality = readNextLine("Enter new driver nationality: ")


            driver.driverName = driverName
            driver.teamName = driverTeam
            driver.driverNationality = driverNationality


            if (driver1API.update(id, driver)) {
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
    if (driver1API.numberOfDrivers() > 0) {
        val id = readNextInt("Enter the ID of the driver to delete from index 3: ")

        if (driver1API.delete(id)) {
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
    val driver = askUserToChooseDriver() // Get the driver object
    if (driver != null) {

        driver.trophies = readNextInt("Enter the number of trophies: ")
        driver.podiums = readNextInt("Enter the number of podiums: ")

        // Pass the updated driver object and ID to the update() method
        if (driver1API.update(driver.id, driver)) {
            println("Driver attributes added successfully.")
        } else {
            println("Failed to add driver attributes.")
        }
    } else {
        println("No driver selected.")
    }
}

fun listDriverAttributes(driverAPI: DriverAPI) {
    val drivers = driverAPI.listAllDrivers() // Get the list of drivers
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

        if (driver1API.update(driver.id, driver)) { // Pass both id and driver object
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

        if (driver1API.update(driver.id, driver)) {
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

        if (driver1API.update(driver.id, driver)) {
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

        // Update team properties directly
        team.teamName = newTeamName
        team.teamLocation = newLocation

        // Update the team in the teamAPI
        val updated = teamAPI.updateTeam(teamId, team)

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

fun searchDriverByCountry(searchString: String, driverTeam: String) {
    // Fetch the list of drivers from the DriverAPI
    val formulas1 = driver1API.listAllDrivers()

    // Filter the result by nationality and team
    val result = formulas1.filter { formula1 ->
        formula1.driverNationality.contains(searchString, ignoreCase = true) &&
                formula1.teamName.contains(driverTeam, ignoreCase = true)
    }.joinToString("\n") {
        "Driver: ${it.driverName}, Team: ${it.teamName}, Nationality: ${it.driverNationality}"
    }

    // Print the result
    if (result.isNotEmpty()) {
        println(result)
    } else {
        println("No drivers found with nationality $searchString and team $driverTeam")
    }
}

fun searchDriverByPodiumNumber(podiumNumber: Int) {
    // Assuming driver1API has a method listAllDrivers() which returns a list of Driver objects
    val drivers = driver1API.listAllDrivers()

    // Filter drivers based on the podium number
    val filteredDrivers = drivers.filter { driver ->
        driver.podiums == podiumNumber
    }

    // Display the result
    if (filteredDrivers.isNotEmpty()) {
        println("Drivers with $podiumNumber podium(s):")
        filteredDrivers.forEach { driver ->
            println("Driver: ${driver.driverName}, Team: ${driver.teamName}, Podiums: ${driver.podiums}")
        }
    } else {
        println("No drivers found with $podiumNumber podium(s).")
    }
}
//------------------------------------
//TEAM REPORTS MENU
//------------------------------------

fun searchTeamByCountry(country: String) {

    val teams = driver1API.listAllTeams()


    if (teams.isEmpty()) {
        println("No teams found in the database.")
        return
    }

    val filteredTeams = teams.filter { it.teamLocation.equals(country, ignoreCase = true) }


    if (filteredTeams.isNotEmpty()) {
        println("Teams found in $country:")
        for (team in filteredTeams) {
            println("Team: ${team.teamName}, Location: ${team.teamLocation}")
        }
    } else {
        println("No teams found in $country.")
    }
}


fun checkTeamsCount() {
    val teams = teamAPI.getAllTeams()
    println("Number of teams: ${teams.size}")
}

fun checkTeamExist(teamId: Int) {
    val team = teamAPI.findTeamById(teamId)
    if (team != null) {
        println("Team exists: ${team.teamName}, Location: ${team.teamLocation}")
    } else {
        println("No team found with ID $teamId")
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

private fun askUserToChooseDriver(): Driver? {
    listDriversTeam()
    if (driver1API.numberOfDrivers() > 0) {
        val driver = driver1API.findDriver(readNextInt("\nEnter the id of the driver: "))
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

//Save and Load methods2

fun saveDriverFile() {
    try {
        // Corrected file name as a string literal
        driver1API.save("drivers.xml")
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun loadDriverFile() {
    try {
        // You may need to pass a filename here if required
        driver1API.load("drivers.xml")
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun saveTeamFile() {
    try {
        // Save team data to a file
        teamAPI.save("team.xml")
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun loadTeamFile() {
    try {
        // Load team data from a file
        teamAPI.load("team.xml")
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