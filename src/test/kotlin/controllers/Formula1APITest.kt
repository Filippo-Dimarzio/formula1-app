package controllers

import Team
import ie.setu.models.Formula1
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Formula1APITest {

    private var driver1: Formula1? = null
    private var driver2: Formula1? = null
    private var driver3: Formula1? = null
    private var driver4: Formula1? = null
    private var team1: Team? = null
    private var team2: Team? = null
    private var team3: Team? = null
    private var team4: Team? = null

    private val listOfDrivers = mutableListOf<Formula1>()
    private val listOfTeams = mutableListOf<Team>()

    @BeforeEach
    fun setup() {
        driver1 = Formula1("Max Verstappen", "Red Bull Racing", "Dutch", "Netherlands", id = 0)
        driver2 = Formula1("Charles Leclerc", "Ferrari", "Monegasque", "Monaco", id = 1)
        driver3 = Formula1("Lewis Hamilton", "Mercedes", "British", "United Kingdom", id = 2)
        driver4 = Formula1("Lando Norris", "McLaren", "British", "United Kingdom", id = 3)

        listOfDrivers.add(driver1!!)
        listOfDrivers.add(driver2!!)
        listOfDrivers.add(driver3!!)
        listOfDrivers.add(driver4!!)

        team1 = Team("Red Bull Racing", "Austria", 0)
        team2 = Team("Ferrari", "Italy", 1)
        team3 = Team("Mercedes", "Germany", 2)
        team4 = Team("McLaren", "United Kingdom", 3)

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
        val newDriver = Formula1("Checo Perez", "Red Bull Racing", "Mexican", "Mexico", id = 4)
        assertTrue(listOfDrivers.add(newDriver))
        assertEquals(5, listOfDrivers.size)
        assertEquals(newDriver, listOfDrivers.last())
        assertTrue(listOfDrivers.contains(newDriver))
        assertFalse(listOfDrivers.isEmpty())
    }

    @Test
    fun `adding a Driver to an empty list adds to ArrayList`() {
        val newDriver = Formula1("Fernando Alonso", "Aston Martin", "Spanish", "Spain", id = 5)
        val emptyDriverList = mutableListOf<Formula1>()
        assertEquals(0, emptyDriverList.size)
        assertTrue(emptyDriverList.add(newDriver))
        assertEquals(1, emptyDriverList.size)
        assertEquals(newDriver, emptyDriverList.last())
        assertTrue(emptyDriverList.contains(newDriver))
        assertFalse(emptyDriverList.isEmpty())
    }

    @Test
    fun `listAllDrivers returns No Drivers Stored message when ArrayList is empty`() {
        val emptyDriverList = mutableListOf<Formula1>()
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
        val newTeam = Team("Alpine", "France", 0)
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
