package bg.qponer.qponerbackend.domain.data

import javax.persistence.*

@Entity
class City(
       @Id @GeneratedValue var id: Long? = null,
       var name: String,
       @ManyToOne var country: Country
)