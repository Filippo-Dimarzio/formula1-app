import models.Driver

data class Team(
    var teamName: String,
    var teamLocation: String,
    var teamId: Int = 1,
    val id: Int = 0,
    var teamAchievements: Int = 0,
    val drivers: List<Driver> = emptyList(),

)
