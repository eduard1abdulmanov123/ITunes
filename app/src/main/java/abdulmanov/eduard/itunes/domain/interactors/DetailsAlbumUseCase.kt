package abdulmanov.eduard.itunes.domain.interactors

import abdulmanov.eduard.itunes.domain.models.DetailsAlbum
import abdulmanov.eduard.itunes.domain.repositories.ITunesRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsAlbumUseCase @Inject constructor(private val iTunesRepository: ITunesRepository) {

    fun fetchDetailsAlbum(albumId:Long): Single<DetailsAlbum> {
        return iTunesRepository.fetchDetailsAlbum(albumId)
    }
}