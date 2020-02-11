package abdulmanov.eduard.itunes.presentation.adapters

import abdulmanov.eduard.itunes.R
import abdulmanov.eduard.itunes.domain.models.Album
import abdulmanov.eduard.itunes.presentation.common.inflate
import abdulmanov.eduard.itunes.presentation.common.loadImg
import abdulmanov.eduard.itunes.presentation.common.visibilityInvisible
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_list_album.view.*

class AlbumsAdapter(private val clickListener:(albumId:Long)->Unit):BaseAdapter<Album>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Album> {
        return ViewHolder(parent.inflate(R.layout.item_list_album))
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder<Album>(itemView) {

        init {
            itemView.setOnClickListener {
                clickListener.invoke(dataList[adapterPosition].collectionId)
            }
        }

        override fun bind(model: Album, position: Int) {
            with(itemView) {
                item_list_album_name.text = model.collectionName
                item_list_album_artist.text = model.artistName
                item_list_album_image.loadImg(model.artworkUrl, R.drawable.placeholder)
                item_list_album_separate.visibilityInvisible(position != dataList.size - 1)
            }
        }
    }

}
