package bg.qponer.qponerbackend.domain.controller

import bg.qponer.qponerbackend.domain.dto.AuthUser
import bg.qponer.qponerbackend.domain.service.auth.data.QponerPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
class AuthController {

    @GetMapping("/api/me")
    fun me(): AuthUser {
        val principal = SecurityContextHolder.getContext().authentication.principal as QponerPrincipal
        val roles: List<String> = principal.authorities.stream()
                .map { item -> item.authority }
                .collect(Collectors.toList())
        return AuthUser(
                id = principal.id,
                name = principal.name,
                type = principal.type.name,
                roles = roles
        )
    }
}
