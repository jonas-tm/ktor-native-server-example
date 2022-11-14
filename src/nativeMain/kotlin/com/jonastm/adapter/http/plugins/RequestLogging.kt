package com.jonastm.adapter.http.plugins

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.request.*
import io.ktor.util.*
import kotlinx.datetime.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val TIME_KEY = AttributeKey<Instant>("call_time")

fun Application.configureRequestLogging() {
    install(createRequestLoggingPlugin())
    log.info("Registered logging plugin")
}

val JSON = Json {
    allowStructuredMapKeys = true
    isLenient = true
    prettyPrint = true
}

@Serializable
private data class Request(val requestID: String?, val method: String, val uri: String, val status: Int, val duration: Long, val httpVersion: String, val time: LocalDateTime)

private fun createRequestLoggingPlugin() = createApplicationPlugin(name = "RequestLoggingPlugin") {
    on(ResponseSent) { call ->
        with(call) {
            val startTime = attributes[TIME_KEY]
            val duration = Clock.System.now().minus(startTime).inWholeMilliseconds
            val timeReq = startTime.toLocalDateTime(TimeZone.UTC)
            val status = response.status()?.value ?: -1

            val logMsg = JSON.encodeToString(
                Request(
                requestID = call.callId,
                method = request.httpMethod.value,
                uri = request.uri,
                status = status,
                duration = duration,
                httpVersion = request.httpVersion,
                time = timeReq,
            )
            )

            when {
                status >= 400 -> application.log.error(logMsg)
                else -> application.log.info(logMsg)
            }
        }
    }

    on(CallSetup) { call ->
        call.attributes.put(TIME_KEY, Clock.System.now())
    }
}