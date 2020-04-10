package bg.qponer.qponerbackend.domain.controller

import bg.qponer.qponerbackend.domain.dto.BusinessOwnerRequestBody
import bg.qponer.qponerbackend.domain.service.BusinessOwnerService
import bg.qponer.qponerbackend.domain.service.VoucherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/businessOwners")
class BusinessOwnerController(
        @Autowired private val businessOwnerService: BusinessOwnerService,
        @Autowired private val voucherService: VoucherService
) {

    @PostMapping
    fun create(@Valid @RequestBody body: BusinessOwnerRequestBody) = runServiceMethod {
        businessOwnerService.save(body)
    }

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping
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

    @PreAuthorize("hasRole('BUSINESS_OWNER') or hasRole('CONTRIBUTOR')")
    @GetMapping("/{id}/top-contributors")
    fun listTopContributors(@PathVariable("id") ownerId: Long) = runServiceMethod {
        businessOwnerService.getTopContributors(ownerId)
    }

    @GetMapping("/{id}/vouchers")
    fun findVouchersForOwner(@PathVariable("id") ownerId: Long) = runServiceMethod {
        voucherService.findAllForOwner(ownerId)
    }
}



