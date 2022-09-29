package de.tm.jonas.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callid.*

val REQUEST_ID_HEADER = HttpHeaders.XRequestId

fun Application.configureCallID() {
    install(CallId) {
        // Retrieve
        retrieveFromHeader(REQUEST_ID_HEADER)
        generate(24, "0123456789") //fallback if not existent

        // Configure minimal verification
        verify {
            it.isNotEmpty()
        }

        // Respond
        replyToHeader(REQUEST_ID_HEADER)
    }
}