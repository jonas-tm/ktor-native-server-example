package plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.TestDTO
import routes.customer.customerRoutes

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