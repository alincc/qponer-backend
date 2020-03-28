package bg.qponer.qponerbackend.domain.dto

data class CountryResponseBody(
        val id: Long,
        val code: String,
        val name: String
)

data class CityResponseBody(
        val id: Long,
        val name: String
)