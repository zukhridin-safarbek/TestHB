package kg.ticode.testh.viewmodel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import kg.ticode.data.usecase.GetBookingDetailUseCase
import kg.ticode.data.usecase.GetHotelRoomsUseCase
import kg.ticode.data.usecase.GetHotelUseCase
import kg.ticode.data.usecase.InfoAboutBuyerUseCase
import kg.ticode.data.usecase.TouristUseCase
import kg.ticode.domain.network.dto.InfoAboutBuyer
import kg.ticode.testh.viewmodel.HotelViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    @Singleton
    fun provideHotelViewModel(
        hotelUseCase: GetHotelUseCase, hotelRoomsUseCase: GetHotelRoomsUseCase, bookingDetailUseCase: GetBookingDetailUseCase, touristUseCase: TouristUseCase, infoAboutBuyerUseCase: InfoAboutBuyerUseCase
    ): HotelViewModel = HotelViewModel(hotelUseCase, hotelRoomsUseCase, bookingDetailUseCase, touristUseCase, infoAboutBuyerUseCase)
}