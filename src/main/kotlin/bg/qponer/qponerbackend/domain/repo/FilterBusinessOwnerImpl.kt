package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.BusinessOwner
import bg.qponer.qponerbackend.domain.data.BusinessType
import bg.qponer.qponerbackend.domain.data.City
import bg.qponer.qponerbackend.domain.data.Country
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Predicate

@Repository
class FilterBusinessOwnerImpl(
        @PersistenceContext private val entityManager: EntityManager
) : CanFilterBusinessOwner {

    override fun findAll(countryId: Long?, cityId: Long?, type: BusinessType?, nameQuery: String?): List<BusinessOwner> {
        val query = entityManager.criteriaBuilder.createQuery(BusinessOwner::class.java)
        val root = query.from(BusinessOwner::class.java)
        val predicates = mutableListOf<Predicate>().apply {

            countryId?.let {
                val countryJoin = root.join<BusinessOwner, Country>("country")
                add(entityManager.criteriaBuilder.equal(countryJoin.get<Long>("id"), it))
            }

            cityId?.let {
                val cityJoin = root.join<BusinessOwner, City>("city")
                add(entityManager.criteriaBuilder.equal(cityJoin.get<Long>("id"), it))
            }

            type?.let {
                add(entityManager.criteriaBuilder.equal(root.get<BusinessType>("type"), it))
            }

            query?.let {
                add(entityManager.criteriaBuilder.like(root.get("businessName"), "%$it%"))
            }
        }

        query
                .select(root)
                .where(entityManager.criteriaBuilder.or(*predicates.toTypedArray()))

        return entityManager.createQuery(query)
                .resultList
    }
}
