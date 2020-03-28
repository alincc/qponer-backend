package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.Country
import bg.qponer.qponerbackend.domain.dto.CountryResponseBody
import bg.qponer.qponerbackend.domain.repo.CountryRepo
import org.springframework.stereotype.Service

@Service
class CountryService(
        private val countryRepo: CountryRepo
) {

    fun findAll() = countryRepo.findAll().map { it.toResponseBody() }

    private fun Country.toResponseBody() =
            CountryResponseBody(
                    id!!,
                    code,
                    name
            )
}