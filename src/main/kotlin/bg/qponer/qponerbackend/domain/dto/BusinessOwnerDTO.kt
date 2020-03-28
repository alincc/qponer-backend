package bg.qponer.qponerbackend.domain.dto

import bg.qponer.qponerbackend.domain.data.BusinessType
import java.util.*

data class BusinessOwnerRequestBody(
        val username: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        val avatarUrl: String?,
        val address: AddressDto,
        val dateOfBirth: Calendar,
        val nationalityCountryId: Long,
        val residenceCountryId: Long,
        val type: BusinessType,
        val businessName: String,
        val businessDescription: String
)

data class BusinessOwnerResponseBody(
        val id: Long,
        val businessName: String,
        val businessDescription: String,
        val avatarUrl: String?,
        val type: BusinessType,
        val address: AddressDto
)
