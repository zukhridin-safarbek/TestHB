package kg.ticode.data.network.repository

import kg.ticode.data.service.ApiService
import kg.ticode.domain.network.dto.BookingDetail
import kg.ticode.domain.network.dto.Hotel
import kg.ticode.domain.network.dto.HotelNumbers
import kg.ticode.domain.network.repository.HotelRepository
import retrofit2.Response
import javax.inject.Inject

class HotelRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : HotelRepository {
    override suspend fun getHostel(): Response<Hotel> {
        return apiService.getHostel()
    }

    override suspend fun getHotelRooms(): Response<HotelNumbers> {
        return apiService.getHotelRooms()
    }

    override suspend fun getBookingDetail(): Response<BookingDetail> {
        return apiService.getBookingDetail()
    }
}