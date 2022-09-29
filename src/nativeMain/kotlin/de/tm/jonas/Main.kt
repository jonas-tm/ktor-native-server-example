package de.tm.jonas

import de.tm.jonas.plugins.configureErrorHandling
import de.tm.jonas.plugins.configureRequestLogging
import de.tm.jonas.plugins.configureRouting
import de.tm.jonas.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080) {
        setupServer()
    }.start(wait = true)
}

fun Application.setupServer() {
    configureErrorHandling()
    configureSerialization()
    configureRequestLogging()
    configureRouting()
}
