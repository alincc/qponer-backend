package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.City
import bg.qponer.qponerbackend.domain.dto.CityResponseBody
import bg.qponer.qponerbackend.domain.repo.CityRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityService(
        @Autowired private val cityRepo: CityRepo
) {

    fun findAllByCountry(countryId: Long) = cityRepo.findByCountry_Id(countryId)

    private fun City.toResponseBody() =
            CityResponseBody(
                    id!!,
                    name
            )

}
