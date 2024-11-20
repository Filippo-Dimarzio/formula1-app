package ie.setu.models

// Formula1 data class holds driver-related information.
data class Formula1(
    var driverName: String,
    var driverTeam: String,
    var driverNationality: String,
    val country: String = "",
    var formula1Id: Int = 0,
    val id: Int = 0,
    var podium: Int = 0,
    var wasDriverAdded: Boolean = true,
    var trophies: Int = 0,
    var podiums: Int = 0
)

// Function to calculate the number of teams with a non-empty driverTeam
fun numberOfTeams(formulas1: List<Formula1>): Int {
    return formulas1.count { it.driverTeam.isNotEmpty() }  // Count teams where driverTeam is not empty
}

// Main function that runs the program
fun main() {
    // Sample list of Formula1 drivers and their teams
    val formulas1 = listOf(
        Formula1("Lewis Hamilton", "Mercedes", "British"),
        Formula1("Max Verstappen", "Red Bull Racing", "Dutch"),
        Formula1("Charles Leclerc", "Ferrari", "Monégasque"),
        Formula1("Fernando Alonso", "Aston Martin", "Spanish")
    )

    // Call the function to get the number of teams and pass 'formulas1' as a parameter
    val totalTeams = numberOfTeams(formulas1)

    // Print the result
    println("Number of teams: $totalTeams")
}
