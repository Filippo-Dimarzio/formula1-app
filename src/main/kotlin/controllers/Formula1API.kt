package controllers

import ie.setu.models.Team
import ie.setu.models.Formula1
import models.Driver

class Formula1API {

    private var formulas1 = ArrayList<Formula1>()
    private val teams = ArrayList<Team>()

    // ----------------------------------------------
    //  For Managing the ID internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR Formula1 ArrayList
    // ----------------------------------------------
    fun add(formula1: Formula1): Boolean {
        formula1.formula1Id = getId()
        return formulas1.add(formula1)
    }

    fun delete(id: Int) = formulas1.removeIf { formula1 -> formula1.formula1Id == id }

    fun update(id: Int, formula1: Formula1?): Boolean {
        val foundFormula1 = findFormula1(id)

        if ((foundFormula1 != null) && (formula1 != null)) {
            foundFormula1.driverName = formula1.driverName
            foundFormula1.driverTeam = formula1.driverTeam
            foundFormula1.driverNationality = formula1.driverNationality
            return true
        }

        return false
    }

    private fun findFormula1(id: Int): Formula1? {
        return formulas1.find { it.formula1Id == id }
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR Formula1 ArrayList
    // ----------------------------------------------
    fun listAllDrivers(): ArrayList<Driver> = formulas1
        //if (formulas1.isEmpty()) "No drivers stored"
        //else formatListString(formulas1)

    fun listDriversTeam(): String =
        if (formulas1.isEmpty()) "No teams stored"
        else formatListString(formulas1.filter { it.driverTeam.isNotEmpty() })

    fun listDriversByNationality(): String =
        if (formulas1.isEmpty()) "No drivers stored"
        else formatListString(formulas1.filter { it.driverNationality.isNotEmpty() })

    // ----------------------------------------------
    //  COUNTING METHODS FOR Formula1 ArrayList
    // ----------------------------------------------
    fun numberOfDrivers() = formulas1.size

    fun numberOfTeams(): Int = formulas1.count { formula1 -> formula1.driverTeam.isNotEmpty() }

    fun numberOfTeamAchievements(): Int = formulas1.count { formula1 -> formula1.driverTeam.isNotEmpty() }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ----------------------------------------------
    fun findDriver(id: Int): Formula1? =
        formulas1.find { formula1 -> formula1.formula1Id == id }

    fun searchDriverByNationality(searchString: String): String =
        formatListString(
            formulas1.filter { formula1 ->
                formula1.driverNationality.contains(searchString, ignoreCase = true)
            }
        )

    private fun formatListString(formula1s: List<Formula1>): String {

        return formula1s.joinToString("\n") { "Driver: ${it.driverName}, Team: ${it.driverTeam}, Nationality: ${it.driverNationality}" }
    }

    // ----------------------------------------------
    //  TEAM METHODS
    // ----------------------------------------------

    fun addTeam(team: Team): Boolean {
        return if (team.teamName.isNotEmpty() && team.teamLocation.isNotEmpty()) {
            // Add the team to the list
            teams.add(team)
            true  // Successfully added the team
        } else {
            false  // Team name or location is empty, so the team is not added
        }
    }

    fun listAllTeams(): List<Team> {
        // Generate IDs dynamically or using a counter
        return teams.ifEmpty {
            val teamList = mutableListOf<Team>()
            teamList.add(Team(id = 1, teamName = "Red Bull Racing", teamLocation = "Milton Keynes"))
            teamList.add(Team(id = 2, teamName = "Scuderia Ferrari", teamLocation = "Monza"))
            teamList.add(Team(id = 3, teamName = "Alpine F1", teamLocation = "Oxfordshire"))
            teamList
        }
    }

    fun deleteTeam(teamId: Int): Boolean {
        // Find the team by its ID
        val teamToDelete = teams.find { it.id == teamId }

        return if (teamToDelete != null) {
            // Remove the team from the list
            teams.remove(teamToDelete)
            true  // Successfully deleted the team
        } else {
            false  // Team not found, so deletion fails
        }
    }

    fun findTeamById(teamId: Int): Team? {
        return teams.find { it.id == teamId }
    }
}
