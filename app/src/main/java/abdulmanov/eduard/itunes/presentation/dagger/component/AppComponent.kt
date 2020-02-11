package abdulmanov.eduard.itunes.presentation.dagger.component

import abdulmanov.eduard.itunes.presentation.dagger.module.AppModule
import abdulmanov.eduard.itunes.presentation.dagger.module.DataModule
import abdulmanov.eduard.itunes.presentation.dagger.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class,DataModule::class])
interface AppComponent {

    fun fragmentComponent():FragmentComponent.Factory

}