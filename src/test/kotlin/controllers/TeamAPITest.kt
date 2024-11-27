package ie.setu.test

import Team
import ie.setu.controllers.TeamAPI
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File

class TeamAPITest {


    private var teamAPI: TeamAPI? = null // Declare as a class-level property

    @BeforeEach
    fun setup() {
        var teamAPI = TeamAPI(XMLSerializer(File("team.xml")))
    }

    @Nested
    inner class AddTeamTests {

        @Test
        fun `adding a valid team returns true`() {
            assertTrue(teamAPI!!.addTeam(Team(id = 4, teamName = "McLaren", teamLocation = "Woking")))
            assertTrue(teamAPI!!.addTeam(Team(id = 2, teamName = "Ferrari", teamLocation = "Faenza")))
            assertTrue(teamAPI!!.addTeam(Team(id = 1, teamName = "Mercedes", teamLocation = "Silverstone")))
            assertTrue(teamAPI!!.addTeam(Team(id = 5, teamName = "Red Bull", teamLocation = "Milton Keynes")))
            assertTrue(teamAPI!!.addTeam(Team(id = 6, teamName = "Aston Martin", teamLocation = "Silverstone")))
            assertTrue(teamAPI!!.addTeam(Team(id = 7, teamName = "Alpine", teamLocation = "Enstone")))
        }

        @Test
        fun `adding a team with duplicate ID returns false`() {
            teamAPI!!.addTeam(Team(id = 5, teamName = "Mercedes", teamLocation = "Stuttgart"))
            assertFalse(teamAPI!!.addTeam(Team(id = 5, teamName = "Mercedes", teamLocation = "Stuttgart")))
            assertFalse(teamAPI!!.addTeam(Team(id = 5, teamName = "Mercedes", teamLocation = "Stuttgart")))
            assertFalse(teamAPI!!.addTeam(Team(id = 5, teamName = "Mercedes", teamLocation = "Stuttgart")))
        }

        @Test
        fun `adding a team with empty name returns false`() {
            assertFalse(teamAPI!!.addTeam(Team(id = 6, teamName = "", teamLocation = "Woking")))
            assertFalse(teamAPI!!.addTeam(Team(id = 7, teamName = "", teamLocation = "Woking")))
            assertFalse(teamAPI!!.addTeam(Team(id = 8, teamName = "", teamLocation = "Woking")))
        }

        @Test
        fun `adding a team with empty name and location returns false`() {
            assertFalse(teamAPI!!.addTeam(Team(id = 8, teamName = "", teamLocation = "")))
            assertFalse(teamAPI!!.addTeam(Team(id = 9, teamName = "", teamLocation = "Silverstone")))
            assertFalse(teamAPI!!.addTeam(Team(id = 10, teamName = "Aston Martin", teamLocation = "")))
            assertFalse(teamAPI!!.addTeam(Team(id = 11, teamName = "", teamLocation = "")))
        }

        @Test
        fun `adding a valid team returns true and team is added correctly`() {
            val team = Team(id = 11, teamName = "McLaren", teamLocation = "Woking")
            assertTrue(teamAPI!!.addTeam(team))
            assertTrue(teamAPI!!.addTeam(Team(id = 12, teamName = "Williams", teamLocation = "Grove")))
            assertTrue(teamAPI!!.addTeam(Team(id = 13, teamName = "Alfa Romeo", teamLocation = "Bologna")))
            assertTrue(teamAPI!!.addTeam(Team(id = 14, teamName = "Toro Rosso", teamLocation = "Faenza")))
        }

        @Test
        fun `adding a team with invalid data does not modify existing teams`() {
            assertFalse(teamAPI!!.addTeam(Team(id = 12, teamName = "", teamLocation = "London")))
            assertFalse(teamAPI!!.addTeam(Team(id = 12, teamName = "", teamLocation = "Paris")))
            assertFalse(teamAPI!!.addTeam(Team(id = 12, teamName = "", teamLocation = "Berlin")))
            assertFalse(teamAPI!!.addTeam(Team(id = 12, teamName = "", teamLocation = "New York")))

            val existingTeams = teamAPI!!.getAllTeams()
            assertEquals(1, existingTeams.size)
            assertTrue(existingTeams.any { it.teamName == "McLaren" })
            assertFalse(existingTeams.any { it.teamName == "Ferrari" })
        }
    }

