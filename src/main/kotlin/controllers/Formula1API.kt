package controllers

import ie.setu.models.Formula1
import ie.setu.models.Team
import models.Driver

class Formula1API(private val drivers: MutableList<Team> = mutableListOf()) {

    private var formulas1 = ArrayList<Formula1>()

    // Constructor to initialize teams (if needed)
    init {
        drivers.addAll(
            listOf(
                Team("Red Bull Racing", "United Kingdom"),
                Team("Ferrari", "Italy"),
                Team("Mercedes", "Germany")
            )
        )
    }

    // Searching for teams in the United Kingdom
    fun searchTeamByCountry(country: String) {
        val filteredTeams = drivers.filter { it.teamLocation.contains(country, ignoreCase = true) }

        if (filteredTeams.isNotEmpty()) {
            println(filteredTeams.joinToString("\n") { "Team: ${it.teamName}, Location: ${it.teamLocation}" })
        } else {
            println("No teams found in $country.")
        }
    }

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
    fun listAllDrivers(): List<Driver> {
        val driverList = mutableListOf<Driver>()

        drivers.forEach { team ->
            driverList.add(Driver(driverName = team.teamName, driverNationality = "Netherlands", driverTeam = team.teamLocation))
        }

        return driverList
    }


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
    //  LISTING ALL TEAMS USING LAMBDA
    // ----------------------------------------------
    fun listAllTeams(): List<Team> {
        return drivers.filter { it.teamName.isNotEmpty() } // Lambda to filter non-empty team names
    }

}
