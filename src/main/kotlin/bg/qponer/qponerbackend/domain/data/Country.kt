package bg.qponer.qponerbackend.domain.data

import javax.persistence.*

@Entity
class Country(
        @get:Id
        @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SID_COUNTRY_ID_SEQ")
        var id: Long? = null,

        @Column(length = 2)
        var code: String,

        var name: String
)
