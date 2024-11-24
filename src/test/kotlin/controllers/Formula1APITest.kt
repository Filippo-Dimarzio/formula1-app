package controllers

import ie.setu.models.Formula1
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Formula1APITest {

    private var driver1: Formula1? = null
    private var driver2: Formula1? = null
    private var driver3: Formula1? = null
    private var driver4: Formula1? = null

    @BeforeEach
    fun setup() {
        // Creating Formula1 instances with actual data
        driver1 = Formula1("Max Verstappen", "Red Bull Racing", "Dutch", "Netherlands", id = 0,,)
        driver2 = Formula1("Charles Leclerc", "Ferrari", "Monegasque", "Monaco", id = 0,,)
        driver3 = Formula1("Lewis Hamilton", "Mercedes", "British", "United Kingdom", id = 0,,)
        driver4 = Formula1("Lando Norris", "McLaren", "British", "United Kingdom", id = 0,,)

        // Adding drivers to the list
        listOfDrivers!!.add(driver1!!)
        listOfDrivers!!.add(driver2!!)
        listOfDrivers!!.add(driver3!!)
        listOfDrivers!!.add(driver4!!)
    }

    @AfterEach
    fun tearDown() {
        driver1 = null
        driver2 = null
        driver3 = null
        driver4 = null
    }

    @Test
    fun `adding a Driver to a Driver list to ArrayList`() {
        val newDriver = Formula1("Lando Norris", "Mclaren", "British", "UK", id = 0,,)
        assertTrue(listOfDrivers!!.add(newDriver))
        assertEquals(5, listOfDrivers!!.numberOfDrivers())
        assertEquals(newDriver, listOfDrivers!!.findDriver(listOfDrivers!!.numberOfDrivers() - 1))
    }

    @Test
    fun `adding a Driver to an empty list adds to ArrayList`() {
        val newDriver = Formula1("Checo Perez", "Red Bull", "Mexican", "Mexico", id = 0,,)
        val emptyDriverList = mutableListOf<Formula1>()
        assertEquals(0, emptyDriverList.size)
        assertTrue(emptyDriverList.add(newDriver))
        assertEquals(1, emptyDriverList.size)
        assertEquals(newDriver, emptyDriverList.last())
    }
}

@Test
fun `listAllDrivers returns No Drivers Stored message when ArrayList is empty`(): Unit {
    assertEquals(0, emptyDriverList!!.numberOfDrivers())
    assertTrue(emptyDriverList!!.listAllDrivers().lowercase().contains("no drivers"))
}

object emptyDriverList {
    fun listAllDrivers(): Any {

    }

}

@Test
fun `listAllDrivers returns Drivers when ArrayList has drivers stored`() {
    assertEquals(5, listOfDrivers!!.numberOfDrivers())
    val driversString = listOfDrivers!!.listAllDrivers().lowercase()
    assertTrue(driversString.contains("max verstappen"))
    assertTrue(driversString.contains("charles leclerc"))
    assertTrue(driversString.contains("lewis hamilton"))
    assertTrue(driversString.contains("lando norris"))
    assertTrue(driversString.contains("checo perez"))
}


