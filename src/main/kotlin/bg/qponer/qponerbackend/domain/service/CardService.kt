package bg.qponer.qponerbackend.domain.service

import bg.qponer.qponerbackend.domain.data.Card
import bg.qponer.qponerbackend.domain.data.CardRegistration
import bg.qponer.qponerbackend.domain.dto.CardRegistrationResponseBody
import bg.qponer.qponerbackend.domain.dto.CardRequestBody
import bg.qponer.qponerbackend.domain.dto.CardResponseBody
import bg.qponer.qponerbackend.domain.repo.CardRepo
import bg.qponer.qponerbackend.domain.repo.ContributorRepo
import bg.qponer.qponerbackend.domain.repo.MangoPayRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CardService(
        @Autowired private val cardRepo: CardRepo,
        @Autowired private val contributorRepo: ContributorRepo,
        @Autowired private val mangoPayRepo: MangoPayRepo
) {

    fun createRegistration(ownerId: Long): CardRegistrationResponseBody {
        val owner = ownerId.toContributorWithId(contributorRepo)

        return mangoPayRepo.createCardRegistration(owner.walletUserId).toResponseBody()
    }

    @Transactional
    fun completeRegistration(ownerId: Long, body: CardRequestBody) =
            createCard(ownerId, body)
                    .let { cardRepo.save(it) }
                    .toResponseBody()

    fun findAllByOwnerId(ownerId: Long) =
            cardRepo.findByOwnerId(ownerId)
                    .map { it.toResponseBody() }

    private fun String.asMaskedCreditCardNumber() = this.replaceRange(0, length - 5, "*".repeat(length - 4))

    private fun Card.toResponseBody() =
            CardResponseBody(
                    id!!,
                    displayName,
                    expiryDate
            )

    private fun CardRegistration.toResponseBody() =
            CardRegistrationResponseBody(
                    accessKey, baseUrl, cardPreregistrationId, cardRegistrationUrl, cardType, clientId, preregistrationData
            )

    private fun createCard(ownerId: Long, body: CardRequestBody) =
            Card(
                    tokenId = body.cardId,
                    displayName = body.number,
                    expiryDate = body.expiryDate,
                    owner = ownerId.toContributorWithId(contributorRepo))
}