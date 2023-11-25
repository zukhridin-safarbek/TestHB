package kg.ticode.testh.state.hotel

import kg.ticode.domain.network.dto.BookingDetail

data class BookingDetailState (
    val isLoading: Boolean = false,
    val data: BookingDetail? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)
