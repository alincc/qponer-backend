package bg.qponer.qponerbackend.domain.controller

import bg.qponer.qponerbackend.domain.service.InvalidDataException
import bg.qponer.qponerbackend.domain.service.MissingDataException
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Level
import java.util.logging.Logger

val log: Logger = Logger.getLogger("ControllerExt")

inline fun <T, R> T.runServiceMethod(block: () -> R): R {
    return try {
        block()
    } catch (throwable: Throwable) {
        log.log(Level.SEVERE, "exception thrown during service call", throwable)
        when (throwable) {
            is InvalidDataException -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, throwable.message)
            is MissingDataException -> throw ResponseStatusException(HttpStatus.NOT_FOUND, throwable.message)
            else -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, throwable.message)
        }
    }
}