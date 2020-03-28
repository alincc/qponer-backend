package bg.qponer.qponerbackend.domain.data

data class CardRegistration(
        val accessKey: String,
        val baseUrl: String,
        val cardPreregistrationId: String,
        val cardRegistrationUrl: String,
        val cardType: String,
        val clientId: String,
        val preregistrationData: String
)