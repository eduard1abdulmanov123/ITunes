package abdulmanov.eduard.itunes.domain.models

data class Album(
    val collectionId:Long,
    val collectionName:String,
    val artistName:String,
    val artworkUrl:String,
    val trackCount:Int
)