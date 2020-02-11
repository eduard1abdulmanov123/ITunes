package abdulmanov.eduard.itunes.data.mappers

import abdulmanov.eduard.itunes.data.models.AlbumsResponse
import abdulmanov.eduard.itunes.domain.models.Album
import abdulmanov.eduard.itunes.domain.models.DetailsAlbum
import okhttp3.ResponseBody

interface ITunesMapper {

    fun albumsResponseToAlbums(albumsResponse: AlbumsResponse):List<Album>

    fun songsFromAlbumToDetailsAlbum(response:ResponseBody): DetailsAlbum
    
}