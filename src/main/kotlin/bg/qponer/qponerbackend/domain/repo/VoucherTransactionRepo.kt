package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.VoucherTransaction

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VoucherTransactionRepo : JpaRepository<VoucherTransaction, Long> {


    @Query("select v from VoucherTransaction v where v.contributor.id = :contributorId")
    fun findByContributor(@Param("contributorId") contributorId: Long): List<VoucherTransaction>
}
