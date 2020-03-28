package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.Card
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CardRepo : JpaRepository<Card, Long> {

    @Query("select c from Card c where c.owner.id = :ownerId")
    fun findByOwnerId(@Param("ownerId") ownerId: Long): List<Card>

}