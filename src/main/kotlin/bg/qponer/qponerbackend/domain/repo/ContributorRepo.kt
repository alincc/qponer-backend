package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.Contributor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ContributorRepo : JpaRepository<Contributor, Long> {
    fun findByUsername(username: String): Optional<Contributor>
}
