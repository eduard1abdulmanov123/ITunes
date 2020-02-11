package abdulmanov.eduard.itunes.data.mappers

import abdulmanov.eduard.itunes.data.models.AlbumDTO
import abdulmanov.eduard.itunes.data.models.AlbumsResponse
import abdulmanov.eduard.itunes.data.models.SongDTO
import abdulmanov.eduard.itunes.domain.models.Album
import abdulmanov.eduard.itunes.domain.models.DetailsAlbum
import abdulmanov.eduard.itunes.domain.models.Song
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject

class ITunesMapperImpl(private val gson: Gson):ITunesMapper {

    override fun albumsResponseToAlbums(albumsResponse: AlbumsResponse): List<Album> {
        return mutableListOf<Album>().apply {
            albumsResponse.results.forEach {
                val album = Album(
                    it.collectionId,
                    it.collectionName,
                    it.artistName,
                    it.artworkUrl100,
                    it.trackCount
                )
                add(album)
            }
        }.sortedBy { it.collectionName }
    }

    override fun songsFromAlbumToDetailsAlbum(response: ResponseBody): DetailsAlbum {
        val resultsJson = JSONObject(response.string()).getJSONArray("results")

        val album = gson.fromJson<AlbumDTO>(resultsJson[0].toString(), AlbumDTO::class.java)
        val songs = mutableListOf<SongDTO>().apply {
            for (i in 1 until resultsJson.length()) {
                val song = gson.fromJson<SongDTO>(resultsJson[i].toString(), SongDTO::class.java)
                add(song)
            }
        }

        return DetailsAlbum(
            album.collectionId,
            album.artistName,
            album.collectionName,
            album.collectionViewUrl,
            album.artworkUrl100.replace("100x100", "200x200"),
            album.trackCount,
            album.releaseDate.substring(0, 10),
            songs.map { Song(it.trackId, it.trackNumber, it.trackName, it.trackTimeMillis) }
        )
    }
    
}