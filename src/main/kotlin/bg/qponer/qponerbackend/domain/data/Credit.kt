package bg.qponer.qponerbackend.domain.data

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Credit(
        @Id @GeneratedValue var id: Long? = null,
        @ManyToOne var owner: BusinessOwner,
        @ManyToOne var contributor: Contributor,
        var value: BigDecimal
)