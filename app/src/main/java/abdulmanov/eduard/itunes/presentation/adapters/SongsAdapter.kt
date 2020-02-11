package abdulmanov.eduard.itunes.presentation.adapters

import abdulmanov.eduard.itunes.R
import abdulmanov.eduard.itunes.domain.models.Song
import abdulmanov.eduard.itunes.presentation.common.inflate
import abdulmanov.eduard.itunes.presentation.common.visibilityInvisible
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_list_track.view.*

class SongsAdapter:BaseAdapter<Song>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Song> {
        return ViewHolder(parent.inflate(R.layout.item_list_track))
    }

    inner class ViewHolder(view: View) : BaseAdapter.BaseViewHolder<Song>(view) {

        override fun bind(model: Song, position: Int) {
            itemView.run {
                item_list_track_top_separate.visibilityInvisible(position == 0)
                item_list_track_number.text = model.trackNumber.toString()
                item_list_track_name.text = model.trackName
                item_list_track_time.text = model.trackTimeMillis.getTime()
            }
        }

        private fun Long.getTime(): String {
            val minuteNumber = this / 1000 / 60 % 60
            val secondNumber = this / 1000 % 60

            val minuteString = if (minuteNumber != 0L) "$minuteNumber:" else "00:"
            val secondString =
                if (secondNumber != 0L)
                    if (secondNumber.toString().length == 1)
                        "0$secondNumber"
                    else
                        "$secondNumber"
                else "00"

            return "$minuteString$secondString"
        }
    }
}