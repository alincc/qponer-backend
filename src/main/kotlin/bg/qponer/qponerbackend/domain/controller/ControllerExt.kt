package bg.qponer.qponerbackend.domain.controller

import bg.qponer.qponerbackend.domain.service.InvalidDataException
import bg.qponer.qponerbackend.domain.service.MissingDataException
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

inline fun <T, R> T.runServiceMethod(block: () -> R): R {
    return try {
        block()
    } catch (throwable: Throwable) {
        when (throwable) {
            is InvalidDataException -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, throwable.message)
            is MissingDataException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, throwable.message)
            else -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, throwable.message)
        }
    }
}