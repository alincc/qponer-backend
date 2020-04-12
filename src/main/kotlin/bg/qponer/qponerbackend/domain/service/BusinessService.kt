package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.Address
import bg.qponer.qponerbackend.domain.data.Business
import bg.qponer.qponerbackend.domain.data.BusinessType
import bg.qponer.qponerbackend.domain.data.Country
import bg.qponer.qponerbackend.domain.dto.BusinessRequestBody
import bg.qponer.qponerbackend.domain.dto.BusinessResponseBody
import bg.qponer.qponerbackend.domain.dto.RankedContributor
import bg.qponer.qponerbackend.domain.repo.BusinessRepo
import bg.qponer.qponerbackend.domain.repo.CityRepo
import bg.qponer.qponerbackend.domain.repo.CountryRepo
import bg.qponer.qponerbackend.domain.repo.MangoPayRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BusinessService(
        @Autowired private val businessRepo: BusinessRepo,
        @Autowired private val countryRepo: CountryRepo,
        @Autowired private val cityRepo: CityRepo,
        @Autowired private val mangoPayRepo: MangoPayRepo,
        @Autowired private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun save(body: BusinessRequestBody): BusinessResponseBody {
        val headquartersAddress = body.headquartersAddress.toEntity(countryRepo, cityRepo)

        val legalRepresentativeNationality = body.legalRepresentativeNationality.toCountryWithId(countryRepo)
        val legalRepresentativeCountryOfResidence = body.legalRepresentativeCountryOfResidence.toCountryWithId(countryRepo)

        val walletIds = mangoPayRepo.createLegalUser(
                headquartersAddress,
                body.name,
                body.legalRepresentativeDateOfBirth,
                legalRepresentativeCountryOfResidence,
                legalRepresentativeNationality,
                body.legalRepresentativeFirstName,
                body.legalRepresentativeLastName,
                body.email
        )

        return createBusiness(body, headquartersAddress, legalRepresentativeNationality, legalRepresentativeCountryOfResidence, walletIds)
                .let { businessRepo.save(it) }
                .toResponseBody()
    }

    fun findAllByFilter(
            countryId: Long?,
            cityId: Long?,
            type: String?,
            query: String?
    ) = businessRepo.findAll(
            countryId,
            cityId,
            type?.let
            { BusinessType.valueOf(type) },
            query
    ).map { it.toResponseBody() }

    private fun createBusiness(body: BusinessRequestBody, headquartersAddress: Address, legalRepresentativeNationality: Country, legalRepresentativeCountryOfResidence: Country, walletIds: Pair<String, String>) =
            Business(
                    username = body.username,
                    password = passwordEncoder.encode(body.password),
                    email = body.email,
                    type = body.type,
                    headquartersAddress = headquartersAddress,
                    phone = body.phone,
                    logoUrl = body.logoUrl,
                    pictureUrl = body.pictureUrl,
                    name = body.name,
                    description = body.description,
                    additionalBenefits = body.additionalBenefits,
                    websiteUrl = body.websiteUrl,
                    legalRepresentativeFirstName = body.legalRepresentativeFirstName,
                    legalRepresentativeLastName = body.legalRepresentativeLastName,
                    legalRepresentativeDateOfBirth = body.legalRepresentativeDateOfBirth,
                    legalRepresentativeNationality = legalRepresentativeNationality,
                    legalRepresentativeCountryOfResidence = legalRepresentativeCountryOfResidence,
                    walletId = walletIds.first,
                    walletUserId = walletIds.second
            )

    fun getTopContributors(ownerId: Long): List<RankedContributor> {
        return businessRepo.getTopContributors(ownerId)
    }

    private fun Business.toResponseBody() =
            BusinessResponseBody(
                    id!!,
                    phone,
                    logoUrl,
                    pictureUrl,
                    headquartersAddress.toDto(),
                    type,
                    name,
                    description,
                    additionalBenefits,
                    websiteUrl
            )

}
