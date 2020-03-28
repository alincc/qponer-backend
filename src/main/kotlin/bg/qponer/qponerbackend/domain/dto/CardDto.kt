package bg.qponer.qponerbackend.domain.dto

data class CardRequestBody(
        val number: String,
        val cvv: String,
        val expiryDate: String
)

data class CardResponseBody(
        val id: Long,
        val displayName: String,
        val expiryDate: String
)