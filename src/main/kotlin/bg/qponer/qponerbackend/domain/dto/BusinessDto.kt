package bg.qponer.qponerbackend.domain.dto

import bg.qponer.qponerbackend.domain.data.BusinessType
import bg.qponer.qponerbackend.domain.data.Country
import java.util.*

data class BusinessRequestBody(
        val username: String,
        val password: String,
        val email: String,
        val phone: String,
        val logoUrl: String? = null,
        val pictureUrl: String? = null,
        val headquartersAddress: AddressDto,
        val type: BusinessType,
        val name: String,
        val description: String,
        val additionalBenefits: String,
        val websiteUrl: String,
        val legalRepresentativeFirstName: String,
        val legalRepresentativeLastName: String,
        val legalRepresentativeDateOfBirth: Calendar,
        val legalRepresentativeNationality: Long,
        val legalRepresentativeCountryOfResidence: Long
)

data class BusinessResponseBody(
        val id: Long,
        val phone: String,
        val logoUrl: String? = null,
        val pictureUrl: String? = null,
        val headquartersAddress: AddressDto,
        val type: BusinessType,
        val name: String,
        val description: String,
        val additionalBenefits: String,
        val websiteUrl: String
)
