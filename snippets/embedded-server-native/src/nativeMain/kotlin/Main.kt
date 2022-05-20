import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*
import plugins.configureRequestLogging
import plugins.configureSerialization
import routes.customer.customerRoutes

fun main() {
    embeddedServer(CIO, port = 8080) {
        configureSerialization()
        configureRequestLogging()
        routing {
            customerRoutes()
        }
    }.start(wait = true)
}
