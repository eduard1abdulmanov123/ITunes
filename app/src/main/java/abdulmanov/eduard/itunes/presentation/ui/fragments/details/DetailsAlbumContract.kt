package abdulmanov.eduard.itunes.presentation.ui.fragments.details

import abdulmanov.eduard.itunes.domain.models.DetailsAlbum
import abdulmanov.eduard.itunes.presentation.ui.base.MvpPresenter
import abdulmanov.eduard.itunes.presentation.ui.base.MvpView

interface DetailsAlbumContract {

    interface View:MvpView{

        fun showProgressBar(show:Boolean)

        fun showData(data:DetailsAlbum)

        fun showError(message:Int)

        fun showRefreshProgressBar(show:Boolean)

        fun hideError()
    }

    interface Presenter<V:View>:MvpPresenter<V>{

        fun fetchDetailsAlbum(id:Long)

        fun refresh(id:Long)

    }
}