package bg.qponer.qponerbackend.domain.data

import java.util.*
import javax.persistence.Entity

@Entity
class Contributor(
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
        walletId: String
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
