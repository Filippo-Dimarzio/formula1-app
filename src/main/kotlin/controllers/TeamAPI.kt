package ie.setu.controllers

import Team
import ie.setu.persistence.Serializer

class TeamAPI(serializerType: Serializer) {


    private var teams = mutableListOf<Team>()
    private var serializer: Serializer = serializerType


    fun addTeam(team: Team): Boolean {
        if (team.teamName.isEmpty() || team.teamLocation.isEmpty()) return false
        if (teams.any { it.id == team.id }) return false
        teams.add(team)
        return true
    }

    fun getAllTeams(): List<Team> = teams

    fun teamExists(teamId: Int): Team? {
        return teams.find { it.id == teamId }
    }

    fun listAllTeams(): List<Team> {
        if (teams.isEmpty()) teams.addAll(defaultTeams)
        return teams
    }

    val defaultTeams = listOf(
        Team(id = 1, teamName = "Red Bull Racing", teamLocation = "Milton Keynes"),
        Team(id = 2, teamName = "Scuderia Ferrari", teamLocation = "Monza"),
        Team(id = 3, teamName = "Alpine F1", teamLocation = "Oxfordshire")
    )


    fun deleteTeam(teamId: Int): Boolean {
        return teams.removeIf { it.id == teamId }
    }

    fun findTeamById(teamId: Int): Team? {
        return teams.find { it.id == teamId }
    }

    // Update team details by teamId
    fun updateTeam(teamId: Int, updatedTeam: Team): Boolean {
        val existingTeam = teams.find { it.id == teamId } ?: return false

        existingTeam.apply {
            teamName = updatedTeam.teamName
            teamLocation = updatedTeam.teamLocation
            teamAchievements = updatedTeam.teamAchievements
        }
        println("Team updated successfully.")
        return true
    }

    fun numberOfTeams(): Int = teams.size


    fun findTeam(i: Int): Team? {
        return teams.getOrNull(i)
    }

    fun add(team: Team) {
        teams.add(team)
    }

    @Throws(Exception::class)
    fun store(s: String) {
        serializer.write(teams)
        println("Team has been stored!")
    }

    fun save(fileName: String) {
        try {
            serializer.save(teams, fileName)
            println("Team has been saved!")
        } catch (e: Exception) {
            System.err.println("Error saving to file: $e")
        }
    }

    @Throws(Exception::class)
    fun load(s: String) {
        teams = serializer.read() as ArrayList<Team>
        println("Team has been loaded")
    }

}
