package bg.qponer.qponerbackend.domain.dto

class AuthUser(
        val id: Long,
        val name: String,
        val type: String,
        val roles: List<String>
)
