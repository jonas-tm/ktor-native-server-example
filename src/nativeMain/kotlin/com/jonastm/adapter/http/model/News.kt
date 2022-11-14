package com.jonastm.adapter.http.model

import com.jonastm.dto.NewsDTO
import io.ktor.server.request.*
import kotlinx.serialization.Serializable

@Serializable
data class News(
    val id: Long = 0,
    val title: String,
    val text: String,
)

suspend fun ApplicationRequest.parseNews(): Result<NewsDTO> {
    val news = this.call.receiveNullable<News>();
    if (news != null) {
        return Result.success(NewsDTO(news.id, news.title, news.text))
    }
    return Result.failure(Exception("could not read body"))
}

fun NewsDTO.toResponse() = News(this.id, this.title, this.text)