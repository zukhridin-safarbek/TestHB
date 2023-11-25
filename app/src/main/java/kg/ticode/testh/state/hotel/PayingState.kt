package kg.ticode.testh.state.hotel

import kg.ticode.data.error_model.FieldType

data class PayingState (
    val isLoading: Boolean = false,
    val errorType: FieldType = FieldType.NULL,
    val error: String? = null,
    val isSuccess: Boolean = false
)
