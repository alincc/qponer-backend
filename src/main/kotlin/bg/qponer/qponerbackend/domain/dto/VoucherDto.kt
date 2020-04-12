package bg.qponer.qponerbackend.domain.dto

import bg.qponer.qponerbackend.domain.data.VoucherTypeName
import java.math.BigDecimal

data class VoucherTypeResponseBody(
        val id: Long,
        val typeName: VoucherTypeName,
        val value: BigDecimal
)

data class VoucherRequestBody(
        val businessId: Long,
        val contributorId: Long,
        val cardId: Long,
        val voucherTypeId: Long
)

data class VoucherResponseBody(
        val id: Long,
        val businessName: String,
        val contributorName: String,
        val value: BigDecimal,
        val pendingValue: BigDecimal,
        val spendValue: BigDecimal?,
        val availableValue: BigDecimal?
)
