package abdulmanov.eduard.itunes.presentation.dagger.component

import abdulmanov.eduard.itunes.presentation.dagger.module.FragmentModule
import abdulmanov.eduard.itunes.presentation.dagger.scope.FragmentScope
import abdulmanov.eduard.itunes.presentation.ui.fragments.details.DetailsAlbumFragment
import abdulmanov.eduard.itunes.presentation.ui.fragments.search.SearchFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():FragmentComponent
    }

    fun inject(searchFragment: SearchFragment)

    fun inject(detailsAlbumFragment: DetailsAlbumFragment)
}