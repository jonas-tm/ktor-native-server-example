package de.tm.jonas

import de.tm.jonas.plugins.configureRequestLogging
import de.tm.jonas.plugins.configureRouting
import de.tm.jonas.plugins.configureSerialization
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080) {
        configureSerialization()
        configureRequestLogging()
        configureRouting()
    }.start(wait = true)
}
