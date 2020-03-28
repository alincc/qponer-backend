package bg.qponer.qponerbackend.domain.controller

import bg.qponer.qponerbackend.domain.dto.BusinessOwnerRequestBody
import bg.qponer.qponerbackend.domain.service.BusinessOwnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1")
class BusinessOwnerController(
        @Autowired private val businessOwnerService: BusinessOwnerService
) {

    @PostMapping("/businessOwners")
    fun create(@Valid @RequestBody body: BusinessOwnerRequestBody) = runServiceMethod {
        businessOwnerService.save(body)
    }

    @GetMapping("/businessOwners")
    fun findAllByFilter(
            @RequestParam(required = false, name = "countryId") countryId: Long?,
            @RequestParam(required = false, name = "cityId") cityId: Long?,
            @RequestParam(required = false, name = "type") type: String?,
            @RequestParam(required = false, name = "query") query: String?
    ) = runServiceMethod {
        businessOwnerService.findAllByFilter(
                countryId,
                cityId,
                type,
                query
        )
    }
}



