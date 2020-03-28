package bg.qponer.qponerbackend.domain.controller

import bg.qponer.qponerbackend.domain.service.CityService
import bg.qponer.qponerbackend.domain.service.CountryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class CountryController(
        @Autowired private val countryService: CountryService,
        @Autowired private val cityService: CityService
) {

    @GetMapping("/countries")
    fun findAll() = runServiceMethod { countryService.findAll() }

    @GetMapping("/countries/{id}/cities")
    fun findCitiesForCountry(@PathVariable("id") countryId: Long) = runServiceMethod { cityService.findAllByCountry(countryId) }
}