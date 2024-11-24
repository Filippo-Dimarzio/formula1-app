package ie.setu.test

import Team
import ie.setu.controllers.TeamAPI
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TeamAPITest {

    private lateinit var teamAPI: TeamAPI

    @BeforeEach
    fun setup() {
        // Initialize the TeamAPI before each test
        teamAPI = TeamAPI()
    }

    @Test
    fun testAddTeam() {
        // Test adding a valid team
        val team = Team(id = 4, teamName = "McLaren", teamLocation = "Woking")
        val result = teamAPI.addTeam(team)
        assertTrue(result, "Team should be added successfully")
    }

    @Test
    fun testAddDuplicateTeam() {
        // Test adding a team with duplicate ID
        val team1 = Team(id = 5, teamName = "Mercedes", teamLocation = "Stuttgart")
        val team2 = Team(id = 5, teamName = "Mercedes", teamLocation = "Stuttgart")
        teamAPI.addTeam(team1)
        val result = teamAPI.addTeam(team2)
        assertFalse(result, "Team with duplicate ID should not be added")
    }

    @Test
    fun testGetAllTeams() {
        // Test retrieving all teams
        teamAPI.addTeam(Team(id = 6, teamName = "Red Bull", teamLocation = "Milton Keynes"))
        teamAPI.addTeam(Team(id = 7, teamName = "Ferrari", teamLocation = "Maranello"))
        val teams = teamAPI.getAllTeams()
        assertEquals(2, teams.size, "There should be two teams in the list")
    }

    @Test
    fun testUpdateTeam() {
        // Test updating an existing team
        val team = Team(id = 8, teamName = "Aston Martin", teamLocation = "Silverstone")
        teamAPI.addTeam(team)

        val updatedTeam = Team(id = 8, teamName = "Aston Martin Racing", teamLocation = "Silverstone", teamAchievements = 3)
        val result = teamAPI.updateTeam(8, updatedTeam)

        assertTrue(result, "Team should be updated successfully")
        val updatedTeamFromAPI = teamAPI.findTeamById(8)
        assertNotNull(updatedTeamFromAPI, "Updated team should be retrievable")
        assertEquals("Aston Martin Racing", updatedTeamFromAPI?.teamName, "Team name should be updated")
    }

    @Test
    fun testUpdateNonExistentTeam() {
        // Test trying to update a non-existent team
        val team = Team(id = 9, teamName = "Williams", teamLocation = "Grove")
        val updatedTeam = Team(id = 9, teamName = "Williams Racing", teamLocation = "Grove")
        val result = teamAPI.updateTeam(9, updatedTeam)
        assertFalse(result, "Updating a non-existent team should return false")
    }

    @Test
    fun testDeleteTeam() {
        // Test deleting a team
        val team = Team(id = 10, teamName = "Alfa Romeo", teamLocation = "Hinwil")
        teamAPI.addTeam(team)
        val result = teamAPI.deleteTeam(10)
        assertTrue(result, "Team should be deleted successfully")
        val deletedTeam = teamAPI.findTeamById(10)
        assertNull(deletedTeam, "Team should not be found after deletion")
    }

    @Test
    fun testDeleteNonExistentTeam() {
        // Test trying to delete a non-existent team
        val result = teamAPI.deleteTeam(10)
        assertFalse(result, "Deleting a non-existent team should return false")
    }

    @Test
    fun testListAllTeams() {
        // Test the listAllTeams function, adding a few teams
        teamAPI.addTeam(Team(id = 11, teamName = "AlphaTauri", teamLocation = "Faenza"))
        val teams = teamAPI.listAllTeams()
        assertTrue(teams.isNotEmpty(), "Teams list should not be empty")
        assertEquals(4, teams.size, "There should be 4 teams in the list after adding")
    }

    @Test
    fun testTeamExists() {
        // Test teamExists to check if a team exists by ID
        val team = Team(id = 12, teamName = "Haas", teamLocation = "Kannapolis")
        teamAPI.addTeam(team)
        val existingTeam = teamAPI.teamExists(12)
        assertNotNull(existingTeam, "Team should exist with ID 12")
        assertEquals("Haas", existingTeam?.teamName, "Team name should match")
    }

    @Test
    fun testTeamDoesNotExist() {
        // Test teamExists with a non-existent team ID
        val nonExistingTeam = teamAPI.teamExists(999)
        assertNull(nonExistingTeam, "No team should exist with ID 999")
    }
}
