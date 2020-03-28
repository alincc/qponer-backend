package bg.qponer.qponerbackend

import bg.qponer.qponerbackend.domain.data.*
import bg.qponer.qponerbackend.domain.repo.BusinessOwnerRepo
import bg.qponer.qponerbackend.domain.repo.CityRepo
import bg.qponer.qponerbackend.domain.repo.CountryRepo
import bg.qponer.qponerbackend.domain.repo.VoucherTypeRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*
import javax.annotation.PostConstruct

@Component
class ApplicationStartup(
        @Autowired var businessOwnerRepo: BusinessOwnerRepo,
        @Autowired var cityRepo: CityRepo,
        @Autowired var countryRepo: CountryRepo,
        @Autowired var voucherTypeRepo: VoucherTypeRepo,
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

        val bronzeVoucher = VoucherType(typeName = VoucherTypeName.BRONZE, value = BigDecimal.valueOf(10L))
        val silverVoucher = VoucherType(typeName = VoucherTypeName.SILVER, value = BigDecimal.valueOf(20L))
        val goldVoucher = VoucherType(typeName = VoucherTypeName.GOLD, value = BigDecimal.valueOf(50L))
        voucherTypeRepo.saveAll(listOf(bronzeVoucher, silverVoucher, goldVoucher))
    }
}
