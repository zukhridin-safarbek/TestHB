package kg.ticode.testh.state.hotel

import kg.ticode.domain.network.dto.Hotel

data class HotelState (
    val isLoading: Boolean = false,
    val data: Hotel? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)
