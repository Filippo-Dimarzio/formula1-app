package ie.setu.models

data class Team(
    var teamName: String,
    var teamLocation: String,
    var teamId: Int = 0,
    val id: Any,
)


{
    constructor(teamName: String, teamLocation: String) :
              this( "Red bull", "Milton Keynes", 0, 0)


}
