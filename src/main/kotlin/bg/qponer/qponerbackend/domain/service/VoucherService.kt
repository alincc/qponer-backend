package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.AccumulatedValue
import bg.qponer.qponerbackend.domain.data.TransactionStatus
import bg.qponer.qponerbackend.domain.data.VoucherTransaction
import bg.qponer.qponerbackend.domain.data.VoucherType
import bg.qponer.qponerbackend.domain.dto.TransactionResponseBody
import bg.qponer.qponerbackend.domain.dto.VoucherRequestBody
import bg.qponer.qponerbackend.domain.dto.VoucherResponseBody
import bg.qponer.qponerbackend.domain.dto.VoucherTypeResponseBody
import bg.qponer.qponerbackend.domain.repo.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.OffsetDateTime
import javax.transaction.Transactional

@Service
class VoucherService(
        @Autowired private val accumulatedValueRepo: AccumulatedValueRepo,
        @Autowired private val voucherTypeRepo: VoucherTypeRepo,
        @Autowired private val businessRepo: BusinessRepo,
        @Autowired private val contributorRepo: ContributorRepo,
        @Autowired private val voucherTransactionRepo: VoucherTransactionRepo,
        @Autowired private val cardRepo: CardRepo,
        @Autowired private val mangoPayRepo: MangoPayRepo
) {

    fun findAllTypes() = accumulatedValueRepo.findAll().map { it.toResponseBody() }

    fun findAllForOwner(ownerId: Long) = accumulatedValueRepo.findByBusinessOwner(ownerId).map { it.toResponseBody() }

    fun findAllForContributor(contributorId: Long) = accumulatedValueRepo.findByContributor(contributorId).map { it.toResponseBody() }

    fun findAllTransactionsForContributor(contributorId: Long) = voucherTransactionRepo.findByContributor(contributorId).map { it.toTrnHistoryResponseBody() }

    @Transactional
    fun buyVoucher(body: VoucherRequestBody): VoucherResponseBody {
        val contributor = body.contributorId.toContributorWithId(contributorRepo)
        val owner = body.businessId.toBusinessWithId()
        val card = cardRepo.findByIdOrNull(body.cardId)
                ?: throw InvalidDataException("Missing card with id: ${body.cardId}")
        val amount = body.voucherTypeId.toVoucherTypeWithId().value
        val transaction = VoucherTransaction(
                business = owner,
                contributor = contributor,
                value = amount,
                externalId = "external-transaction-id",
                voucherType = voucherTypeRepo.getOne(body.voucherTypeId),
                status = TransactionStatus.CREATED,
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now(),
                businessWithdrawTransactionId = null
        )

        voucherTransactionRepo.save(transaction)
        if (!mangoPayRepo.transferFunds(
                        contributor.walletId,
                        owner.walletId,
                        card.tokenId,
                        amount)) {
            transaction.status = TransactionStatus.FAILED
            voucherTransactionRepo.save(transaction)
            throw RuntimeException("Could not process transfer")
        }

        transaction.status = TransactionStatus.COMPLETED
        voucherTransactionRepo.save(transaction)
        return accumulatedValueRepo.findByBusinessOwnerAndContributor(body.businessId, body.contributorId)
                .orElse(body.toEntity())
                .apply {
                    allTimeValue += amount
                    pendingValue += amount
                }
                .let { accumulatedValueRepo.save(it) }
                .toResponseBody()
    }


    private fun VoucherType.toResponseBody() =
            VoucherTypeResponseBody(
                    id!!,
                    typeName,
                    value
            )

    private fun VoucherRequestBody.toEntity() =
            AccumulatedValue(
                    business = businessId.toBusinessWithId(),
                    contributor = contributorId.toContributorWithId(contributorRepo),
                    allTimeValue = BigDecimal.ZERO,
                    pendingValue = BigDecimal.ZERO
            )

    private fun Long.toBusinessWithId() = businessRepo.findByIdOrNull(this)
            ?: throw InvalidDataException("Missing owner with id: $this")

    private fun Long.toVoucherTypeWithId() = voucherTypeRepo.findByIdOrNull(this)
            ?: throw InvalidDataException("Invalid AccumulatedValue Type with id: $this")

    private fun AccumulatedValue.toResponseBody() =
            VoucherResponseBody(
                    id!!,
                    business.name,
                    "${contributor.firstName} ${contributor.lastName}",
                    allTimeValue,
                    pendingValue,
                    spendValue,
                    availableValue
            )

    private fun VoucherTransaction.toTrnHistoryResponseBody() =
            TransactionResponseBody(
                    id!!,
                    business.type,
                    business.name,
                    business.description,
                    "${contributor.firstName} ${contributor.lastName}",
                    voucherType,
                    createdAt
            )
}
