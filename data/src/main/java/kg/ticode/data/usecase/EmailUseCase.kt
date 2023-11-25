package kg.ticode.data.usecase

import kg.ticode.data.error_model.FieldResult
import kg.ticode.data.error_model.FieldType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmailUseCase @Inject constructor() {
    operator fun invoke(email: String): Flow<FieldResult> = flow {
        val isEmail = email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
        when {
            email.isBlank() -> {
                emit(
                    FieldResult.Error(
                        fieldType = FieldType.EMAIL,
                        "Поля с email должны быть заполнены"
                    )
                )
            }

            !isEmail -> emit(
                FieldResult.Error(
                    FieldType.EMAIL,
                    "Пожалуйста, правильно укажите адрес электронной почты"
                )
            )

            else -> {
                emit(FieldResult.IsSuccess)
            }
        }
    }
}