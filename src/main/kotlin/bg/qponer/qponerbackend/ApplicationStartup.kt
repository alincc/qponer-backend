package bg.qponer.qponerbackend

import bg.qponer.qponerbackend.domain.data.*
import bg.qponer.qponerbackend.domain.repo.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*
import javax.annotation.PostConstruct

@Component
@Profile("!prod")
class ApplicationStartup(
        @Autowired val businessOwnerRepo: BusinessOwnerRepo,
        @Autowired val contributorRepo: ContributorRepo,
        @Autowired val cityRepo: CityRepo,
        @Autowired val countryRepo: CountryRepo,
        @Autowired val voucherRepo: VoucherRepo,
        @Autowired val voucherTypeRepo: VoucherTypeRepo,
        @Autowired val passwordEncoder: PasswordEncoder
) {

    @PostConstruct
    fun init() {
        val country = Country(code = "BG", name = "Bulgaria")
        countryRepo.save(country)

        val city = City(name = "Sofia", country = country)
        cityRepo.save(city);

        val address = Address(line1 = "Mladost", city = city, country = country, postalCode = "1000", region = "Sofia")
        val businessOwner = BusinessOwner(
                username = "dakata",
                password = passwordEncoder.encode("password"),
                firstName = "Danail",
                lastName = "Danailov",
                address = address,
                dateOfBirth = Calendar.getInstance(),
                nationality = country,
                countryOfResidence = country,
                walletUserId = "1",
                walletId = "1",
                type = BusinessType.BAR,
                businessName = "Masterpiece",
                businessDescription = "le chef deuvre"
        )
        businessOwnerRepo.save(businessOwner)

        val contributor = Contributor(
                username = "pocko",
                password = passwordEncoder.encode("password"),
                firstName = "Pocko",
                lastName = "Pockov",
                address = address,
                dateOfBirth = Calendar.getInstance(),
                nationality = country,
                countryOfResidence = country,
                walletUserId = "1",
                walletId = "1"
        )
        val contributor1 = Contributor(
                username = "pocko2",
                password = passwordEncoder.encode("password"),
                firstName = "Pocko2",
                lastName = "Pockov",
                address = address,
                dateOfBirth = Calendar.getInstance(),
                nationality = country,
                countryOfResidence = country,
                walletUserId = "1",
                walletId = "1"
        )
        contributorRepo.saveAll(listOf(contributor, contributor1))

        val bronzeVoucher = VoucherType(typeName = VoucherTypeName.BRONZE, value = BigDecimal.valueOf(10L))
        val silverVoucher = VoucherType(typeName = VoucherTypeName.SILVER, value = BigDecimal.valueOf(20L))
        val goldVoucher = VoucherType(typeName = VoucherTypeName.GOLD, value = BigDecimal.valueOf(50L))
        voucherTypeRepo.saveAll(listOf(bronzeVoucher, silverVoucher, goldVoucher))

        val vouchers = ArrayList<Voucher>()
        vouchers.add(Voucher(owner = businessOwner, contributor = contributor1, value = BigDecimal(150)))
        vouchers.add(Voucher(owner = businessOwner, contributor = contributor, value = BigDecimal(240)))
        voucherRepo.saveAll(vouchers)

//        createVouchers(contributor, businessOwner, arrayOf(goldVoucher, goldVoucher));
//        createVouchers(contributor1, businessOwner, arrayOf(silverVoucher, silverVoucher, silverVoucher));
    }
}
