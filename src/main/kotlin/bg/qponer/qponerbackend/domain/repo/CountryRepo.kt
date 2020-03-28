package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.Country
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepo : CrudRepository<Country, Long>