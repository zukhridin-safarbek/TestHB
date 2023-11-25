package kg.ticode.data.usecase

import kg.ticode.data.error_model.FieldResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class InfoAboutBuyerUseCase @Inject constructor(
    private val phoneNumberUseCase: PhoneNumberUseCase,
    private val emailUseCase: EmailUseCase,
) {
    fun phoneUC(phone: String): Flow<FieldResult> {
        return phoneNumberUseCase.invoke(phone)
    }

    fun emailUC(email: String): Flow<FieldResult> {
        return emailUseCase.invoke(email)
    }
}