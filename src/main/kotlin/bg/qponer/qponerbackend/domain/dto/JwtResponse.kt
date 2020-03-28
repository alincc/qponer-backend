package bg.qponer.qponerbackend.domain.dto

import bg.qponer.qponerbackend.domain.service.auth.data.QponerPrincipalType

class JwtResponse(
        val jwt: String,
        val customerId: Long?,
        val username: String,
        val type: QponerPrincipalType,
        val roles: List<String>
)
