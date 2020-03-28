package bg.qponer.qponerbackend.domain.service.auth

import bg.qponer.qponerbackend.domain.repo.BusinessOwnerRepo
import bg.qponer.qponerbackend.domain.repo.ContributorRepo
import bg.qponer.qponerbackend.domain.service.auth.data.QponerPrincipal
import bg.qponer.qponerbackend.domain.service.auth.data.QponerPrincipalType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
        @Autowired private val businessOwnerRepo: BusinessOwnerRepo,
        @Autowired private val contributorRepo: ContributorRepo
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        var userDetails = loadBusinessOwner(username)
        if (userDetails == null) {
            userDetails = loadContributor(username)
        }

        if (userDetails == null) {
            throw UsernameNotFoundException("no user")
        } else {
            return userDetails
        }
    }

    private fun loadBusinessOwner(username: String): QponerPrincipal? {
        val optionalBusinessOwner = businessOwnerRepo.findByUsername(username)
        if (!optionalBusinessOwner.isPresent) {
            return null
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

    private fun loadContributor(username: String): QponerPrincipal? {
        val optionalContributor = contributorRepo.findByUsername(username)
        if (!optionalContributor.isPresent) {
            return null
        }

        val contributor = optionalContributor.get()
        return QponerPrincipal(
                id = contributor.id,
                name = contributor.username,
                pwd = contributor.password,
                authorityStringList = listOf(QponerPrincipalType.CONTRIBUTOR.name),
                type = QponerPrincipalType.CONTRIBUTOR
        )
    }
}
