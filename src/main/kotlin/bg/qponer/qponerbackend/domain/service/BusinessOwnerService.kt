package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.Address
import bg.qponer.qponerbackend.domain.data.BusinessOwner
import bg.qponer.qponerbackend.domain.data.BusinessType
import bg.qponer.qponerbackend.domain.data.Country
import bg.qponer.qponerbackend.domain.dto.BusinessOwnerRequestBody
import bg.qponer.qponerbackend.domain.dto.BusinessOwnerResponseBody
import bg.qponer.qponerbackend.domain.repo.BusinessOwnerRepo
import bg.qponer.qponerbackend.domain.repo.CityRepo
import bg.qponer.qponerbackend.domain.repo.CountryRepo
import bg.qponer.qponerbackend.domain.repo.MangoPayRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BusinessOwnerService(
        @Autowired private val businessOwnerRepo: BusinessOwnerRepo,
        @Autowired private val countryRepo: CountryRepo,
        @Autowired private val cityRepo: CityRepo,
        @Autowired private val mangoPayRepo: MangoPayRepo,
        @Autowired private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun save(body: BusinessOwnerRequestBody): BusinessOwnerResponseBody {
        val address = body.address.toEntity(countryRepo, cityRepo)

        val nationality = body.nationalityCountryId.toCountryWithId(countryRepo)
        val countryOfResidence = body.residenceCountryId.toCountryWithId(countryRepo)

        val walletIds = mangoPayRepo.createUser(
                body.firstName,
                body.lastName,
                address,
                body.dateOfBirth,
                nationality,
                countryOfResidence,
                body.username
        )

        return createOwner(body, address, nationality, countryOfResidence, walletIds)
                .let { businessOwnerRepo.save(it) }
                .toResponseBody()
    }

    fun findAllByFilter(
            countryId: Long?,
            cityId: Long?,
            type: String?,
            query: String?
    ) = businessOwnerRepo.findAll(
            countryId,
            cityId,
            type?.let
            { BusinessType.valueOf(type) },
            query
    ).map { it.toResponseBody() }

    private fun createOwner(body: BusinessOwnerRequestBody, address: Address, nationality: Country, countryOfResidence: Country, walletIds: Pair<String, String>) =
            BusinessOwner(
                    username = body.username,
                    password = passwordEncoder.encode(body.password),
                    avatarUrl = body.avatarUrl,
                    firstName = body.firstName,
                    lastName = body.lastName,
                    address = address,
                    dateOfBirth = body.dateOfBirth,
                    nationality = nationality,
                    countryOfResidence = countryOfResidence,
                    businessName = body.businessName,
                    businessDescription = body.businessDescription,
                    type = body.type,
                    walletId = walletIds.first,
                    walletUserId = walletIds.second
            )

    private fun BusinessOwner.toResponseBody() =
            BusinessOwnerResponseBody(
                    id!!,
                    businessName,
                    businessDescription,
                    avatarUrl,
                    type,
                    address.toDto()
            )

}