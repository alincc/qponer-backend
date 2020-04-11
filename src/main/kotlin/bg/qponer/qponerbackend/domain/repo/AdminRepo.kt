package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.QponerAdmin
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepo : CrudRepository<QponerAdmin, Long>
