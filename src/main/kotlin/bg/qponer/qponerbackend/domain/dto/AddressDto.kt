package bg.qponer.qponerbackend.domain.dto

data class AddressDto(
        val line1: String,
        val line2: String?,
        val cityId: Long,
        val countryId: Long,
        var postalCode: String,
        var region: String
)