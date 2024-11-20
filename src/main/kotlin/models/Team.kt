package ie.setu.models

data class Team(
    var teamName: String = "Red Bull", // Default value for teamName
    var teamLocation: String = "Milton Keynes", // Default value for teamLocation
    var teamId: Int = 0, // Default value for teamId
    val id: Any = 0, // Default value for id
    val teamAchievements: Int = 0 // Default value for teamAchievements
)

fun main() {
    // Creating a Team object with default values for name, location, and achievements
    val team1 = Team(teamAchievements = 4)  // Uses default name, location, and id, but achievements will be 4

    // Creating a Team object with custom values
    val team2 = Team("Mercedes", "Stuttgart", teamAchievements = 7, id = 1)

    // Printing the Team objects to see their details
    println("Team 1: $team1")
    println("Team 2: $team2")
}
