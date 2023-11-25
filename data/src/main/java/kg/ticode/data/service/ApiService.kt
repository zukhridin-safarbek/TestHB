package kg.ticode.data.service

import kg.ticode.domain.network.dto.BookingDetail
import kg.ticode.domain.network.dto.Hotel
import kg.ticode.domain.network.dto.HotelNumbers
import kg.ticode.domain.network.dto.HotelRoom
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHostel(): Response<Hotel>

    @GET("8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getHotelRooms(): Response<HotelNumbers>

    @GET("63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun getBookingDetail(): Response<BookingDetail>
}