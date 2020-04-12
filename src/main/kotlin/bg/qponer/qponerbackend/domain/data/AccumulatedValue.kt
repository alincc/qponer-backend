package bg.qponer.qponerbackend.domain.data

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(
        uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("business_id", "contributor_id"))],
        indexes = [Index(columnList = "business_id, contributor_id", unique = true)]
)
class AccumulatedValue(
        @get:Id
        @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SID_ACCUMULATED_VALUE_ID_SEQ")
        var id: Long? = null,

        @get:ManyToOne
        var business: Business,

        @get:ManyToOne
        var contributor: Contributor,

        var allTimeValue: BigDecimal,

        var availableValue: BigDecimal? = BigDecimal.ZERO,

        var spendValue: BigDecimal? = BigDecimal.ZERO,

        var pendingValue: BigDecimal
)
