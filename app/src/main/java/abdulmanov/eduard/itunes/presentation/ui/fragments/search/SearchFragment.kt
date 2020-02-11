package abdulmanov.eduard.itunes.presentation.ui.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import abdulmanov.eduard.itunes.R
import abdulmanov.eduard.itunes.presentation.adapters.AlbumsAdapter
import abdulmanov.eduard.itunes.presentation.app.BaseApp
import abdulmanov.eduard.itunes.presentation.common.createObservableChange
import abdulmanov.eduard.itunes.presentation.common.focus
import abdulmanov.eduard.itunes.presentation.common.visibilityGone
import abdulmanov.eduard.itunes.presentation.ui.activities.MainActivity
import abdulmanov.eduard.itunes.presentation.ui.fragments.details.DetailsAlbumFragment
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.container_empty_data.*
import kotlinx.android.synthetic.main.container_error.*
import kotlinx.android.synthetic.main.container_start_search.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragment : Fragment(),SearchContract.View {

    @Inject
    lateinit var presenter: SearchContract.Presenter<SearchContract.View>

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApp.instance.appComponent.fragmentComponent().create().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        presenter.attach(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    override fun updateState(state: SearchContract.State) {
        when (state) {
            is SearchContract.State.StartSearchState -> {
                search_app_bar_layout.visibilityGone(true)
                search_recycler_view.visibilityGone(false)
                search_progress_bar.visibilityGone(false)
                container_start_search.visibilityGone(true)
                container_empty_data.visibilityGone(false)
                container_error.visibilityGone(false)
            }
            is SearchContract.State.LoadingState -> {
                search_app_bar_layout.visibilityGone(true)
                search_recycler_view.visibilityGone(false)
                search_progress_bar.visibilityGone(true)
                container_start_search.visibilityGone(false)
                container_empty_data.visibilityGone(false)
                container_error.visibilityGone(false)
            }
            is SearchContract.State.DataState -> {
                (search_recycler_view.adapter as AlbumsAdapter).updateItems(state.data)
                search_app_bar_layout.visibilityGone(true)
                search_recycler_view.visibilityGone(true)
                search_progress_bar.visibilityGone(false)
                container_start_search.visibilityGone(false)
                container_empty_data.visibilityGone(false)
                container_error.visibilityGone(false)
            }
            is SearchContract.State.EmptyDataState -> {
                search_app_bar_layout.visibilityGone(true)
                search_recycler_view.visibilityGone(false)
                search_progress_bar.visibilityGone(false)
                container_start_search.visibilityGone(false)
                container_empty_data.visibilityGone(true)
                container_error.visibilityGone(false)
            }
            is SearchContract.State.ErrorState -> {
                error_secondary_message.setText(state.message)
                search_app_bar_layout.visibilityGone(false)
                search_edit_text.focus(false)
                search_recycler_view.visibilityGone(false)
                search_progress_bar.visibilityGone(false)
                container_start_search.visibilityGone(false)
                container_empty_data.visibilityGone(false)
                container_error.visibilityGone(true)
                error_refresh_progress_bar.visibilityGone(false)
                error_refresh_button.visibilityGone(true)
            }
            is SearchContract.State.ErrorRefreshState -> {
                search_app_bar_layout.visibilityGone(false)
                search_recycler_view.visibilityGone(false)
                search_progress_bar.visibilityGone(false)
                container_start_search.visibilityGone(false)
                container_empty_data.visibilityGone(false)
                container_error.visibilityGone(true)
                error_refresh_progress_bar.visibilityGone(true)
                error_refresh_button.visibilityGone(false)
            }
        }
    }

    private fun initUI() {
        initRecyclerView()
        initSearchView()
        initErrorRefreshButton()
    }

    private fun initRecyclerView() {
        with(search_recycler_view) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = AlbumsAdapter {
                (activity as? MainActivity)?.showFragment(DetailsAlbumFragment.newInstance(it))
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == SCROLL_STATE_DRAGGING) {
                        search_edit_text.focus(false)
                    }
                }
            })
        }
    }

    private fun initSearchView() {

        search_clear_string.setOnClickListener {
            search_edit_text.setText("")
        }

        search_edit_text.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search_edit_text.focus(false)
                    return true
                }
                return false
            }
        })

        compositeDisposable.add(search_edit_text.createObservableChange()
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                presenter.search(it)
            }
        )
    }

    private fun initErrorRefreshButton() {
        error_refresh_button.setOnClickListener {
            presenter.refresh()
        }
    }

}
