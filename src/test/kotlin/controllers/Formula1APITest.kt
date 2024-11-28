package controllers

import Team
import models.Driver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Formula1APITest {

    private var driver1: Driver? = null
    private var driver2: Driver? = null
    private var driver3: Driver? = null
    private var driver4: Driver? = null
    private var team1: Team? = null
    private var team2: Team? = null
    private var team3: Team? = null
    private var team4: Team? = null

    private val listOfDrivers = mutableListOf<Driver>()
    private val listOfTeams = mutableListOf<Team>()

    @BeforeEach
    fun setup() {
        driver1 = Driver(driverId = 0, driverName = "Max Verstappen", driverNationality = "Dutch", teamName = "Red Bull Racing", teamLocation = "Austria", driverTeam = "Red Bull Racing")
        driver2 = Driver(driverId = 1, driverName = "Charles Leclerc", driverNationality = "Monegasque", teamName = "Ferrari", teamLocation = "Italy", driverTeam =  "Ferrari",)
        driver3 = Driver(driverId = 2, driverName = "Lewis Hamilton", driverNationality = "British", teamName = "Mercedes", teamLocation = "Germany", driverTeam = "Mercedes")
        driver4 = Driver(driverId = 3, driverName = "Lando Norris", driverNationality = "British", teamName = "McLaren", teamLocation = "United Kingdom", driverTeam = "Mclaren")

        listOfDrivers.add(driver1!!)
        listOfDrivers.add(driver2!!)
        listOfDrivers.add(driver3!!)
        listOfDrivers.add(driver4!!)

        team1 = Team(teamName = "Red Bull Racing", teamLocation = "Austria", teamId = 0)
        team2 = Team(teamName = "Ferrari", teamLocation = "Italy", teamId = 1)
        team3 = Team(teamName = "Mercedes", teamLocation = "Germany", teamId = 2)
        team4 = Team(teamName = "McLaren", teamLocation = "United Kingdom", teamId = 3)

        listOfTeams.add(team1!!)
        listOfTeams.add(team2!!)
        listOfTeams.add(team3!!)
        listOfTeams.add(team4!!)
    }

    @AfterEach
    fun tearDown() {
        listOfDrivers.clear()
        listOfTeams.clear()
    }

    @Test
    fun `adding a Driver to a Driver list to ArrayList`() {
        val newDriver = Driver(driverId = 4, driverName = "Sergio Perez", driverNationality = "Mexican", driverTeam = "Red Bull Racing", teamLocation = "Mexico", teamName = "Red Bull Racing" )
        assertTrue(listOfDrivers.add(newDriver))
        assertEquals(5, listOfDrivers.size)
        assertEquals(newDriver, listOfDrivers.last())
        assertTrue(listOfDrivers.contains(newDriver))
        assertFalse(listOfDrivers.isEmpty())
    }

    @Test
    fun `adding a Driver to an empty list adds to ArrayList`() {
        val newDriver = Driver(driverId = 0, driverName = "Fernando Alonso", driverNationality = "Spanish", driverTeam = "Aston Martin", teamLocation = "Spain", teamName = "Aston Martin")
        val emptyDriverList = mutableListOf<Driver>()
        assertEquals(0, emptyDriverList.size)
        assertTrue(emptyDriverList.add(newDriver))
        assertEquals(1, emptyDriverList.size)
        assertEquals(newDriver, emptyDriverList.last())
        assertTrue(emptyDriverList.contains(newDriver))
        assertFalse(emptyDriverList.isEmpty())
    }

    @Test
    fun `listAllDrivers returns No Drivers Stored message when ArrayList is empty`() {
        val emptyDriverList = mutableListOf<Driver>()
        assertEquals(0, emptyDriverList.size)
        val result = if (emptyDriverList.isEmpty()) "No drivers stored" else emptyDriverList.joinToString(", ")
        assertTrue(result.lowercase().contains("no drivers"))
        assertFalse(result.contains("drivers"))
    }

    @Test
    fun `listAllDrivers returns Drivers when ArrayList has drivers stored`() {
        assertEquals(4, listOfDrivers.size)
        val driversString = listOfDrivers.joinToString(", ") { it.driverName.lowercase() }
        assertTrue(driversString.contains("max verstappen"))
        assertTrue(driversString.contains("charles leclerc"))
        assertTrue(driversString.contains("lewis hamilton"))
        assertTrue(driversString.contains("lando norris"))
        assertFalse(driversString.contains("checo perez"))
    }

    @Test
    fun `adding a Team to a Team list to ArrayList`() {
        val newTeam = Team(teamName = "Alpine", teamLocation = "France", teamId = 4)
        assertTrue(listOfTeams.add(newTeam))
        assertEquals(5, listOfTeams.size)
        assertEquals(newTeam, listOfTeams.last())
        assertTrue(listOfTeams.contains(newTeam))
        assertFalse(listOfTeams.isEmpty())
    }

    @Test
    fun `listAllTeams returns No Teams Stored message when ArrayList is empty`() {
        val emptyTeamList = mutableListOf<Team>()
        assertEquals(0, emptyTeamList.size)
        val result = if (emptyTeamList.isEmpty()) "No teams stored" else emptyTeamList.joinToString(", ")
        assertTrue(result.lowercase().contains("no teams"))
        assertFalse(result.contains("teams"))
    }

    @Test
    fun `listAllTeams returns Teams when ArrayList has teams stored`() {
        assertEquals(4, listOfTeams.size)
        val teamsString = listOfTeams.joinToString(", ") { it.teamName.lowercase() }
        assertTrue(teamsString.contains("red bull racing"))
        assertTrue(teamsString.contains("ferrari"))
        assertTrue(teamsString.contains("mercedes"))
        assertTrue(teamsString.contains("mclaren"))
        assertFalse(teamsString.contains("alpine"))
    }
}
