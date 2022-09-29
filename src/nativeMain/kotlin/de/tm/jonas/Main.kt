package de.tm.jonas

import de.tm.jonas.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080) {
        setupServer()
    }.start(wait = true)
}

fun Application.setupServer() {
    configureCallID()
    configureErrorHandling()
    configureSerialization()
    configureRequestLogging()
    configureRouting()
}
