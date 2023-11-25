package kg.ticode.data.usecase

import kg.ticode.data.ApiResult
import kg.ticode.domain.network.dto.BookingDetail
import kg.ticode.domain.network.dto.HotelNumbers
import kg.ticode.domain.network.repository.HotelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBookingDetailUseCase @Inject constructor(
    private val hotelRepository: HotelRepository,
) {
    operator fun invoke(): Flow<ApiResult<BookingDetail>> = flow {
        try {
            emit(ApiResult.Loading())
            val response = hotelRepository.getBookingDetail()
            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()))
            } else {
                emit(ApiResult.Error(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }
}