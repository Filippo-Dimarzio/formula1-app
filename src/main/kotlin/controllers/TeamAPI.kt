package ie.setu.controllers

import Team
import ie.setu.persistence.Serializer

class TeamAPI(serializerType: Serializer) {


    private var teams = mutableListOf<Team>()
    private var serializer: Serializer = serializerType

    // Adds a team with a unique ID
    fun addTeam(team: Team): Boolean {
        return if (team.teamName.isNotEmpty() && team.teamLocation.isNotEmpty()) {
            if (teams.none { it.id == team.id }) {
                teams.add(team)
                true
            } else {
                false // Duplicate ID
            }
        } else {
            false
        }
    }

    // Returns all teams
    fun getAllTeams(): List<Team> = teams


    fun teamExists(teamId: Int): Team? {
        return teams.find { it.id == teamId }
    }


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
            true
        } else {
            false
        }
    }


    fun findTeamById(teamId: Int): Team? {
        return teams.find { it.id == teamId }
    }

    // Update team details by teamId
    fun updateTeam(teamId: Int, updatedTeam: Team): Boolean {
        // Retrieve the team using the teamId
        val existingTeam = teams.find { it.id == teamId }

        return if (existingTeam != null) {

            existingTeam.teamName = updatedTeam.teamName
            existingTeam.teamLocation = updatedTeam.teamLocation
            existingTeam.teamAchievements = updatedTeam.teamAchievements

            println("Team updated successfully.")
            true
        } else {

            println("Team with ID $teamId not found.")
            false
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
