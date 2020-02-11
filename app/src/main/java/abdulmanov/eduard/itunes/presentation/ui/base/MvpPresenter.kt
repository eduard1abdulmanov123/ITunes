package abdulmanov.eduard.itunes.presentation.ui.base

interface MvpPresenter<V:MvpView> {

    fun attach(view:V)

    fun detach()

}