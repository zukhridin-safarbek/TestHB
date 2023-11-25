package kg.ticode.data.usecase

import kg.ticode.data.error_model.FieldResult
import kg.ticode.data.error_model.FieldType
import kg.ticode.domain.network.dto.Tourist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class TouristUseCase @Inject constructor(){
    operator fun invoke(tourist: Tourist): Flow<FieldResult> = flow {
        when {
            tourist.firstName.isBlank() -> {
                emit(
                    FieldResult.Error(
                        fieldType = FieldType.FIRST_NAME,
                        "Поля с именем должны быть заполнены"
                    )
                )
            }

            tourist.lastName.isBlank() -> {
                emit(
                    FieldResult.Error(
                        fieldType = FieldType.LAST_NAME,
                        "Поля с фамилией должны быть заполнены"
                    )
                )
            }

            tourist.dateOfBirth.isBlank() -> {
                emit(
                    FieldResult.Error(
                        fieldType = FieldType.DATE_OF_BIRTH,
                        "Поля с указанием гражданства должны быть заполнены"
                    )
                )
            }

            tourist.citizenship.isBlank() -> {
                emit(
                    FieldResult.Error(
                        fieldType = FieldType.CITIZENSHIP,
                        "Поля с фамилией должны быть заполнены"
                    )
                )
            }

            tourist.passportNumber.isBlank() -> {
                emit(
                    FieldResult.Error(
                        fieldType = FieldType.PASSPORT_NUMBER,
                        "Поля с номером паспорта должны быть заполнены"
                    )
                )
            }

            tourist.passportValidPeriod.isBlank() -> {
                emit(
                    FieldResult.Error(
                        fieldType = FieldType.PASSPORT_VALID_DATE,
                        "Поля с указанием срока действия паспорта должны быть заполнены"
                    )
                )
            }

            else -> {
                emit(FieldResult.IsSuccess)
            }

        }
    }
}