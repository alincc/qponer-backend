package bg.qponer.qponerbackend.domain.data

import java.util.*
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class BusinessOwner(
        id: Long? = null,
        username: String,
        password: String,
        avatarUrl: String? = null,
        firstName: String,
        lastName: String,
        address: Address,
        dateOfBirth: Calendar,
        nationality: Country,
        countryOfResidence: Country,
        walletUserId: String,
        walletId: String,
        @Enumerated(EnumType.STRING) var type: BusinessType,
        var businessName: String,
        var businessDescription: String
) : User(
        id,
        username,
        password,
        avatarUrl,
        firstName,
        lastName,
        address,
        dateOfBirth,
        nationality,
        countryOfResidence,
        walletUserId,
        walletId
)

enum class BusinessType {
    RESTAURANT, BAR, DISCO
}
