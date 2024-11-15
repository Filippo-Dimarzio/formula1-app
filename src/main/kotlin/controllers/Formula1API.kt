package controllers

import ie.setu.models.Formula1
import listDriversTeam
import models.Driver
import utils.formatListString
import java.util.ArrayList

class Formula1API {

    private var formulas1 = ArrayList<Formula1>()

    // ----------------------------------------------
    //  For Managing the ID internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR Formula1 ArrayList
    // ----------------------------------------------
    fun add(driver: Driver): Boolean {
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

    // ----------------------------------------------
    //  LISTING METHODS FOR Formula1 ArrayList
    // ----------------------------------------------
    fun listAllDrivers() =
        if (formulas1.isEmpty()) "No drivers stored"
        else formatListString(formulas1)

    fun listDriversTeam() =
        if (formulas1.count { formula1 -> formula1.isDriversTeam } == 0) "No teams have been added"
        else formatListString(formulas1.filter { formula1 -> formula1.isDriversTeam })

    fun listDriverDetails() =
        if (numberOfDrivers() == 0) "Details have not been added"
        else formatListString(formulas1.filter { formula1 -> formula1.isDriverInSystem })

    // ----------------------------------------------
    //  COUNTING METHODS FOR Formula1 ArrayList
    // ----------------------------------------------
    fun numberOfDrivers() = formulas1.size

    fun numberOfTeams(): Int =
        formulas1.count { formula1 -> formula1.isTeamInSystem }

    fun numberOfDriverAchievements(): Int =
        formulas1.count { formula1 -> !formula1.isDriverInSystem }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ----------------------------------------------
    fun findFormula1(id: Int) =
        formulas1.find { formula1 -> formula1.formula1Id == id }

    fun searchDriverByName(searchString: String) =
        formatListString(
            formulas1.filter { formula1 ->
                formula1.driverName.contains(searchString, ignoreCase = true)
            }
        )
}
