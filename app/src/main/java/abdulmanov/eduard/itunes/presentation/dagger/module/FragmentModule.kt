package abdulmanov.eduard.itunes.presentation.dagger.module

import abdulmanov.eduard.itunes.domain.interactors.DetailsAlbumUseCase
import abdulmanov.eduard.itunes.domain.interactors.SearchUseCase
import abdulmanov.eduard.itunes.presentation.common.SharedPreferencesManager
import abdulmanov.eduard.itunes.presentation.dagger.scope.FragmentScope
import abdulmanov.eduard.itunes.presentation.ui.fragments.details.DetailsAlbumContract
import abdulmanov.eduard.itunes.presentation.ui.fragments.details.DetailsAlbumPresenter
import abdulmanov.eduard.itunes.presentation.ui.fragments.search.SearchContract
import abdulmanov.eduard.itunes.presentation.ui.fragments.search.SearchPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @FragmentScope
    @Provides
    fun provideSearchPresenter(
        sharedPreferencesManager: SharedPreferencesManager,
        searchUseCase: SearchUseCase
    ): SearchContract.Presenter<SearchContract.View> {
        return SearchPresenter(sharedPreferencesManager, searchUseCase)
    }

    @FragmentScope
    @Provides
    fun provideDetailsAlbumPresenter(
        detailsAlbumUseCase: DetailsAlbumUseCase
    ): DetailsAlbumContract.Presenter<DetailsAlbumContract.View> {
        return DetailsAlbumPresenter(detailsAlbumUseCase)
    }

}