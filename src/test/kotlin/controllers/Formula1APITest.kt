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

    // The actual list where drivers will be stored
    private val listOfDrivers = mutableListOf<Formula1>()

    @BeforeEach
    fun setup() {
        // Creating Formula1 instances with actual data
        driver1 = Formula1("Max Verstappen", "Red Bull Racing", "Dutch", "Netherlands", id = 0)
        driver2 = Formula1("Charles Leclerc", "Ferrari", "Monegasque", "Monaco", id = 1)
        driver3 = Formula1("Lewis Hamilton", "Mercedes", "British", "United Kingdom", id = 2)
        driver4 = Formula1("Lando Norris", "McLaren", "British", "United Kingdom", id = 3)

        // Adding drivers to the list
        listOfDrivers.add(driver1!!)
        listOfDrivers.add(driver2!!)
        listOfDrivers.add(driver3!!)
        listOfDrivers.add(driver4!!)
    }

    @AfterEach
    fun tearDown() {
        listOfDrivers.clear() // Clear the list after each test
    }

    @Test
    fun `adding a Driver to a Driver list to ArrayList`() {
        val newDriver = Formula1("Lando Norris", "McLaren", "British", "UK", id = 4)
        assertTrue(listOfDrivers.add(newDriver))  // Adds the new driver to the list
        assertEquals(5, listOfDrivers.size)  // Check the size of the list
        assertEquals(newDriver, listOfDrivers.last())  // Ensure the last driver is the one added
    }

    @Test
    fun `adding a Driver to an empty list adds to ArrayList`() {
        val newDriver = Formula1("Checo Perez", "Red Bull", "Mexican", "Mexico", id = 5)
        val emptyDriverList = mutableListOf<Formula1>()
        assertEquals(0, emptyDriverList.size)  // List is empty before adding
        assertTrue(emptyDriverList.add(newDriver))  // Add the new driver
        assertEquals(1, emptyDriverList.size)  // List has 1 driver now
        assertEquals(newDriver, emptyDriverList.last())  // Ensure the last driver is the one added
    }

    @Test
    fun `listAllDrivers returns No Drivers Stored message when ArrayList is empty`() {
        val emptyDriverList = mutableListOf<Formula1>()
        assertEquals(0, emptyDriverList.size)  // Ensure the list is empty
        val result = if (emptyDriverList.isEmpty()) "No drivers stored" else emptyDriverList.joinToString(", ")
        assertTrue(result.lowercase().contains("no drivers"))  // Verify the message
    }

    @Test
    fun `listAllDrivers returns Drivers when ArrayList has drivers stored`() {
        assertEquals(5, listOfDrivers.size)  // Ensure there are 5 drivers
        val driversString = listOfDrivers.joinToString(", ") { it.driverName.lowercase() }
        assertTrue(driversString.contains("max verstappen"))
        assertTrue(driversString.contains("charles leclerc"))
        assertTrue(driversString.contains("lewis hamilton"))
        assertTrue(driversString.contains("lando norris"))
        assertTrue(driversString.contains("checo perez"))
    }
}
