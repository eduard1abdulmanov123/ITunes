package abdulmanov.eduard.itunes.presentation.app

import abdulmanov.eduard.itunes.presentation.dagger.component.AppComponent
import abdulmanov.eduard.itunes.presentation.dagger.component.DaggerAppComponent
import abdulmanov.eduard.itunes.presentation.dagger.module.AppModule
import android.app.Application
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import io.reactivex.plugins.RxJavaPlugins

class BaseApp: Application() {

    companion object {
        private const val MAX_CACHE = 250_000_000

        lateinit var instance: BaseApp
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = buildAppComponent()
        initPicasso()
        RxJavaPlugins.setErrorHandler { }
    }

    private fun buildAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private fun initPicasso() {
        val picasso = Picasso.Builder(this)
            .memoryCache(LruCache(MAX_CACHE))
            .build()
        Picasso.setSingletonInstance(picasso)
    }

}