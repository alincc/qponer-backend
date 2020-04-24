package bg.qponer.qponerbackend.domain.dto

data class CardRequestBody(
        val cardId: String,
        val number: String,
        val expiryDate: String
)

data class CardResponseBody(
        val id: Long,
        val displayName: String,
        val expiryDate: String
)

data class CardRegistrationResponseBody(
        val accessKey: String,
        val baseUrl: String,
        val cardPreregistrationId: String,
        val cardRegistrationUrl: String,
        val cardType: String,
        val clientId: String,
        val preregistrationData: String
)