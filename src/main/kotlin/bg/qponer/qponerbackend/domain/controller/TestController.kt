package bg.qponer.qponerbackend.domain.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/test/")
class TestController {
    @GetMapping("/all")
    fun allAccess(): String {
        return "Public Content."
    }

    @GetMapping("/contributor")
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    fun userAccess(): String {
        return "Contributor Content."
    }

    @GetMapping("/business-owner")
    @PreAuthorize("hasRole('BUSINESS_OWNER')")
    fun adminAccess(): String {
        return "Admin Board."
    }
}
