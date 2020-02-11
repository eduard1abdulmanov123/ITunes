package abdulmanov.eduard.itunes.data.models

data class AlbumsResponse(
    val resultCount:Int,
    val results:List<AlbumDTO>
)