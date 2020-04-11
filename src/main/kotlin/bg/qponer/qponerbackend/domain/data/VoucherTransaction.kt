package bg.qponer.qponerbackend.domain.data

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
class VoucherTransaction(
        @get:Id
        @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SID_VOUCHER_TRN_ID_SEQ")
        var id: Long? = null,

        @get:ManyToOne
        var owner: BusinessOwner,

        @get:ManyToOne
        var contributor: Contributor,

        var value: BigDecimal,

        var externalId: String,

        @get:ManyToOne
        var voucherType: VoucherType,

        // TODO: find annotation for auto set current datetime on create
        var createdAt: Date,


        var updatedAt: Date,

        var status: TransactionStatus
)
// TODO: create revert transaction and bussines withdraw transaction
