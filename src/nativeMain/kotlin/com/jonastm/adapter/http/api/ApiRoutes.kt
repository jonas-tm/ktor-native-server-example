package com.jonastm.adapter.http.api

import com.jonastm.service.NewsService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.apiRoutes(newsService: NewsService) {
    routing {
        route("/api/v1") {
            newsRoutes(newsService)
        }
    }
}