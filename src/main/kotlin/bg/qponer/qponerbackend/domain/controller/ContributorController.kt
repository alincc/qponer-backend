package bg.qponer.qponerbackend.domain.controller

import bg.qponer.qponerbackend.domain.dto.CardRequestBody
import bg.qponer.qponerbackend.domain.dto.ContributorRequestBody
import bg.qponer.qponerbackend.domain.service.CardService
import bg.qponer.qponerbackend.domain.service.ContributorService
import bg.qponer.qponerbackend.domain.service.VoucherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("api/v1")
class ContributorController(
        @Autowired private val contributorService: ContributorService,
        @Autowired private val cardService: CardService,
        @Autowired private val voucherService: VoucherService
) {

    @PostMapping("/contributors")
    fun create(
            @RequestBody body: ContributorRequestBody
    ) = runServiceMethod {
        contributorService.save(body)
    }

    @GetMapping("/contributors/{id}/cards")
    fun findCardsForContributor(
            @PathParam("id") contributorId: Long
    ) = runServiceMethod {
        cardService.findAllByOwnerId(contributorId)
    }

    @PostMapping("/contributors/{id}/cards")
    fun createCardForContributor(
            @PathParam("id") contributorId: Long,
            @RequestBody body: CardRequestBody
    ) = runServiceMethod {
        cardService.save(contributorId, body)
    }

    @GetMapping("/contributors/{id}/vouchers")
    fun findVouchersForContributor(
            @PathParam("id") contributorId: Long
    ) = runServiceMethod {
        voucherService.findAllForContributor(contributorId)
    }
}