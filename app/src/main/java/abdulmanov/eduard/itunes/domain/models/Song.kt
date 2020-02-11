package abdulmanov.eduard.itunes.domain.models

data class Song(
    val trackId:Long,
    val trackNumber:Int,
    val trackName:String,
    val trackTimeMillis:Long
)