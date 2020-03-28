package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.BusinessOwner
import bg.qponer.qponerbackend.domain.data.BusinessType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BusinessOwnerRepo
    : JpaRepository<BusinessOwner, Long> {

    @Query("select bo from BusinessOwner bo " +
            "where (:countryId is null or bo.address.country.id = :countryId) " +
            "and (:cityId is null or bo.address.city.id = :cityId) " +
            "and (:type is null or bo.type = :type) " +
            "and (:nameQuery is null or lower(bo.businessName) like lower(concat('%', concat(:nameQuery, '%'))))")
    fun findAll(
            @Param("countryId") countryId: Long?,
            @Param("cityId") cityId: Long?,
            @Param("type") type: BusinessType?,
            @Param("nameQuery") nameQuery: String?
    ): List<BusinessOwner>

    fun findByUsername(username: String): BusinessOwner
}
