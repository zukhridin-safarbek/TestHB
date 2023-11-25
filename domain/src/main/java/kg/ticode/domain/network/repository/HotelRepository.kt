package kg.ticode.domain.network.repository

import kg.ticode.domain.network.dto.BookingDetail
import kg.ticode.domain.network.dto.Hotel
import kg.ticode.domain.network.dto.HotelNumbers
import retrofit2.Response

interface HotelRepository {
   suspend fun getHostel(): Response<Hotel>
   suspend fun getHotelRooms(): Response<HotelNumbers>
   suspend fun getBookingDetail(): Response<BookingDetail>
}