package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.Business
import bg.qponer.qponerbackend.domain.data.BusinessType
import bg.qponer.qponerbackend.domain.dto.RankedContributor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BusinessRepo
    : JpaRepository<Business, Long> {

    @Query("select bo from Business bo " +
            "where (:countryId is null or bo.headquartersAddress.country.id = :countryId) " +
            "and (:cityId is null or bo.headquartersAddress.city.id = :cityId) " +
            "and (:type is null or bo.type = :type) " +
            "and (:nameQuery is null or lower(bo.name) like lower(concat('%', concat(:nameQuery, '%'))))")
    fun findAll(
            @Param("countryId") countryId: Long?,
            @Param("cityId") cityId: Long?,
            @Param("type") type: BusinessType?,
            @Param("nameQuery") nameQuery: String?
    ): List<Business>

    fun findByUsername(username: String): Optional<Business>

    @Query("select new bg.qponer.qponerbackend.domain.dto.RankedContributor(concat(min(v.contributor.firstName), concat(' ', min(v.contributor.lastName))) , sum(v.allTimeValue)) from AccumulatedValue v " +
            "where v.business.id = :businessId " +
            "order by v.allTimeValue desc")
    fun getTopContributors(@Param("businessId") businessId: Long?): List<RankedContributor>
}
