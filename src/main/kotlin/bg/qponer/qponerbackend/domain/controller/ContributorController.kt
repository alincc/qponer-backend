package bg.qponer.qponerbackend.domain.controller

import bg.qponer.qponerbackend.domain.dto.ContributorRequestBody
import bg.qponer.qponerbackend.domain.service.ContributorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class ContributorController(
        @Autowired private val contributorService: ContributorService
) {

    @PostMapping("/contributors")
    fun create(body: ContributorRequestBody) = contributorService.save(body)

}