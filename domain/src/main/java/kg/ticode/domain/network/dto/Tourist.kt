package kg.ticode.domain.network.dto

data class Tourist(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val citizenship: String,
    val passportNumber: String,
    val passportValidPeriod: String
)
