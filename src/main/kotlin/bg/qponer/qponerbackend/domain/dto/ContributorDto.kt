package bg.qponer.qponerbackend.domain.dto

import java.util.*

data class ContributorRequestBody(
        val username: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        val avatarUrl: String?,
        val address: AddressDto,
        val dateOfBirth: Calendar,
        val nationalityCountryId: Long,
        val residenceCountryId: Long
)

data class ContributorResponseBody(
        val id: Long,
        val firstName: String,
        val lastName: String,
        val avatarUrl: String?
)

