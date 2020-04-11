package bg.qponer.qponerbackend.domain.data

import javax.persistence.Entity

@Entity
class QponerAdmin(
        id: Long? = null,
        username: String,
        password: String,
        email: String,
        phone: String
) : User(
        id,
        username,
        password,
        email,
        phone
)
