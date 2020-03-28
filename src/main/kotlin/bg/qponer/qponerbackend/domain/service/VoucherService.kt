package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.Voucher
import bg.qponer.qponerbackend.domain.data.VoucherType
import bg.qponer.qponerbackend.domain.dto.VoucherRequestBody
import bg.qponer.qponerbackend.domain.dto.VoucherResponseBody
import bg.qponer.qponerbackend.domain.dto.VoucherTypeResponseBody
import bg.qponer.qponerbackend.domain.repo.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.math.BigDecimal
import javax.transaction.Transactional

@Service
class VoucherService(
        @Autowired private val voucherTypeRepo: VoucherTypeRepo,
        @Autowired private val voucherRepo: VoucherRepo,
        @Autowired private val businessOwnerRepo: BusinessOwnerRepo,
        @Autowired private val contributorRepo: ContributorRepo,
        @Autowired private val cardRepo: CardRepo,
        @Autowired private val mangoPayRepo: MangoPayRepo
) {

    fun findAllTypes() = voucherTypeRepo.findAll().map { it.toResponseBody() }

    fun findAllForOwner(ownerId: Long) = voucherRepo.findByBusinessOwner(ownerId).map { it.toResponseBody() }

    fun findAllForContributor(contributorId: Long) = voucherRepo.findByContributor(contributorId).map { it.toResponseBody() }

    @Transactional
    fun buyVoucher(body: VoucherRequestBody): VoucherResponseBody {
        val contributor = body.contributorId.toContributorWithId(contributorRepo)
        val owner = body.businessOwnerId.toBusinessOwnerWithId()
        val card = cardRepo.findByIdOrNull(body.cardId)
                ?: throw InvalidDataException("Missing card with id: ${body.cardId}")
        val amount = body.voucherTypeId.toVoucherTypeWithId().value
        if (!mangoPayRepo.transferFunds(
                        contributor.walletId,
                        owner.walletId,
                        card.tokenId,
                        amount)) {
            throw RuntimeException("Could not process transfer")
        }

        return voucherRepo.findByBusinessOwnerAndContributor(body.businessOwnerId, body.contributorId)
                .orElse(body.toEntity())
                .apply {
                    value += amount
                }
                .let { voucherRepo.save(it) }
                .toResponseBody()
    }


    private fun VoucherType.toResponseBody() =
            VoucherTypeResponseBody(
                    id!!,
                    typeName,
                    value
            )

    private fun VoucherRequestBody.toEntity() =
            Voucher(
                    owner = businessOwnerId.toBusinessOwnerWithId(),
                    contributor = contributorId.toContributorWithId(contributorRepo),
                    value = BigDecimal.ZERO
            )

    private fun Long.toBusinessOwnerWithId() = businessOwnerRepo.findByIdOrNull(this)
            ?: throw InvalidDataException("Missing owner with id: $this")

    private fun Long.toVoucherTypeWithId() = voucherTypeRepo.findByIdOrNull(this)
            ?: throw InvalidDataException("Invalid Voucher Type with id: $this")

    private fun Voucher.toResponseBody() =
            VoucherResponseBody(
                    id!!,
                    owner.businessName,
                    "${contributor.firstName} ${contributor.lastName}",
                    value
            )
}