    @Nested
    inner class UpdateTeamTests {

        @Test
        fun `updating an existing team returns true and updates the team`() {
            teamAPI!!.addTeam(Team(id = 8, teamName = "Aston Martin", teamLocation = "Silverstone"))
            assertTrue(teamAPI!!.updateTeam(8, Team(id = 8, teamName = "Aston Martin Racing", teamLocation = "Silverstone")))
            assertTrue(teamAPI!!.updateTeam(8, Team(id = 8, teamName = "Red Bull Racing", teamLocation = "Milton Keynes")))
            assertTrue(teamAPI!!.updateTeam(8, Team(id = 8, teamName = "Mercedes", teamLocation = "Brackley")))
        }

        @Test
        fun `updating a non-existent team returns false`() {
            assertFalse(teamAPI!!.updateTeam(9, Team(id = 9, teamName = "Williams Racing", teamLocation = "Grove")))
            assertFalse(teamAPI!!.updateTeam(10, Team(id = 10, teamName = "Ferrari Racing", teamLocation = "Maranello")))
            assertFalse(teamAPI!!.updateTeam(11, Team(id = 11, teamName = "Renault", teamLocation = "Dieppe")))
        }

        @Test
        fun `updating a team with empty name returns false`() {
            teamAPI!!.addTeam(Team(id = 10, teamName = "McLaren", teamLocation = "Woking"))
            assertFalse(teamAPI!!.updateTeam(10, Team(id = 10, teamName = "", teamLocation = "Woking")))
            assertFalse(teamAPI!!.updateTeam(10, Team(id = 10, teamName = "", teamLocation = "Monza")))
            assertFalse(teamAPI!!.updateTeam(10, Team(id = 10, teamName = "", teamLocation = "Barcelona")))
        }
    }

    @Nested
    inner class DeleteTeamTests {

        @Test
        fun `deleting an existing team returns true`() {
            teamAPI!!.addTeam(Team(id = 11, teamName = "Alfa Romeo", teamLocation = "Bologna"))
            assertTrue(teamAPI!!.deleteTeam(11))
            assertTrue(teamAPI!!.deleteTeam(12))
            assertTrue(teamAPI!!.deleteTeam(13))
            assertTrue(teamAPI!!.deleteTeam(14))
        }

        @Test
        fun `deleting a non-existent team returns false`() {
            assertFalse(teamAPI!!.deleteTeam(20))
            assertFalse(teamAPI!!.deleteTeam(25))
            assertFalse(teamAPI!!.deleteTeam(30))
        }
    }

    @Nested
    inner class ListAllTeamsTests {

        @Test
        fun `listing all teams returns the correct list of teams`() {
            teamAPI!!.addTeam(Team(id = 12, teamName = "AlphaTauri", teamLocation = "Faenza"))
            assertTrue(teamAPI!!.listAllTeams().isNotEmpty())
            assertTrue(teamAPI!!.listAllTeams().any { it.teamName == "AlphaTauri" })
            assertTrue(teamAPI!!.listAllTeams().any { it.teamLocation == "Faenza" })
            assertEquals(1, teamAPI!!.listAllTeams().size)
        }
    }

    @Nested
    inner class TeamExistsTests {

        @Test
        fun `team exists by ID returns the correct team`() {
            teamAPI!!.addTeam(Team(id = 13, teamName = "Haas", teamLocation = "Annapolis"))
            assertNotNull(teamAPI!!.teamExists(13))
            assertTrue(teamAPI!!.teamExists(13)?.teamName == "Haas")
            assertTrue(teamAPI!!.teamExists(13)?.teamLocation == "Annapolis")
            assertFalse(teamAPI!!.teamExists(13)?.teamName == "Mercedes")
        }

        @Test
        fun `team does not exist by ID returns null`() {
            assertNull(teamAPI!!.teamExists(59))
            assertNull(teamAPI!!.teamExists(81))
            assertNull(teamAPI!!.teamExists(101))
        }
    }
}
