package abdulmanov.eduard.itunes.presentation.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment

import abdulmanov.eduard.itunes.R
import abdulmanov.eduard.itunes.domain.models.DetailsAlbum
import abdulmanov.eduard.itunes.presentation.adapters.SongsAdapter
import abdulmanov.eduard.itunes.presentation.app.BaseApp
import abdulmanov.eduard.itunes.presentation.common.loadImg
import abdulmanov.eduard.itunes.presentation.common.visibilityGone
import android.content.Intent
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.container_error.*
import kotlinx.android.synthetic.main.fragment_details_album.*
import javax.inject.Inject

class DetailsAlbumFragment : Fragment(),DetailsAlbumContract.View {

    companion object {
        private const val ALBUM_ID = "ALBUM_ID"

        fun newInstance(albumId: Long): DetailsAlbumFragment {
            return DetailsAlbumFragment().apply {
                arguments = Bundle().apply {
                    putLong(ALBUM_ID, albumId)
                }
            }
        }
    }

    @Inject
    lateinit var presenter: DetailsAlbumContract.Presenter<DetailsAlbumContract.View>

    private lateinit var sendMenuItem: MenuItem

    private var albumId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApp.instance.appComponent.fragmentComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getLong(ALBUM_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_album, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        presenter.attach(this)
        presenter.fetchDetailsAlbum(albumId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    override fun showProgressBar(show: Boolean) {
        details_album_progress_bar.visibilityGone(show)
        details_album_content.visibilityGone(!show)
    }

    override fun showData(data: DetailsAlbum) {
        details_album_image.loadImg(data.artworkUrl, R.drawable.placeholder)
        details_album_name.text = data.collectionName
        details_album_artist.text = data.artistName
        details_album_count_track.text = resources.getQuantityString(
            R.plurals.details_album_count_track,
            data.trackCount,
            data.trackCount
        )
        details_album_release_date.text = data.releaseDate.split("-").run {
            val year = this[0]
            val month =
                this@DetailsAlbumFragment.resources.getStringArray(R.array.details_album_month)[this[1].toInt()]
            val day = this[2].toInt()
            "$day $month $year"
        }
        (details_album_tracks_recycler_view.adapter as SongsAdapter).updateItems(data.songs)

        sendMenuItem.run {
            isVisible = true
            setOnMenuItemClickListener {
                val intentSend = Intent(Intent.ACTION_SEND)
                intentSend.type = "text/plain"
                intentSend.putExtra(Intent.EXTRA_TEXT, data.collectionViewUrl)
                startActivity(intentSend)
                true
            }
        }
    }

    override fun showError(message: Int) {
        details_album_content.visibilityGone(false)
        container_error.visibilityGone(true)
        error_secondary_message.setText(message)
    }

    override fun showRefreshProgressBar(show: Boolean) {
        error_refresh_progress_bar.visibilityGone(show)
        error_refresh_button.visibilityGone(!show)
    }

    override fun hideError() {
        details_album_content.visibilityGone(true)
        container_error.visibilityGone(false)
    }

    private fun initUI() {
        initToolbar()
        initRecyclerView()
        initErrorRefreshButton()
    }

    private fun initToolbar() {
        details_album_toolbar.run {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }

            sendMenuItem = menu.add(Menu.NONE, 0, Menu.NONE, R.string.details_album_send).apply {
                setIcon(R.drawable.ic_send)
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                isVisible = false
            }
        }
    }

    private fun initRecyclerView() {
        details_album_tracks_recycler_view.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = SongsAdapter()
        }
    }

    private fun initErrorRefreshButton() {
        error_refresh_button.setOnClickListener {
            presenter.refresh(albumId)
        }
    }

}
