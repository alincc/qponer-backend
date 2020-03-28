package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.Address
import bg.qponer.qponerbackend.domain.data.CardRegistration
import bg.qponer.qponerbackend.domain.data.Country
import com.mangopay.MangoPayApi
import com.mangopay.core.Money
import com.mangopay.core.enumerations.*
import com.mangopay.entities.PayIn
import com.mangopay.entities.UserNatural
import com.mangopay.entities.Wallet
import com.mangopay.entities.subentities.PayInExecutionDetailsDirect
import com.mangopay.entities.subentities.PayInPaymentDetailsCard
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

@Repository
class MangoPayRepo(
        private val api: MangoPayApi
) {

    fun createUser(
            firstName: String,
            lastName: String,
            address: Address,
            dateOfBirth: Calendar,
            nationality: Country,
            countryOfResidence: Country,
            email: String
    ): Pair<String, String> {
        val user = createMangoPayUser(firstName, lastName, address, dateOfBirth, nationality, countryOfResidence, email)
        val userId = api.Users.create(user).Id
        val walletId = api.Wallets.create(createWallet(userId, email)).Id
        return Pair(userId, walletId)
    }

    fun createCardRegistration(
            walletUserId: String
    ): CardRegistration {
        val cardRegistration = com.mangopay.entities.CardRegistration().apply {
            UserId = walletUserId
            Currency = CurrencyIso.BGN
            CardType = com.mangopay.core.enumerations.CardType.CB_VISA_MASTERCARD
        }

        val internalRegistration = api.CardRegistrations.create(cardRegistration)
        return CardRegistration(
                internalRegistration.AccessKey,
                api.Config.BaseUrl,
                internalRegistration.Id,
                internalRegistration.CardRegistrationURL,
                CardType.CB_VISA_MASTERCARD.name,
                api.Config.ClientId,
                internalRegistration.PreregistrationData
        )
    }

    fun completeCardRegistration(registrationId: String, registrationData: String): String {
        val cardRegistration = com.mangopay.entities.CardRegistration().apply {
            Id = registrationId
            RegistrationData = registrationData
        }
        return api.CardRegistrations.update(cardRegistration).CardId
    }

    fun transferFunds(
            contributorWalletId: String,
            ownerWalletId: String,
            cardTokenId: String,
            amount: BigDecimal
    ): Boolean {
        val payIn = PayIn().apply {
            PaymentType = PayInPaymentType.CARD
            ExecutionType = PayInExecutionType.DIRECT
            AuthorId = contributorWalletId
            DebitedFunds = Money().apply {
                Amount = amount.multiply(CENTS).intValueExact()
                Currency = CurrencyIso.BGN
            }
            Fees = Money().apply {
                Amount = amount.multiply(FEE_PERCENT).multiply(CENTS).intValueExact()
                Currency = CurrencyIso.BGN
            }
            CreditedWalletId = ownerWalletId
            PaymentDetails = PayInPaymentDetailsCard().apply {
                CardId = cardTokenId
                CardType = com.mangopay.core.enumerations.CardType.CB_VISA_MASTERCARD
            }
            ExecutionDetails = PayInExecutionDetailsDirect().apply {
                CardId = cardTokenId
                SecureModeReturnURL = "www.test.com"
            }
        }

        // TODO: This is bad
        return api.PayIns.create(payIn).Status != TransactionStatus.FAILED
    }

    private fun createWallet(userId: String, email: String): Wallet {
        return Wallet().apply {
            Owners = arrayListOf(userId)
            Description = "Wallet for $email"
            Currency = CurrencyIso.BGN
        }
    }

    private fun createMangoPayUser(firstName: String, lastName: String, address: Address, dateOfBirth: Calendar, nationality: Country, countryOfResidence: Country, username: String): UserNatural {
        return UserNatural().apply {
            FirstName = firstName
            LastName = lastName
            Address = com.mangopay.core.Address().apply {
                AddressLine1 = address.line1
                AddressLine2 = address.line2
                City = address.city.name
                Region = address.region
                PostalCode = address.postalCode
                Country = CountryIso.valueOf(address.country.code)
            }
            Birthday = dateOfBirth.timeInMillis
            Nationality = CountryIso.valueOf(nationality.code)
            CountryOfResidence = CountryIso.valueOf(countryOfResidence.code)
            Email = username
        }
    }

    companion object {
        private val FEE_PERCENT = BigDecimal(0.025)
        private val CENTS = BigDecimal(100)
    }

}