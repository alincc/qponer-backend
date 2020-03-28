package bg.qponer.qponerbackend.domain.data

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Credit(
        @get:Id
        @get:GeneratedValue
        var id: Long? = null,

        @get:ManyToOne
        var owner: BusinessOwner,

        @get:ManyToOne
        var contributor: Contributor,

        var value: BigDecimal
)