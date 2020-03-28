package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.Contributor
import bg.qponer.qponerbackend.domain.dto.ContributorRequestBody
import bg.qponer.qponerbackend.domain.dto.ContributorResponseBody
import bg.qponer.qponerbackend.domain.repo.CityRepo
import bg.qponer.qponerbackend.domain.repo.ContributorRepo
import bg.qponer.qponerbackend.domain.repo.CountryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ContributorService(
        @Autowired private val contributorRepo: ContributorRepo,
        @Autowired private val countryRepo: CountryRepo,
        @Autowired private val cityRepo: CityRepo,
        @Autowired private val passwordEncoder: PasswordEncoder
) {

    fun save(body: ContributorRequestBody): ContributorResponseBody =
            body.toEntity()
                    .also { contributorRepo.save(it) }
                    .toResponseBody()


    private fun ContributorRequestBody.toEntity() =
            Contributor(
                    username = username,
                    password = passwordEncoder.encode(password),
                    avatarUrl = avatarUrl,
                    firstName = firstName,
                    lastName = lastName,
                    address = address.toEntity(countryRepo, cityRepo),
                    dateOfBirth = dateOfBirth,
                    nationality = nationalityCountryId.toCountryWithId(countryRepo),
                    countryOfResidence = residenceCountryId.toCountryWithId(countryRepo),
                    walletId = "TBD",
                    walletUserId = "TBD"
            )

    private fun Contributor.toResponseBody() =
            ContributorResponseBody(
                    id!!,
                    firstName,
                    lastName,
                    avatarUrl
            )
}