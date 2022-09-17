package de.tm.jonas.plugins

import de.tm.jonas.customer.customerRoutes
import de.tm.jonas.model.TestDTO
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        get("/test") {
            call.respond(TestDTO("Hello World"))
        }
        get("/coroutine") {
            val result = async {
                delay(5.seconds)
                "Hello World Async"
            }
            call.respondText(result.await())
        }
        customerRoutes()
    }
    log.info("Registered routing")
}