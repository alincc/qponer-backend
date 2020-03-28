package bg.qponer.qponerbackend.domain.data

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Country(
        @get:Id
        @get:GeneratedValue
        var id: Long? = null,

        @Column(length = 2)
        var code: String,

        var name: String
)
