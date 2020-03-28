package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.VoucherType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VoucherTypeRepo : JpaRepository<VoucherType, Long>