package abdulmanov.eduard.itunes.data.repositories

import abdulmanov.eduard.itunes.data.mappers.ITunesMapper
import abdulmanov.eduard.itunes.data.remote.ITunesSearchApi
import abdulmanov.eduard.itunes.domain.models.Album
import abdulmanov.eduard.itunes.domain.models.DetailsAlbum
import abdulmanov.eduard.itunes.domain.repositories.ITunesRepository
import io.reactivex.Single

class ITunesRepositoryImpl(
    private val iTunesSearchApi: ITunesSearchApi,
    private val mapper:ITunesMapper
):ITunesRepository {

    override fun searchAlbums(query: String): Single<List<Album>> {
        return iTunesSearchApi.searchAlbums(query).map {
            mapper.albumsResponseToAlbums(it)
        }
    }

    override fun fetchDetailsAlbum(albumId: Long): Single<DetailsAlbum> {
        return iTunesSearchApi.fetchSongsFromAlbum(albumId).map {
            mapper.songsFromAlbumToDetailsAlbum(it)
        }
    }

}