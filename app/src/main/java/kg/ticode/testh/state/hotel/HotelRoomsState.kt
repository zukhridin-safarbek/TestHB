package kg.ticode.testh.state.hotel

import kg.ticode.domain.network.dto.HotelNumbers

data class HotelRoomsState (
    val isLoading: Boolean = false,
    val data: HotelNumbers? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)
