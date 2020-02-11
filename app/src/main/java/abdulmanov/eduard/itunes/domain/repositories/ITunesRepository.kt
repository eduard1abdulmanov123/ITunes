package abdulmanov.eduard.itunes.domain.repositories

import abdulmanov.eduard.itunes.domain.models.Album
import abdulmanov.eduard.itunes.domain.models.DetailsAlbum
import io.reactivex.Single

interface ITunesRepository {

    fun searchAlbums(query:String):Single<List<Album>>

    fun fetchDetailsAlbum(albumId:Long):Single<DetailsAlbum>

}