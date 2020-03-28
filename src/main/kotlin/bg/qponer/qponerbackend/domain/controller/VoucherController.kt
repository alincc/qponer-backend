package bg.qponer.qponerbackend.domain.controller

import bg.qponer.qponerbackend.domain.dto.VoucherRequestBody
import bg.qponer.qponerbackend.domain.service.VoucherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1")
class VoucherController(
        @Autowired private val voucherService: VoucherService
) {

    @GetMapping("/vouchers/types")
    fun findTypes() = runServiceMethod { voucherService.findAllTypes() }

    @PostMapping("/vouchers")
    fun buy(@RequestBody body: VoucherRequestBody) = runServiceMethod { voucherService.buyVoucher(body) }
}