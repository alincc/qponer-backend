package bg.qponer.qponerbackend.domain.data

import javax.persistence.*

@Entity
class City(
        @get:Id
        @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SID_CITY_ID_SEQ")
        var id: Long? = null,

        var name: String,

        @get:ManyToOne
        var country: Country
)
