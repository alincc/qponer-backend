package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.Address
import bg.qponer.qponerbackend.domain.data.Contributor
import bg.qponer.qponerbackend.domain.data.Country
import bg.qponer.qponerbackend.domain.dto.ContributorRequestBody
import bg.qponer.qponerbackend.domain.dto.ContributorResponseBody
import bg.qponer.qponerbackend.domain.repo.CityRepo
import bg.qponer.qponerbackend.domain.repo.ContributorRepo
import bg.qponer.qponerbackend.domain.repo.CountryRepo
import bg.qponer.qponerbackend.domain.repo.MangoPayRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ContributorService(
        @Autowired private val contributorRepo: ContributorRepo,
        @Autowired private val countryRepo: CountryRepo,
        @Autowired private val cityRepo: CityRepo,
        @Autowired private val mangoPayRepo: MangoPayRepo,
        @Autowired private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun save(body: ContributorRequestBody): ContributorResponseBody {
        val address = body.address.toEntity(countryRepo, cityRepo)

        val nationality = body.nationalityCountryId.toCountryWithId(countryRepo)
        val countryOfResidence = body.residenceCountryId.toCountryWithId(countryRepo)

        val walletIds = mangoPayRepo.createNaturalUser(
                body.firstName,
                body.lastName,
                address,
                body.dateOfBirth,
                nationality,
                countryOfResidence,
                body.email
        )

        return createContributor(body, address, nationality, countryOfResidence, walletIds)
                .let { contributorRepo.save(it) }
                .toResponseBody()
    }

    private fun createContributor(body: ContributorRequestBody, address: Address, nationality: Country, countryOfResidence: Country, walletIds: Pair<String, String>) = Contributor(
            username = body.username,
            password = passwordEncoder.encode(body.password),
            email = body.email,
            phone = body.phone,
            avatarUrl = body.avatarUrl,
            firstName = body.firstName,
            lastName = body.lastName,
            address = address,
            dateOfBirth = body.dateOfBirth,
            nationality = nationality,
            countryOfResidence = countryOfResidence,
            walletId = walletIds.first,
            walletUserId = walletIds.second
    )

    private fun Contributor.toResponseBody() =
            ContributorResponseBody(
                    id!!,
                    firstName,
                    lastName,
                    avatarUrl
            )
}
