package abdulmanov.eduard.itunes.presentation.dagger.module

import abdulmanov.eduard.itunes.data.remote.Constants.Companion.BASE_URL_ITUNES_SEARCH_API
import abdulmanov.eduard.itunes.data.remote.RetrofitFactory
import abdulmanov.eduard.itunes.data.remote.ITunesSearchApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideITunesSearchApi(): ITunesSearchApi {
        return RetrofitFactory.buildApi(
            BASE_URL_ITUNES_SEARCH_API,
            ITunesSearchApi::class.java
        )
    }

}