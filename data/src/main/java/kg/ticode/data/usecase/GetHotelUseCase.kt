package kg.ticode.data.usecase

import kg.ticode.data.ApiResult
import kg.ticode.domain.network.dto.Hotel
import kg.ticode.domain.network.repository.HotelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHotelUseCase @Inject constructor(
    private val repository: HotelRepository,
) {
    operator fun invoke(): Flow<ApiResult<Hotel>> = flow {
        try {
            emit(ApiResult.Loading())
            val response = repository.getHostel()
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