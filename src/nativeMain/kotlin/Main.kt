import io.ktor.server.cio.*
import io.ktor.server.engine.*
import plugins.configureRequestLogging
import plugins.configureRouting
import plugins.configureSerialization

fun main() {
    embeddedServer(CIO, port = 8080) {
        configureSerialization()
        configureRequestLogging()
        configureRouting()
    }.start(wait = true)
}
