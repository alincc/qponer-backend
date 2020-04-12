package bg.qponer.qponerbackend.domain.data

import java.util.*
import javax.persistence.*

@Entity
class Business(
        id: Long? = null,

        username: String,

        password: String,

        email: String,

        phone: String,

        var logoUrl: String? = null,

        var pictureUrl: String? = null,

        @get:Embedded
        var headquartersAddress: Address,

        @get:Enumerated(EnumType.STRING)
        var type: BusinessType,

        var name: String,

        @get:Column(columnDefinition = "TEXT")
        @get:Lob
        var description: String,

        @get:Column(columnDefinition = "TEXT")
        @get:Lob
        var additionalBenefits: String,

        var websiteUrl: String,

        var legalRepresentativeFirstName: String,

        var legalRepresentativeLastName: String,

        /**
         * Be careful to ensure that the time is midnight UTC
         * (otherwise a local time can be understood as 23h UTC,
         * and therefore rendering the wrong date which will
         * present problems when needing to validate the KYC identity)
         */
        @get:Temporal(TemporalType.DATE)
        var legalRepresentativeDateOfBirth: Calendar,

        @get:ManyToOne
        var legalRepresentativeNationality: Country,

        @get:ManyToOne
        var legalRepresentativeCountryOfResidence: Country,

        var walletUserId: String,

        var walletId: String

) : User(
        id,
        username,
        password,
        email,
        phone
)

enum class BusinessType {
    RESTAURANT, BAR, DISCO, CAFE
}
