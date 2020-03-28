package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.BusinessOwner
import bg.qponer.qponerbackend.domain.data.BusinessType
import bg.qponer.qponerbackend.domain.dto.BusinessOwnerRequestBody
import bg.qponer.qponerbackend.domain.dto.BusinessOwnerResponseBody
import bg.qponer.qponerbackend.domain.repo.BusinessOwnerRepo
import bg.qponer.qponerbackend.domain.repo.CityRepo
import bg.qponer.qponerbackend.domain.repo.CountryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BusinessOwnerService(
        @Autowired private val businessOwnerRepo: BusinessOwnerRepo,
        @Autowired private val countryRepo: CountryRepo,
        @Autowired private val cityRepo: CityRepo,
        @Autowired private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun save(body: BusinessOwnerRequestBody) =
            body.toEntity()
                    .let { businessOwnerRepo.save(it) }
                    .toResponseBody()

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

    private fun BusinessOwner.toResponseBody() =
            BusinessOwnerResponseBody(
                    id!!,
                    businessName,
                    businessDescription,
                    avatarUrl,
                    type,
                    address.toDto()
            )

    private fun BusinessOwnerRequestBody.toEntity() =
            BusinessOwner(
                    username = username,
                    password = passwordEncoder.encode(password),
                    avatarUrl = avatarUrl,
                    firstName = firstName,
                    lastName = lastName,
                    address = address.toEntity(countryRepo, cityRepo),
                    dateOfBirth = dateOfBirth,
                    nationality = nationalityCountryId.toCountryWithId(countryRepo),
                    countryOfResidence = residenceCountryId.toCountryWithId(countryRepo),
                    businessName = businessName,
                    businessDescription = businessDescription,
                    type = type,
                    walletId = "TBD",
                    walletUserId = "TBD"
            )
}