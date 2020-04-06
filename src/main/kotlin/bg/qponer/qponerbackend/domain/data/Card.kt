package bg.qponer.qponerbackend.domain.data

import javax.persistence.*

@Entity
class Card(
        @get:Id
        @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SID_CARD_ID_SEQ")
        var id: Long? = null,

        var tokenId: String,

        var displayName: String,

        var expiryDate: String,

        @get:ManyToOne
        var owner: Contributor
)
