package abdulmanov.eduard.itunes.data.remote

import abdulmanov.eduard.itunes.data.models.AlbumsResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesSearchApi {

    @GET("search?entity=album&attribute=albumTerm")
    fun searchAlbums(@Query("term") term: String): Single<AlbumsResponse>

    @GET("lookup?entity=song")
    fun fetchSongsFromAlbum(@Query("id") id:Long): Single<ResponseBody>

}