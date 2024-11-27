package ie.setu.test

import Team
import ie.setu.controllers.TeamAPI
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File

class Formula1Tests {

    private var teamAPI: TeamAPI? = null

    @BeforeEach
    fun setup() {
        teamAPI = TeamAPI(XMLSerializer(File("teams.xml"))) // Initialize the nullable teamAPI
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty teams.xml file.
            val storingTeams = teamAPI ?: error("teamAPI is null")
            storingTeams.store("team.xml")

            // Loading the empty teams.xml file into a new object
            val loadedTeams = TeamAPI(XMLSerializer(File("team.xml")))
            loadedTeams.load()

            // Comparing the source of the teams (storingTeams) with the XML loaded teams (loadedTeams)
            assertEquals(0, storingTeams.numberOfTeams())
            assertEquals(0, loadedTeams.numberOfTeams())
            assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't lose data`() {
            // Storing 3 teams to the teams.xml file.
            val storingTeams = teamAPI ?: error("teamAPI is null")
            storingTeams.add(Team(id = 1, teamName = "McLaren", teamLocation = "Woking"))
            storingTeams.add(Team(id = 2, teamName = "Ferrari", teamLocation = "Maranello"))
            storingTeams.add(Team(id = 3, teamName = "Red Bull", teamLocation = "Milton Keynes"))
            storingTeams.(team.json)

            // Loading teams.xml into a different collection
            val loadedTeams = TeamAPI(XMLSerializer(File("team.xml")))
            loadedTeams.load()

            // Comparing the source of the teams (storingTeams) with the XML loaded teams (loadedTeams)
            assertEquals(3, storingTeams.numberOfTeams())
            assertEquals(3, loadedTeams.numberOfTeams())
            assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
            assertEquals(storingTeams.findTeam(0), loadedTeams.findTeam(0))
            assertEquals(storingTeams.findTeam(1), loadedTeams.findTeam(1))
            assertEquals(storingTeams.findTeam(2), loadedTeams.findTeam(2))
        }
    }
}
