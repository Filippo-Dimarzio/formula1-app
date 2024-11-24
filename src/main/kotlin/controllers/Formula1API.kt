package controllers

import Team
import ie.setu.models.Formula1
import ie.setu.utils.formatListString
import models.Driver
import java.io.*


/**
 * Formula1API provides functionalities for managing Formula1 drivers and teams,
 * including CRUD operations, searching, and listing drivers and teams.
 *
 * @property drivers A list of Team objects, initialized with default teams.
 */
class Formula1API(
    private val drivers: MutableList<Team> = mutableListOf()
) {

    private var formulas1 = ArrayList<Formula1>()

    /**
     * Initializes the API with default teams.
     */
    init {
        drivers.addAll(
            listOf(
                Team("Red Bull Racing", "United Kingdom"),
                Team("Ferrari", "Italy"),
                Team("Mercedes", "Germany")
            )
        )
    }

    // ----------------------------------------------
    //  For Managing the ID internally in the program
    // ----------------------------------------------

    private var lastId = 0

    /**
     * Generates a unique ID for Formula1 entries.
     */
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR Formula1 ArrayList
    // ----------------------------------------------

    /**
     * Adds a Formula1 object to the list.
     *
     * @param formula1 The Formula1 object to add.
     * @return True if the object is successfully added.
     */
    fun add(formula1: Formula1): Boolean {
        formula1.formula1Id = getId()
        return formulas1.add(formula1)
    }

    /**
     * Deletes a Formula1 object by ID.
     *
     * @param id The ID of the Formula1 object to delete.
     * @return True if the object is successfully deleted.
     */
    fun delete(id: Int) = formulas1.removeIf { formula1 -> formula1.formula1Id == id }

    /**
     * Updates a Formula1 object by ID.
     *
     * @param id The ID of the Formula1 object to update.
     * @param formula1 The updated Formula1 object.
     * @return True if the object is successfully updated.
     */
    fun update(id: Int, formula1: Formula1?): Boolean {
        val foundFormula1 = findFormula1(id)

        if ((foundFormula1 != null) && (formula1 != null)) {
            foundFormula1.driverName = formula1.driverName
            foundFormula1.driverTeam = formula1.driverTeam
            foundFormula1.driverNationality = formula1.driverNationality
            return true
        }

        return false
    }

    /**
     * Finds a Formula1 object by ID.
     *
     * @param id The ID of the Formula1 object to find.
     * @return The Formula1 object if found, null otherwise.
     */
    private fun findFormula1(id: Int): Formula1? {
        return formulas1.find { it.formula1Id == id }
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR Formula1 ArrayList
    // ----------------------------------------------

    /**
     * Lists all drivers based on the initialized teams.
     *
     * @return A list of Driver objects.
     */
    fun listAllDrivers(): List<Driver> {
        val driverList = mutableListOf<Driver>()

        drivers.forEach { team ->
            driverList.add(
                Driver(
                    driverName = team.teamName,
                    driverNationality = "Netherlands",
                    driverTeam = team.teamLocation
                )
            )
        }

        return driverList
    }

    /**
     * Lists teams of drivers.
     *
     * @return A formatted string of teams.
     */
    fun listDriversTeam(): String =
        if (formulas1.isEmpty()) "No teams stored"
        else formatListString(formulas1.filter { it.driverTeam.isNotEmpty() })

    /**
     * Lists drivers by their nationality.
     *
     * @return A formatted string of drivers by nationality.
     */
    fun listDriversByNationality(): String =
        if (formulas1.isEmpty()) "No drivers stored"
        else formatListString(formulas1.filter { it.driverNationality.isNotEmpty() })

    // ----------------------------------------------
    //  COUNTING METHODS FOR Formula1 ArrayList
    // ----------------------------------------------
    /**
     * Counts the total number of drivers.
     *
     * @return The number of drivers.
     */
    fun numberOfDrivers() = formulas1.size

    /**
     * Counts the total number of teams.
     *
     * @return The number of teams.
     */
    fun numberOfTeams(): Int = formulas1.count { formula1 -> formula1.driverTeam.isNotEmpty() }

    /**
     * Counts the number of team achievements.
     *
     * @return The number of team achievements.
     */
    fun numberOfTeamAchievements(): Int = formulas1.count { formula1 -> formula1.driverTeam.isNotEmpty() }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ----------------------------------------------
    /**
     * Finds a driver by ID.
     *
     * @param id The ID of the driver.
     * @return The Formula1 object if found, null otherwise.
     */
    fun findDriver(id: Int): Formula1? =
        formulas1.find { formula1 -> formula1.formula1Id == id }

    /**
     * Searches for drivers by their nationality.
     *
     * @param searchString The nationality to search for.
     * @return A formatted string of drivers with the matching nationality.
     */
    fun searchDriverByCountry(searchString: String): String {
        val result = formulas1.filter { formula1 ->
            formula1.driverNationality.contains(searchString, ignoreCase = true)
        }.joinToString("\n") {
            "Driver: ${it.driverName}, Team: ${it.driverTeam}, Nationality: ${it.driverNationality}"
        }

        return if (result.isNotEmpty()) result else "No drivers found with nationality $searchString"
    }

    /**
     * Searches for teams by their country.
     *
     * @param country The country to search for.
     */
    fun searchTeamByCountry(country: String) {
        val filteredTeams = drivers.filter { it.teamLocation.contains(country, ignoreCase = true) }

        if (filteredTeams.isNotEmpty()) {
            println(filteredTeams.joinToString("\n") { "Team: ${it.teamName}, Location: ${it.teamLocation}" })
        } else {
            println("No teams found in $country.")
        }
    }

    // ----------------------------------------------
    //  LISTING ALL TEAMS USING LAMBDA
    // ----------------------------------------------
    /**
     * Lists all teams with non-empty names.
     *
     * @return A list of Team objects.
     */
    fun listAllTeams(): List<Team> {
        return drivers.filter { it.teamName.isNotEmpty() } // Lambda to filter non-empty team names
    }

    @Throws(Exception::class)
    fun load(fileName: String) {
        val file = File(fileName)
        if (file.exists()) {
            ObjectInputStream(FileInputStream(file)).use { input ->
                formulas1 = input.readObject() as ArrayList<Formula1>
            }
        } else {
            println("File not found.")
        }
    }

    @Throws(Exception::class)
    fun save(fileName: String) {
        try {
            val file = File(fileName)
            ObjectOutputStream(FileOutputStream(file)).use { output ->
                output.writeObject(formulas1)
            }
        } catch (e: Exception) {
            println("Error saving data: ${e.message}")
        }
    }




}
