package com.jonastm.adapter.http

import com.jonastm.Env
import com.jonastm.adapter.http.api.apiRoutes
import com.jonastm.adapter.http.plugins.configureCallID
import com.jonastm.adapter.http.plugins.configureErrorHandling
import com.jonastm.adapter.http.plugins.configureRequestLogging
import com.jonastm.adapter.http.plugins.configureSerialization
import com.jonastm.service.NewsService
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun startServer(newsService: NewsService, config: Env.Http) {
    embeddedServer(CIO, port = config.port) {
        configure()
        apiRoutes(newsService)
    }.start(wait = true)
}

fun Application.configure() {
    configureCallID()
    configureErrorHandling()
    configureSerialization()
    configureRequestLogging()
}
