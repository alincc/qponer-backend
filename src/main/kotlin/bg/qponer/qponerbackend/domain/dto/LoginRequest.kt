package bg.qponer.qponerbackend.domain.dto

import javax.validation.constraints.NotEmpty

class LoginRequest(
        @NotEmpty val username: String,
        @NotEmpty val password: String
)
