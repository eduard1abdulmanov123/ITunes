package abdulmanov.eduard.itunes.domain.interactors

import abdulmanov.eduard.itunes.domain.models.Album
import abdulmanov.eduard.itunes.domain.repositories.ITunesRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchUseCase @Inject constructor(private val iTunesRepository: ITunesRepository) {

    fun searchAlbums(albumName:String):Single<List<Album>>{
       return iTunesRepository.searchAlbums(albumName)
    }

}