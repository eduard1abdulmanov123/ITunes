package abdulmanov.eduard.itunes.presentation.ui.base

import abdulmanov.eduard.itunes.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

abstract class BaseMvpPresenter<View: MvpView>:MvpPresenter<View> {

    private val compositeDisposable = CompositeDisposable()

    protected var view: View? = null

    override fun attach(view: View) {
        this.view = view
    }

    override fun detach() {
        view = null
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    protected fun <T> Single<T>.safeSubscribe(
        onSuccess: (T) -> Unit,
        onError: (error: Throwable) -> Unit
    ) {
        compositeDisposable.add(
            subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
        )
    }

    protected fun getErrorString(error: Throwable): Int {
        return if (error is HttpException) {
            R.string.error_server
        } else {
            R.string.error_network
        }
    }

}