package bg.qponer.qponerbackend.domain.data

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Card(
        @get:Id
        @get:GeneratedValue
        var id: Long? = null,

        var tokenId: String,

        var displayName: String,

        var expiryDate: String
)
