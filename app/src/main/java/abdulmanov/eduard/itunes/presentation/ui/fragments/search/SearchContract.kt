package abdulmanov.eduard.itunes.presentation.ui.fragments.search

import abdulmanov.eduard.itunes.domain.models.Album
import abdulmanov.eduard.itunes.presentation.ui.base.MvpPresenter
import abdulmanov.eduard.itunes.presentation.ui.base.MvpView

interface SearchContract {

    interface View:MvpView{
        fun updateState(state:State)
    }

    interface Presenter<V:View>:MvpPresenter<V>{

        fun search(stringSearch:String)

        fun refresh()
    }

    sealed class State {
        object StartSearchState : State()
        object LoadingState : State()
        class DataState(val data:List<Album>):State()
        object EmptyDataState : State()
        class ErrorState(val message:Int):State()
        object ErrorRefreshState : State()
    }
}