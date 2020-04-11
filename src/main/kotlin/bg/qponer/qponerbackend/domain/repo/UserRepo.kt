package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.User
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepo : org.springframework.data.repository.Repository<User, Long> {

    fun findFirstByUsername(username: String): Optional<User>
}
