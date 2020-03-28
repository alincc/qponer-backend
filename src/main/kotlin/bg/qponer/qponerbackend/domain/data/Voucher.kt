package bg.qponer.qponerbackend.domain.data

import java.math.BigDecimal
import javax.persistence.*

@Entity
class Voucher(
        @Id @GeneratedValue var id: Long? = null,
        @Enumerated(EnumType.STRING) var type: VoucherType,
        var value: BigDecimal
)

enum class VoucherType {
    BRONZE, SILVER, GOLD
}