package bg.qponer.qponerbackend.domain.data

import java.util.*
import javax.persistence.*

@Entity
class Contributor(
        id: Long? = null,

        username: String,

        password: String,

        phone: String,

        email: String,

        var avatarUrl: String? = null,

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
) : User(
        id,
        username,
        password,
        email,
        phone
)
