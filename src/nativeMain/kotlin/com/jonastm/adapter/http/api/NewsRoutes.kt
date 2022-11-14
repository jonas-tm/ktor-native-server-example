package com.jonastm.adapter.http.api


import com.jonastm.adapter.http.model.InvalidBodyError
import com.jonastm.adapter.http.model.NotFoundError
import com.jonastm.adapter.http.model.parseNews
import com.jonastm.adapter.http.model.toResponse
import com.jonastm.service.NewsService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val NEWS_ID = "newsID"

fun Route.newsRoutes(newsService: NewsService) {
    route("/news") {
        get {
            newsService.getAllNewsEntries().let {
                call.respond(HttpStatusCode.OK, it.map { it.toResponse() }.toList())
            }
        }
        get("/{$NEWS_ID}") {
            call.parameters[NEWS_ID]?.let {
                val newsID = it.toLong()
                newsService.getNewsEntry(newsID)?.let { newsDTO ->
                    call.respond(newsDTO.toResponse())
                } ?: kotlin.run {
                    call.respond(HttpStatusCode.NotFound, NotFoundError())
                }
            }
        }
        post {
            val result = call.request.parseNews()
            result.onFailure {
                call.respond(HttpStatusCode.BadRequest, InvalidBodyError())
            }
            result.onSuccess {
                val savedEntry = newsService.addNewsEntry(it)
                call.respond(savedEntry.toResponse())
            }

        }
    }
}