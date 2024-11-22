package ie.setu.models

// Formula1 data class holds driver-related information.
data class Formula1(
    var driverName: String,
    var driverTeam: String,
    var driverNationality: String,
    var country: String = "",
    var formula1Id: Int = 0,
    var podium: Int = 0,
    var wasDriverAdded: Boolean = true,
    var trophies: Int = 0,
    var podiums: Int = 0,
    var team: Team? = null, // Changed Any to Team? for type safety
    val id: Int
)

// Function to calculate the number of unique teams with a non-empty driverTeam
fun numberOfTeams(formulas1: List<Formula1>): Int {
    return formulas1
        .filter { it.driverTeam.isNotEmpty() } // Filter out empty driverTeam
        .map { it.driverTeam }                // Extract driverTeam names
        .distinct()                           // Remove duplicates
        .count()                              // Count unique teams
}

// Main function that runs the program
fun main() {
    // Sample list of Formula1 drivers and their teams
    val formulas1 = listOf(
        Formula1("Lewis Hamilton", "Mercedes", "British", id = 1),
        Formula1("Max Verstappen", "Red Bull Racing", "Dutch", id = 2),
        Formula1("Charles Leclerc", "Ferrari", "Monégasque", id = 3),
        Formula1("Fernando Alonso", "Aston Martin", "Spanish", id = 4)
    )

    // Call the function to get the number of unique teams
    val totalTeams = numberOfTeams(formulas1)

    // Print the result
    println("Number of unique teams: $totalTeams")
}
