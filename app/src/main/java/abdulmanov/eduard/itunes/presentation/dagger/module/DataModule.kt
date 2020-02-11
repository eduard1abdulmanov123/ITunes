package abdulmanov.eduard.itunes.presentation.dagger.module

import abdulmanov.eduard.itunes.data.mappers.ITunesMapper
import abdulmanov.eduard.itunes.data.mappers.ITunesMapperImpl
import abdulmanov.eduard.itunes.data.remote.ITunesSearchApi
import abdulmanov.eduard.itunes.data.repositories.ITunesRepositoryImpl
import abdulmanov.eduard.itunes.domain.repositories.ITunesRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideITunesMapper(gson: Gson): ITunesMapper {
        return ITunesMapperImpl(gson)
    }

    @Singleton
    @Provides
    fun provideITunesRepository(
        iTunesSearchApi: ITunesSearchApi,
        mapper: ITunesMapper
    ): ITunesRepository {
        return ITunesRepositoryImpl(iTunesSearchApi, mapper)
    }

}