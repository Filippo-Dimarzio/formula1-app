package controllers

import ie.setu.controllers.TeamAPI
import ie.setu.persistence.Serializer
import models.Driver
import persistence.XMLSerializer
import java.io.*

/**
 * DriverAPI provides functionalities for managing Formula1 drivers and teams,
 * including CRUD operations, searching, and listing drivers and teams.
 *
 * @property drivers A list of Driver objects, initialized with default drivers.
 */
class DriverAPI(private val teamAPI: MutableList<TeamAPI>, private var serializer: Serializer) {


    private var drivers = ArrayList<Driver>()


    private var lastId = 0

    /**
     * Generates a unique ID for Driver entries.
     *
     * @return The next unique ID.
     */
    private fun getId() = lastId++

    // ----------------------------------------------
    //  INITIALIZATION
    // ----------------------------------------------

    /**
     * Initializes the API with default drivers.
     */
    init {
        drivers.addAll(
            listOf(
                Driver(0, "Max Verstappen", "Dutch", "Netherlands", 0, 0, 100, true, 6, false, "Red Bull Racing", "Milton Keynes", "Red Bull Racing"
                ),
                Driver(1, "Lewis Hamilton", "British", "UK", 1, 7, 95, true, 7, false, "Mercedes AMG F1", "Brackley", "Red Bull Racing"
                ),
                Driver(
                    2, "Sebastian Vettel", "German", "Germany", 2, 4, 80, true, 4, false, "Aston Martin", "Silverstone", "Red Bull Racing"
                )
            )
        )
    }

    // ----------------------------------------------
    //  CRUD METHODS
    // ----------------------------------------------

    /**
     * Adds a driver to the list.
     *
     * @param driver The driver object to add.
     * @return True if the driver was successfully added.
     */
    fun add(driver: Driver): Boolean {
        driver.driverId = getId()
        return drivers.add(driver)
    }

    /**
     * Deletes a driver by ID.
     *
     * @param id The ID of the driver to delete.
     * @return True if the driver was successfully deleted.
     */
    fun delete(id: Int): Boolean = drivers.removeIf { it.driverId == id }

    /**
     * Updates a driver by ID.
     *
     * @param id The ID of the driver to update.
     * @param updatedDriver The updated driver object.
     * @return True if the driver was successfully updated.
     */
    fun update(id: Int, updatedDriver: Driver): Boolean {
        val driver = findDriver(id)
        return if (driver != null) {
            driver.driverName = updatedDriver.driverName
            driver.teamName = updatedDriver.teamName
            driver.driverNationality = updatedDriver.driverNationality
            true
        } else {
            false
        }
    }

    /**
     * Finds a driver by ID.
     *
     * @param id The ID of the driver.
     * @return The Driver object if found, null otherwise.
     */
    fun findDriver(id: Int): Driver? = drivers.find { it.driverId == id }

    // ----------------------------------------------
    //  LISTING METHODS
    // ----------------------------------------------

    /**
     * Lists all drivers.
     *
     * @return A list of all drivers.
     */
    fun listAllDrivers(): List<Driver> = drivers

    /**
     * Lists the teams of drivers.
     *
     * @return A formatted string of teams.
     */
    fun listDriversTeam(): String =
        if (drivers.isEmpty()) "No teams stored"
        else drivers.joinToString("\n") { "Team: ${it.teamName}" }

    /**
     * Lists drivers by their nationality.
     *
     * @return A formatted string of drivers by nationality.
     */
    fun listDriversByNationality(): String =
        if (drivers.isEmpty()) "No drivers stored"
        else drivers.joinToString("\n") { "Driver: ${it.driverName}, Nationality: ${it.driverNationality}" }

    // ----------------------------------------------
    //  COUNTING METHODS
    // ----------------------------------------------

    /**
     * Counts the total number of drivers.
     *
     * @return The number of drivers.
     */
    fun numberOfDrivers(): Int = drivers.size

    /**
     * Counts the total number of teams.
     *
     * @return The number of distinct teams.
     */
    fun numberOfTeams(): Int = drivers.distinctBy { it.teamName }.size

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ----------------------------------------------

    /**
     * Searches for drivers by their nationality.
     *
     * @param searchString The nationality to search for.
     * @return A formatted string of drivers with the matching nationality.
     */
    fun searchDriverByCountry(searchString: String): String {
        val result = drivers.filter {
            it.driverNationality.contains(searchString, ignoreCase = true)
        }.joinToString("\n") {
            "Driver: ${it.driverName}, Team: ${it.teamName}, Nationality: ${it.driverNationality}"
        }
        return if (result.isNotEmpty()) result else "No drivers found with nationality $searchString"
    }

    /**
     * Searches for teams by their country.
     *
     * @param country The country to search for.
     */
    fun searchTeamByCountry(country: String) {
        val filteredTeams = drivers.filter { it.country.contains(country, ignoreCase = true) }
        if (filteredTeams.isNotEmpty()) {
            println(filteredTeams.joinToString("\n") { "Team: ${it.teamName}, Country: ${it.country}" })
        } else {
            println("No teams found in $country.")
        }
    }

    // ----------------------------------------------
    //  PERSISTENCE METHODS
    // ----------------------------------------------

    /**
     * Loads driver data from the serializer.
     *
     * @throws Exception If an error occurs during loading.
     */
    @Throws(Exception::class)
    fun load() {
        try {
            drivers = serializer.read() as ArrayList<Driver>
        } catch (e: Exception) {
            println("Error loading data: ${e.message}")
        }
    }

    /**
     * Saves driver data using the serializer.
     *
     * @throws Exception If an error occurs during saving.
     */
    @Throws(Exception::class)
    fun save(s: String) {
        try {
            serializer.write(drivers)
        } catch (e: Exception) {
            println("Error saving data: ${e.message}")
        }
    }

    /**
     * Loads driver data using an XML serializer.
     *
     * @param serializer The XMLSerializer instance.
     * @return An ArrayList of Driver objects.
     * @throws Exception If an error occurs during loading.
     */
    @Throws(Exception::class)
    fun load(serializer: String): ArrayList<Driver> {
        return serializer.toString() as ArrayList<Driver>
    }

    /**
     * Stores driver data using an XML serializer.
     *
     * @param serializer The XMLSerializer instance.
     * @param drivers The list of drivers to store.
     * @throws Exception If an error occurs during storing.
     */
    @Throws(Exception::class)
    fun store(serializer: XMLSerializer, drivers: ArrayList<Driver>) {
        serializer.write(drivers)
    }

    // ----------------------------------------------
    //  LISTING ALL TEAMS USING LAMBDA
    // ----------------------------------------------

    /**
     * Lists all teams with non-empty names.
     *
     * @return A list of Team objects.
     */
    fun listAllTeams(): List<Driver> {
        return drivers.filter { it.teamName.isNotEmpty() }
    }
}