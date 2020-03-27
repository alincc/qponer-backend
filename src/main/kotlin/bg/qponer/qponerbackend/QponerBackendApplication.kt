package bg.qponer.qponerbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QponerBackendApplication

fun main(args: Array<String>) {
	runApplication<QponerBackendApplication>(*args)
}
