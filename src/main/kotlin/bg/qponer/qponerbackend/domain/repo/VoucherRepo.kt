package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.Voucher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VoucherRepo : JpaRepository<Voucher, Long> {

    @Query("select v from Voucher v where v.owner.id = :ownerId and v.contributor.id = :contributorId")
    fun findByBusinessOwnerAndContributor(
            @Param("ownerId") ownerId: Long,
            @Param("contributorId") contributorId: Long
    ): Optional<Voucher>

    @Query("select v from Voucher v where v.owner.id = :ownerId")
    fun findByBusinessOwner(@Param("ownerId") ownerId: Long): List<Voucher>

    @Query("select v from Voucher v where v.contributor.id = :contributorId")
    fun findByContributor(@Param("contributorId") contributorId: Long): List<Voucher>

}