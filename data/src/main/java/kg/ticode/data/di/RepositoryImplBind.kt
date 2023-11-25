package kg.ticode.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.ticode.data.network.repository.HotelRepositoryImpl
import kg.ticode.domain.network.repository.HotelRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryImplBind {
    @Singleton
    @Binds
    fun bindHostelRepoImpl(impl: HotelRepositoryImpl): HotelRepository
}