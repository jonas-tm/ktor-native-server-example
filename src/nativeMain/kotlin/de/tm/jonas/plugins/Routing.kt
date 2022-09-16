package de.tm.jonas.plugins

import de.tm.jonas.customer.customerRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import de.tm.jonas.model.TestDTO

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        get("/test") {
            call.respond(TestDTO("Hello World"))
        }
        customerRoutes()
    }
    log.info("Registered routing")
}