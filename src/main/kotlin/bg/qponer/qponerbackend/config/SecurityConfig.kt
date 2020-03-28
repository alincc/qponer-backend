package bg.qponer.qponerbackend.config

import bg.qponer.qponerbackend.domain.service.oauth2.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
        @Autowired private val customUserDetails: CustomUserDetailsService
): WebSecurityConfigurerAdapter() {

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return customUserDetails
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}
