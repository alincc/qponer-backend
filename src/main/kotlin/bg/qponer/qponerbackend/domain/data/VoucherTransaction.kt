package bg.qponer.qponerbackend.domain.data

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.LastModifiedDate

import java.math.BigDecimal
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class VoucherTransaction(
        @get:Id
        @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SID_VOUCHER_TRN_ID_SEQ")
        var id: Long? = null,

        @get:ManyToOne
        var business: Business,

        @get:ManyToOne
        var contributor: Contributor,

        var value: BigDecimal,

        var externalId: String,

        @get:ManyToOne
        var voucherType: VoucherType,

        @CreationTimestamp
        @Column(name = "created_at", insertable = false, updatable = false)
        var createdAt: OffsetDateTime,

        @UpdateTimestamp
        @LastModifiedDate
        @Column(name = "updated_at")
        var updatedAt: OffsetDateTime,

        var status: TransactionStatus,

        var businessWithdrawTransactionId: String?
)
// TODO: create revert transaction and bussines withdraw transaction
