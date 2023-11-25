package kg.ticode.data.usecase

import kg.ticode.data.error_model.FieldResult
import kg.ticode.data.error_model.FieldType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhoneNumberUseCase @Inject constructor() {
    operator fun invoke(number: String): Flow<FieldResult> = flow {
        when  {
            number.isBlank() -> emit(
                FieldResult.Error(
                    fieldType = FieldType.PHONE_NUMBER,
                    "Поля с номером телефона должны быть заполнены"
                )
            )
            number.last() == '*' -> emit(
                FieldResult.Error(
                    fieldType = FieldType.PHONE_NUMBER,
                    "Пожалуйста, напишите номер телефона правильно"
                )
            )
            else -> {
                emit(FieldResult.IsSuccess)
            }
        }
    }
}