package bg.qponer.qponerbackend.domain.repo

import bg.qponer.qponerbackend.domain.data.Address
import bg.qponer.qponerbackend.domain.data.CardRegistration
import bg.qponer.qponerbackend.domain.data.Country
import com.mangopay.MangoPayApi
import com.mangopay.core.Money
import com.mangopay.core.enumerations.*
import com.mangopay.entities.*
import com.mangopay.entities.subentities.PayInExecutionDetailsDirect
import com.mangopay.entities.subentities.PayInPaymentDetailsCard
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

@Repository
class MangoPayRepo(
        private val api: MangoPayApi
) {

    fun createNaturalUser(
            firstName: String,
            lastName: String,
            address: Address,
            dateOfBirth: Calendar,
            nationality: Country,
            countryOfResidence: Country,
            email: String
    ): Pair<String, String> =
            persistUser(
                    createMangoPayUser(
                            firstName,
                            lastName,
                            address,
                            dateOfBirth,
                            nationality,
                            countryOfResidence,
                            email
                    )
            )


    fun createLegalUser(
            headquartersAddress: Address,
            name: String,
            legalRepresentativeDateOfBirth: Calendar,
            legalRepresentativeCountryOfResidence: Country,
            legalRepresentativeNationality: Country,
            legalRepresentativeFirstName: String,
            legalRepresentativeLastName: String,
            email: String
    ): Pair<String, String> =
            persistUser(
                    createMangoPayLegalUser(
                            headquartersAddress,
                            name,
                            legalRepresentativeDateOfBirth,
                            legalRepresentativeCountryOfResidence,
                            legalRepresentativeNationality,
                            legalRepresentativeFirstName,
                            legalRepresentativeLastName,
                            email
                    )
            )

    private fun persistUser(user: User): Pair<String, String> {
        val userId = api.Users.create(user).Id
        val walletId = api.Wallets.create(createWallet(userId, user.Email)).Id
        return Pair(walletId, userId)
    }

    fun createCardRegistration(
            walletUserId: String
    ): CardRegistration {
        val cardRegistration = com.mangopay.entities.CardRegistration().apply {
            UserId = walletUserId
            Currency = CurrencyIso.EUR
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

    fun transferFunds(
            contributorWalletUserId: String,
            ownerWalletId: String,
            cardTokenId: String,
            amount: BigDecimal
    ): Boolean {
        val payIn = PayIn().apply {
            PaymentType = PayInPaymentType.CARD
            ExecutionType = PayInExecutionType.DIRECT
            AuthorId = contributorWalletUserId
            DebitedFunds = Money().apply {
                Amount = amount.multiply(CENTS).toInt()
                Currency = CurrencyIso.EUR
            }
            Fees = Money().apply {
                Amount = PAY_IN_FEE
                Currency = CurrencyIso.EUR
            }
            CreditedWalletId = ownerWalletId
            PaymentDetails = PayInPaymentDetailsCard().apply {
                CardId = cardTokenId
                CardType = com.mangopay.core.enumerations.CardType.CB_VISA_MASTERCARD
            }
            ExecutionDetails = PayInExecutionDetailsDirect().apply {
                CardId = cardTokenId
                SecureModeReturnURL = "http://test.com"
            }
        }

        // TODO: This is bad
        return api.PayIns.create(payIn).Status != TransactionStatus.FAILED
    }

    private fun createWallet(userId: String, email: String): Wallet {
        return Wallet().apply {
            Owners = arrayListOf(userId)
            Description = "Wallet for $email"
            Currency = CurrencyIso.EUR
        }
    }

    private fun createMangoPayUser(firstName: String, lastName: String, address: Address, dateOfBirth: Calendar, nationality: Country, countryOfResidence: Country, username: String): UserNatural {
        return UserNatural().apply {
            FirstName = firstName
            LastName = lastName
            Address = address.toMangoPayAddress()
            Birthday = dateOfBirth.toMangoPayBirthday()
            Nationality = CountryIso.valueOf(nationality.code)
            CountryOfResidence = CountryIso.valueOf(countryOfResidence.code)
            Email = username
        }
    }

    private fun createMangoPayLegalUser(
            headquartersAddress: Address,
            name: String,
            legalRepresentativeDateOfBirth: Calendar,
            legalRepresentativeCountryOfResidence: Country,
            legalRepresentativeNationality: Country,
            legalRepresentativeFirstName: String,
            legalRepresentativeLastName: String,
            email: String
    ): UserLegal {
        return UserLegal().apply {
            LegalPersonType = com.mangopay.core.enumerations.LegalPersonType.BUSINESS
            HeadquartersAddress = headquartersAddress.toMangoPayAddress()
            Name = name
            LegalRepresentativeBirthday = legalRepresentativeDateOfBirth.toMangoPayBirthday()
            LegalRepresentativeCountryOfResidence = CountryIso.valueOf(legalRepresentativeCountryOfResidence.code)
            LegalRepresentativeNationality = CountryIso.valueOf(legalRepresentativeNationality.code)
            LegalRepresentativeFirstName = legalRepresentativeFirstName
            LegalRepresentativeLastName = legalRepresentativeLastName
            Email = email
        }
    }

    private fun Address.toMangoPayAddress() = com.mangopay.core.Address().apply {
        AddressLine1 = line1
        AddressLine2 = line2
        City = city.name
        Region = region
        PostalCode = postalCode
        Country = CountryIso.valueOf(country.code)
    }

    private fun Calendar.toMangoPayBirthday() =
            Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    .apply {
                        set(
                                this@toMangoPayBirthday.get(Calendar.YEAR),
                                this@toMangoPayBirthday.get(Calendar.MONTH),
                                this@toMangoPayBirthday.get(Calendar.DAY_OF_MONTH),
                                0,
                                0,
                                0
                        )
                    }.timeInMillis / 1000

    companion object {
        private val PAY_IN_FEE = 50
        private val CENTS = BigDecimal(100)
    }

}
