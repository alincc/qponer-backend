package bg.qponer.qponerbackend.domain.data

import java.util.*
import javax.persistence.*

@Entity
class BusinessOwner(
        id: Long? = null,

        username: String,

        password: String,

        email: String,

        phone: String,

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

        var walletId: String,

        @Enumerated(EnumType.STRING) var type: BusinessType,
        var businessName: String,

        var businessDescription: String
) : User(
        id,
        username,
        password,
        email,
        phone
)

enum class BusinessType {
    RESTAURANT, BAR, DISCO
}
