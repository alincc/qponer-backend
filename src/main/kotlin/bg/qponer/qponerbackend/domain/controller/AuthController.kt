package bg.qponer.qponerbackend.domain.controller

import bg.qponer.qponerbackend.auth.JwtUtils
import bg.qponer.qponerbackend.domain.dto.JwtResponse
import bg.qponer.qponerbackend.domain.dto.LoginRequest
import bg.qponer.qponerbackend.domain.service.auth.data.QponerPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid


//@CrossOrigin(origins = ["*"], maxAge = 3600)
//@RestController
//@RequestMapping("/api/auth")
class AuthController(
        @Autowired private val authenticationManager: AuthenticationManager,
        @Autowired private val jwtUtils: JwtUtils
) {

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest): ResponseEntity<JwtResponse> {
        val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtUtils.generateJwtToken(authentication)
        val userDetails: QponerPrincipal = authentication.principal as QponerPrincipal
        val roles: List<String> = userDetails.authorities.stream()
                .map { item -> item.authority }
                .collect(Collectors.toList())
        return ResponseEntity.ok(JwtResponse(
                jwt,
                userDetails.id,
                userDetails.username,
                userDetails.type,
                roles))
    }
}
