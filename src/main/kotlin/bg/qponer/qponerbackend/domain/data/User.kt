package bg.qponer.qponerbackend.domain.data

import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class User(
        @get:Id
        @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SID_USER_ID_SEQ")
        var id: Long?,

        var username: String,

        var password: String,

        var avatarUrl: String?,

        var firstName: String,

        var lastName: String,

        @get:Embedded
        var address: Address,

        @get:Temporal(TemporalType.DATE)
        var dateOfBirth: Calendar,

        @get:ManyToOne
        var nationality: Country,

        @get:ManyToOne
        var countryOfResidence: Country,

        var walletUserId: String,

        var walletId: String
)

