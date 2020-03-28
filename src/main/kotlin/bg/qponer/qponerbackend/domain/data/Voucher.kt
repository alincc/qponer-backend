package bg.qponer.qponerbackend.domain.data

import java.math.BigDecimal
import javax.persistence.*

@Entity
class Voucher(
        @get:Id
        @get:GeneratedValue var id: Long? = null,

        @get:Enumerated(EnumType.STRING)
        var type: VoucherType,

        var value: BigDecimal
)

enum class VoucherType {
    BRONZE, SILVER, GOLD
}