package bg.qponer.qponerbackend.domain.service.oauth2

import bg.qponer.qponerbackend.domain.repo.BusinessOwnerRepo
import bg.qponer.qponerbackend.domain.service.oauth2.data.QponerPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
        @Autowired private val businessOwnerRepo: BusinessOwnerRepo
        // TODO add contributors
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val businessOwner = businessOwnerRepo.findByUsername(username)
        return QponerPrincipal(name = businessOwner.username, pwd = businessOwner.password, authorityStringList = listOf("BUSINESS_OWNER"))
    }
}
