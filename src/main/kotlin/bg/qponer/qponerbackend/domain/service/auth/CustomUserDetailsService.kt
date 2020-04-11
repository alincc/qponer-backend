package bg.qponer.qponerbackend.domain.service.auth

import bg.qponer.qponerbackend.domain.data.BusinessOwner
import bg.qponer.qponerbackend.domain.data.Contributor
import bg.qponer.qponerbackend.domain.data.QponerAdmin
import bg.qponer.qponerbackend.domain.data.User
import bg.qponer.qponerbackend.domain.repo.UserRepo
import bg.qponer.qponerbackend.domain.service.auth.data.QponerPrincipal
import bg.qponer.qponerbackend.domain.service.auth.data.QponerPrincipalType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
//        @Autowired private val businessOwnerRepo: BusinessOwnerRepo,
//        @Autowired private val contributorRepo: ContributorRepo
        @Autowired private val userRepo: UserRepo
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val optionalUser = userRepo.findFirstByUsername(username)
        if (!optionalUser.isPresent) {
            throw UsernameNotFoundException("no user")
        }

        val user = optionalUser.get()
        val userType = getUserType(user)
        return QponerPrincipal(
                id = user.id!!,
                name = user.username,
                pwd = user.password,
                authorityStringList = listOf(userType.name),
                type = userType
        )
    }

    private fun getUserType(user: User): QponerPrincipalType {
        if (Contributor::class.java.isAssignableFrom(user.javaClass)) {
            return QponerPrincipalType.CONTRIBUTOR
        } else if (BusinessOwner::class.java.isAssignableFrom(user.javaClass)) {
            return QponerPrincipalType.BUSINESS_OWNER
        } else if (QponerAdmin::class.java.isAssignableFrom(user.javaClass)) {
            return QponerPrincipalType.ADMIN
        } else {
            throw UsernameNotFoundException("unsupported user type")
        }
    }
}
