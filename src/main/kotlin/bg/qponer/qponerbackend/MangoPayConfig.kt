package bg.qponer.qponerbackend

import com.mangopay.MangoPayApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val MANGOPAY_API_KEY = "rxF6rJziRq6RnWEYzPzJVqFo5HYG83eLBZ2N0h0nfDadcufYMH"
private const val MANGOPAY_CLIENT_ID = "qponer"

@Configuration
class MangoPayConfig {

    @Bean
    fun mangoPayApi(): MangoPayApi =
            MangoPayApi().apply {
                Config.ClientId = MANGOPAY_CLIENT_ID
                Config.ClientPassword = MANGOPAY_API_KEY
            }

}