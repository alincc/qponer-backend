package bg.qponer.qponerbackend.domain.dto

import bg.qponer.qponerbackend.domain.data.BusinessType
import bg.qponer.qponerbackend.domain.data.VoucherType

import java.time.OffsetDateTime

data class TransactionResponseBody(
        var id: Long? = null,
        var businessType: BusinessType,
        var businessName: String,
        var businessDescription: String,
        var contributorName: String,
        var voucherType: VoucherType,
        var createdAt: OffsetDateTime
        )
