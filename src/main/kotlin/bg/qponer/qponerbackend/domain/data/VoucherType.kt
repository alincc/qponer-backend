package bg.qponer.qponerbackend.domain.data

import java.math.BigDecimal
import javax.persistence.*

@Entity
class VoucherType(
        @get:Id
        @get:GeneratedValue
        var id: Long? = null,

        @get:Enumerated(EnumType.STRING)
        var typeName: VoucherTypeName,

        var value: BigDecimal
)

enum class VoucherTypeName {
    BRONZE, SILVER, GOLD
}