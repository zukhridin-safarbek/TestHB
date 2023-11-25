package kg.ticode.data.error_model

sealed class FieldResult{
    data object IsSuccess: FieldResult()
    data class Error(val fieldType: FieldType, val error: String): FieldResult()
}
