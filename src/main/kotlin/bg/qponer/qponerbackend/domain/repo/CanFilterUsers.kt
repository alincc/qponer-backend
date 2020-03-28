package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.BusinessOwner
import bg.qponer.qponerbackend.domain.data.BusinessType

interface CanFilterBusinessOwner {

    fun findAll(
            countryId: Long?,
            cityId: Long?,
            type: BusinessType?,
            nameQuery: String?
    ): List<BusinessOwner>

}