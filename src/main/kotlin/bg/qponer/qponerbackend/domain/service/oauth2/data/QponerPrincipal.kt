package bg.qponer.qponerbackend.domain.service.oauth2.data

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class QponerPrincipal(
        val id: Long?,
        val name: String,
        @JsonIgnore val pwd: String,
        val type: QponerPrincipalType,
        val authorityStringList: List<String>
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val grantedAuthorities = mutableListOf<SimpleGrantedAuthority>()

        for (authority in authorityStringList) {
            val grantedAuthority = SimpleGrantedAuthority("ROLE_$authority")
            grantedAuthorities.add(grantedAuthority)
        }
        grantedAuthorities.add(SimpleGrantedAuthority("ROLE_USER"))

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
