package bg.qponer.qponerbackend.domain.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal

class RankedContributor(
        val name: String,

        @get:JsonFormat(shape = JsonFormat.Shape.STRING)
        val amount: BigDecimal
)
