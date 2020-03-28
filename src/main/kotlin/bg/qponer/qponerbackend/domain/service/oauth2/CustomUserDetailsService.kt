package bg.qponer.qponerbackend.domain.service.oauth2

import bg.qponer.qponerbackend.domain.repo.BusinessOwnerRepo
import bg.qponer.qponerbackend.domain.service.oauth2.data.QponerPrincipal
import bg.qponer.qponerbackend.domain.service.oauth2.data.QponerPrincipalType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
        @Autowired private val businessOwnerRepo: BusinessOwnerRepo
        // TODO add contributors
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val optionalBusinessOwner = businessOwnerRepo.findByUsername(username)
        if (!optionalBusinessOwner.isPresent) {
            throw UsernameNotFoundException("no user")
        }
        val businessOwner = optionalBusinessOwner.get()
        return QponerPrincipal(
                id = businessOwner.id,
                name = businessOwner.username,
                pwd = businessOwner.password,
                authorityStringList = listOf(QponerPrincipalType.BUSINESS_OWNER.name),
                type = QponerPrincipalType.BUSINESS_OWNER
        )
    }
}
