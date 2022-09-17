package de.tm.jonas

import de.tm.jonas.logger.BasicLogging
import de.tm.jonas.logger.LoggingContext
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

        // Test for context-receiver
        with(BasicLogging()) {
            test()
        }
    }.start(wait = true)
}

context(LoggingContext)
fun test() {
    log("test")
}
