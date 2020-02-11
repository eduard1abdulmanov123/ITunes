package abdulmanov.eduard.itunes.domain.models

data class DetailsAlbum(
    val collectionId:Long,
    val artistName:String,
    val collectionName:String,
    val collectionViewUrl:String,
    val artworkUrl:String,
    val trackCount:Int,
    val releaseDate:String,
    val songs:List<Song>
)