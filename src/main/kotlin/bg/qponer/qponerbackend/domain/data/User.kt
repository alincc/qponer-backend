package bg.qponer.qponerbackend.domain.data

import javax.persistence.*

@Entity
@Table(name = "registered_user")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class User(
        @get:Id
        @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SID_USER_ID_SEQ")
        var id: Long?,

        var username: String,

        var password: String,

        var email: String,

        var phone: String
)

