package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.Card
import bg.qponer.qponerbackend.domain.dto.CardRequestBody
import bg.qponer.qponerbackend.domain.dto.CardResponseBody
import bg.qponer.qponerbackend.domain.repo.CardRepo
import bg.qponer.qponerbackend.domain.repo.ContributorRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CardService(
        @Autowired private val cardRepo: CardRepo,
        @Autowired private val contributorRepo: ContributorRepo
) {

    @Transactional
    fun save(
            ownerId: Long,
            body: CardRequestBody
    ) =
            body.toEntity(ownerId, contributorRepo)
                    .let { cardRepo.save(it) }
                    .toResponseBody()


    fun findAllByOwnerId(ownerId: Long) =
            cardRepo.findByOwnerId(ownerId)
                    .map { it.toResponseBody() }

    private fun CardRequestBody.toEntity(ownerId: Long, contributorRepo: ContributorRepo) =
            Card(
                    tokenId = "TBD",
                    displayName = number.asMaskedCreditCardNumber(),
                    expiryDate = expiryDate,
                    owner = ownerId.toContributorWithId(contributorRepo)
            )

    private fun String.asMaskedCreditCardNumber() = this.replaceRange(0, length - 5, "*".repeat(length - 4))

    private fun Card.toResponseBody() =
            CardResponseBody(
                    id!!,
                    displayName,
                    expiryDate
            )
}