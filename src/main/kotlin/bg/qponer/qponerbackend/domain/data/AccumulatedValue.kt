package bg.qponer.qponerbackend.domain.data

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(
        uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("owner_id", "contributor_id"))],
        indexes = [Index(columnList = "owner_id, contributor_id", unique = true)]
)
class AccumulatedValue(
        @get:Id
        @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SID_ACCUMULATED_VALUE_ID_SEQ")
        var id: Long? = null,

        @get:ManyToOne
        var owner: BusinessOwner,

        @get:ManyToOne
        var contributor: Contributor,

        var allTimeValue: BigDecimal,

        var availableValue: BigDecimal? = BigDecimal.ZERO
)
