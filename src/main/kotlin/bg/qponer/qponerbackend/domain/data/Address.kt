package bg.qponer.qponerbackend.domain.data

import javax.persistence.Embeddable
import javax.persistence.ManyToOne

@Embeddable
class Address(
        var line1: String,

        var line2: String?,

        @get:ManyToOne
        var city: City,

        @get:ManyToOne
        var country: Country,

        var postalCode: String,

        var region: String
)