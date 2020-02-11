package abdulmanov.eduard.itunes.data.models

data class AlbumDTO(
    val wrapperType:String,
    val collectionType:String,
    val artistId:Long,
    val collectionId:Long,
    val amgArtistId:Long,
    val artistName:String,
    val collectionName:String,
    val collectionCensoredName:String,
    val artistViewUrl:String,
    val collectionViewUrl:String,
    val artworkUrl60:String,
    val artworkUrl100:String,
    val collectionPrice:Double,
    val collectionExplicitness:String,
    val trackCount:Int,
    val copyright:String,
    val country:String,
    val currency:String,
    val releaseDate:String,
    val primaryGenreName:String
)