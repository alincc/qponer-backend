package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.Contributor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContributorRepo : JpaRepository<Contributor, Long>