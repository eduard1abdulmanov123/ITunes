package abdulmanov.eduard.itunes.presentation.ui.fragments.search

import abdulmanov.eduard.itunes.domain.interactors.SearchUseCase
import abdulmanov.eduard.itunes.presentation.common.SharedPreferencesManager
import abdulmanov.eduard.itunes.presentation.ui.base.BaseMvpPresenter

class SearchPresenter <V:SearchContract.View>(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val searchUseCase: SearchUseCase
):BaseMvpPresenter<V>(),SearchContract.Presenter<V> {

    private var lastQuery:String = ""

    override fun attach(view: V) {
        super.attach(view)
        if (sharedPreferencesManager.lastQuery != null) {
            search(sharedPreferencesManager.lastQuery!!)
        } else {
            this.view?.updateState(SearchContract.State.StartSearchState)
        }
    }

    override fun search(stringSearch: String) {
        view?.updateState(SearchContract.State.LoadingState)
        fetchAlbums(stringSearch)
    }

    override fun refresh() {
        view?.updateState(SearchContract.State.ErrorRefreshState)
        fetchAlbums(lastQuery)
    }

    private fun fetchAlbums(stringSearch: String){
        lastQuery = stringSearch
        searchUseCase.searchAlbums(stringSearch)
            .safeSubscribe(
                {
                    if (it.isNotEmpty()) {
                        sharedPreferencesManager.lastQuery = stringSearch
                        view?.updateState(SearchContract.State.DataState(it))
                    } else {
                        view?.updateState(SearchContract.State.EmptyDataState)
                    }
                },
                {
                    view?.updateState(SearchContract.State.ErrorState(getErrorString(it)))
                }
            )
    }

}
