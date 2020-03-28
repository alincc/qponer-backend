package bg.qponer.qponerbackend.domain.data

import javax.persistence.*

@Entity
class City(
       @get:Id
       @get:GeneratedValue
       var id: Long? = null,

       var name: String,

       @get:ManyToOne
       var country: Country
)