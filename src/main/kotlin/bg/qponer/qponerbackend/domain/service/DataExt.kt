package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.Address
import bg.qponer.qponerbackend.domain.data.Country
import bg.qponer.qponerbackend.domain.dto.AddressDto
import bg.qponer.qponerbackend.domain.repo.CityRepo
import bg.qponer.qponerbackend.domain.repo.CountryRepo
import org.springframework.data.repository.findByIdOrNull

fun Address.toDto() =
        AddressDto(
                line1,
                line2,
                city.id!!,
                country.id!!,
                postalCode,
                region
        )

fun AddressDto.toEntity(
        countryRepo: CountryRepo,
        cityRepo: CityRepo
) = Address(
        line1,
        line2,
        cityId.toCityWithId(cityRepo),
        countryId.toCountryWithId(countryRepo),
        postalCode,
        region
)

fun Long.toCountryWithId(countryRepo: CountryRepo): Country = countryRepo.findByIdOrNull(this)
        ?: throw InvalidDataException("Missing country with id: $this")

fun Long.toCityWithId(cityRepo: CityRepo) = cityRepo.findByIdOrNull(this)
        ?: throw InvalidDataException("Missing city with id: $this")

