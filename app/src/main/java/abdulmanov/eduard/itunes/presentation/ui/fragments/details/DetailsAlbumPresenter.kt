package abdulmanov.eduard.itunes.presentation.ui.fragments.details

import abdulmanov.eduard.itunes.domain.interactors.DetailsAlbumUseCase
import abdulmanov.eduard.itunes.presentation.ui.base.BaseMvpPresenter

class DetailsAlbumPresenter<V:DetailsAlbumContract.View>(
    private val detailsAlbumUseCase: DetailsAlbumUseCase
):BaseMvpPresenter<V>(),DetailsAlbumContract.Presenter<V>{

    override fun fetchDetailsAlbum(id: Long) {
        view?.showProgressBar(true)
        detailsAlbumUseCase.fetchDetailsAlbum(id).safeSubscribe(
            {
                view?.showProgressBar(false)
                view?.showData(it)
            },
            {
                view?.showProgressBar(false)
                view?.showError(getErrorString(it))
            }
        )
    }

    override fun refresh(id: Long) {
        view?.showRefreshProgressBar(true)
        detailsAlbumUseCase.fetchDetailsAlbum(id).safeSubscribe(
            {
                view?.showRefreshProgressBar(false)
                view?.hideError()
                view?.showData(it)
            },
            {
                view?.showRefreshProgressBar(false)
                view?.showError(getErrorString(it))
            }
        )
    }

}