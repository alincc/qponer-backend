package bg.qponer.qponerbackend.domain.service.oauth2.data

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class QponerPrincipal(
        var name: String,
        var pwd: String,
        var authorityStringList: List<String>
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val grantedAuthorities = mutableListOf<SimpleGrantedAuthority>()

        for (authority in authorityStringList) {
            val grantedAuthority = SimpleGrantedAuthority(authority)
            grantedAuthorities.add(grantedAuthority)
        }

        return grantedAuthorities
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return name
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return pwd
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

}
