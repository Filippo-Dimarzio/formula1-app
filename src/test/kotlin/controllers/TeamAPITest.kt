package ie.setu.test

import Team
import ie.setu.controllers.TeamAPI
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TeamAPITest {

    private lateinit var teamAPI: TeamAPI

    @BeforeEach
    fun setup() {
        // Initialize the TeamAPI before each test
        teamAPI = TeamAPI()
    }

    @Nested
    inner class AddTeamTests {

        @Test
        fun `adding a team with valid name returns true`() {
            assertTrue(teamAPI.addTeam(Team(id = 4, teamName = "McLaren", teamLocation = "Woking")))
            assertEquals(teamAPI.addTeam(id = 2, teamName = "Ferrari", teamLocation = "Faenza")))
        }

        @Test
        fun `adding a team with duplicate ID returns false`() {
            teamAPI.addTeam(Team(id = 5, teamName = "Mercedes", teamLocation = "Stuttgart"))
            assertFalse(teamAPI.addTeam(Team(id = 5, teamName = "Mercedes", teamLocation = "Stuttgart")))
        }

        @Test
        fun `adding a team with empty name returns false`() {
            assertFalse(teamAPI.addTeam(Team(id = 6, teamName = "", teamLocation = "Woking")))
        }

        @Test
        fun `adding a team with empty location returns false`() {
            assertFalse(teamAPI.addTeam(Team(id = 7, teamName = "Aston Martin", teamLocation = "")))
        }
    }

    @Nested
    inner class UpdateTeamTests {

        @Test
        fun `updating an existing team returns true and updates the team`() {
            teamAPI.addTeam(Team(id = 8, teamName = "Aston Martin", teamLocation = "Silverstone"))
            assertTrue(teamAPI.updateTeam(8, Team(id = 8, teamName = "Aston Martin Racing", teamLocation = "Silverstone")))
            val updatedTeam = teamAPI.findTeamById(8)
            assertNotNull(updatedTeam)
            assertEquals("Aston Martin Racing", updatedTeam?.teamName)
        }

        @Test
        fun `updating a non-existent team returns false`() {
            assertFalse(teamAPI.updateTeam(9, Team(id = 9, teamName = "Williams Racing", teamLocation = "Grove")))
        }

        @Test
        fun `updating a team with empty name returns false`() {
            teamAPI.addTeam(Team(id = 10, teamName = "McLaren", teamLocation = "Woking"))
            assertFalse(teamAPI.updateTeam(10, Team(id = 10, teamName = "", teamLocation = "Woking")))
        }
    }

    @Nested
    inner class DeleteTeamTests {

        @Test
        fun `deleting an existing team returns true`() {
            teamAPI.addTeam(Team(id = 11, teamName = "Alfa Romeo", teamLocation = "Hinwil"))
            assertTrue(teamAPI.deleteTeam(11))
            assertNull(teamAPI.findTeamById(11))
        }

        @Test
        fun `deleting a non-existent team returns false`() {
            assertFalse(teamAPI.deleteTeam(999))
        }
    }

    @Nested
    inner class ListAllTeamsTests {

        @Test
        fun `listing all teams returns the correct list of teams`() {
            teamAPI.addTeam(Team(id = 12, teamName = "AlphaTauri", teamLocation = "Faenza"))
            assertTrue(teamAPI.listAllTeams().isNotEmpty())
            assertEquals(1, teamAPI.listAllTeams().size)
        }
    }

    @Nested
    inner class TeamExistsTests {

        @Test
        fun `team exists by ID returns the correct team`() {
            teamAPI.addTeam(Team(id = 13, teamName = "Haas", teamLocation = "Annapolis"))
            val existingTeam = teamAPI.teamExists(13)
            assertNotNull(existingTeam)
            assertEquals("Haas", existingTeam?.teamName)
        }

        @Test
        fun `team does not exist by ID returns null`() {
            assertNull(teamAPI.teamExists(999))
        }
    }
}
