package de.tm.jonas.plugins

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.request.*
import io.ktor.util.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val TIME_KEY = AttributeKey<Instant>("call_time")

fun Application.configureRequestLogging() {
    install(RequestLoggingPlugin)
    log.info("Registered logging plugin")
}

val RequestLoggingPlugin = createApplicationPlugin(name = "RequestLoggingPlugin") {
    on(ResponseSent) { call ->
        val startTime = call.attributes[TIME_KEY]
        val duration = Clock.System.now().minus(startTime).inWholeMilliseconds
        val timeReq = startTime.toLocalDateTime(TimeZone.UTC)

        val status = call.response.status()?.value ?: -1

        application.log.info("[${call.request.httpMethod.value}] Request handled --> [uri]: ${call.request.uri}, [status]: $status, [duration]: ${duration}ms, [http]: ${call.request.httpVersion}, [time]: $timeReq")
    }

    on(CallSetup) { call ->
        call.attributes.put(TIME_KEY, Clock.System.now())
    }
}