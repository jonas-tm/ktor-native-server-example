package de.tonndorf_martini.jonas

import de.tonndorf_martini.jonas.plugins.configureRequestLogging
import de.tonndorf_martini.jonas.plugins.configureRouting
import de.tonndorf_martini.jonas.plugins.configureSerialization
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080) {
        configureSerialization()
        configureRequestLogging()
        configureRouting()
    }.start(wait = true)
}
