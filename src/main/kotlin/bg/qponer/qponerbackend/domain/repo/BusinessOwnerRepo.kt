package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.BusinessOwner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BusinessOwnerRepo
    : JpaRepository<BusinessOwner, Long>,
        CanFilterBusinessOwner