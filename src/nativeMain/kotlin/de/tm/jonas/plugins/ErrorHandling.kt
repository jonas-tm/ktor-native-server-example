package de.tm.jonas.plugins

import de.tm.jonas.model.ErrorDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<Throwable> { call, throwable ->
            call.application.log.error("call error", throwable)
            call.respond(HttpStatusCode.InternalServerError, ErrorDTO("Internal server error"))
        }
    }
}
