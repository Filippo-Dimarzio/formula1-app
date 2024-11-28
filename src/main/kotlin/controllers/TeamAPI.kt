package ie.setu.controllers

import Team
import ie.setu.persistence.Serializer

class TeamAPI(serializerType: Serializer) {

    // Fixed variable name to `teams` for consistency
    private var teams = mutableListOf<Team>()
    private var serializer: Serializer = serializerType

    // Adds a team with a unique ID
    fun addTeam(team: Team): Boolean {
        return if (team.teamName.isNotEmpty() && team.teamLocation.isNotEmpty()) {
            if (teams.none { it.id == team.id }) { // Ensures unique ID
                teams.add(team)
                true // Successfully added
            } else {
                false // Duplicate ID
            }
        } else {
            false // Name or location is empty
        }
    }

    // Returns all teams
    fun getAllTeams(): List<Team> = teams

    // Checks if a team exists by its ID and returns the team
    fun teamExists(teamId: Int): Team? {
        return teams.find { it.id == teamId }
    }

    // Lists all teams, with a default list if no teams exist
    fun listAllTeams(): List<Team> {
        if (teams.isEmpty()) {
            teams.addAll(
                listOf(
                    Team(id = 1, teamName = "Red Bull Racing", teamLocation = "Milton Keynes"),
                    Team(id = 2, teamName = "Scuderia Ferrari", teamLocation = "Monza"),
                    Team(id = 3, teamName = "Alpine F1", teamLocation = "Oxfordshire")
                )
            )
        }
        return teams
    }

    // Deletes a team by its ID
    fun deleteTeam(teamId: Int): Boolean {
        val teamToDelete = teams.find { it.id == teamId }
        return if (teamToDelete != null) {
            teams.remove(teamToDelete)
            true // Successfully deleted
        } else {
            false // Team not found
        }
    }

    // Finds a team by its ID
    fun findTeamById(teamId: Int): Team? {
        return teams.find { it.id == teamId }
    }

    // Update team details by teamId
    fun updateTeam(teamId: Int, updatedTeam: Team): Boolean {
        // Retrieve the team using the teamId
        val existingTeam = teams.find { it.id == teamId }

        return if (existingTeam != null) {
            // If the team exists, update its details with the new team information
            existingTeam.teamName = updatedTeam.teamName
            existingTeam.teamLocation = updatedTeam.teamLocation
            existingTeam.teamAchievements = updatedTeam.teamAchievements

            println("Team updated successfully.")
            true // Return true to indicate that the update was successful
        } else {
            // If no team is found with the provided ID, return false
            println("Team with ID $teamId not found.")
            false // Return false as the team was not found
        }
    }
    
    fun numberOfTeams(): Int {
        return teams.size 
    }

    fun findTeam(i: Int): Team? {
        return teams.getOrNull(i) // Returns the team at index i, or null if the index is invalid
    }

    fun add(team: Team) {
        teams.add(team)
    }


    @Throws(Exception::class)
    fun load(s: String) {
        teams = serializer.read() as ArrayList<Team>
    }

    @Throws(Exception::class)
    fun store(s: String) {
        serializer.write(teams)


    }

    fun save(fileName: String) {
        try {
            serializer.save(teams, fileName)
        } catch (e: Exception) {
            System.err.println("Error saving to file: $e")
        }
    }

}
