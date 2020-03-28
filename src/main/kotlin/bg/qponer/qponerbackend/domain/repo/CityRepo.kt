package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.City
import org.springframework.data.repository.CrudRepository

interface CityRepo : CrudRepository<City, Long>