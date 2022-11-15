package com.jonastm

import com.jonastm.adapter.http.api.apiRoutes
import com.jonastm.adapter.http.configure
import com.jonastm.adapter.http.model.News
import com.jonastm.adapter.http.model.internalError
import com.jonastm.adapter.http.plugins.REQUEST_ID_HEADER
import com.jonastm.dto.NewsDTO
import com.jonastm.service.NewsService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

val defaultEntry = NewsDTO(1, "A", "B")
val defaultResp = News(1, "A", "B")

class HttpTest {

    private fun testServer(newsService: NewsService, f: suspend (HttpClient) -> Unit) = testApplication {
        application {
            configure()
            apiRoutes(newsService)
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        f(client)
    }

    @Test
    fun `GET all news`() = testServer(NewsServiceMock()) {
        it.get("/api/v1/news").apply {
            assertEquals(HttpStatusCode.OK, status)
            val content = body<List<News>>()
            assertEquals(1, content.size)
            assertEquals(defaultResp, content.get(0))
            assertNotNull(headers[REQUEST_ID_HEADER])
        }
    }

    @Test
    fun `GET all news with requestID`() = testServer(NewsServiceMock()) {
        val id = "2238_asd1-85c"
        it.get("/api/v1/news") {
            header(REQUEST_ID_HEADER, id)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val content = body<List<News>>()
            assertEquals(1, content.size)
            assertEquals(defaultResp, content.get(0))
            assertEquals(id, headers[REQUEST_ID_HEADER])
        }
    }

    @Test
    fun `GET all news err`() = testServer(ErrorServiceMock()) {
        it.get("/api/v1/news").apply {
            assertEquals(HttpStatusCode.InternalServerError, status)
            assertEquals(internalError(), body())
            assertNotNull(headers[REQUEST_ID_HEADER])
        }
    }

    @Test
    fun `GET all news err with requestID`() = testServer(ErrorServiceMock()) {
        val id = "2238_asd1-85c"
        it.get("/api/v1/news") {
            header(REQUEST_ID_HEADER, id)
        }.apply {
            assertEquals(HttpStatusCode.InternalServerError, status)
            assertEquals(internalError(), body())
            assertEquals(id, headers[REQUEST_ID_HEADER])
        }
    }

    @Test
    fun `GET specific news`() = testServer(NewsServiceMock()) {
        it.get("/api/v1/news/1").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(defaultEntry, body())
            assertNotNull(headers[REQUEST_ID_HEADER])
        }
    }

    @Test
    fun `GET specific news error`() = testServer(ErrorServiceMock()) {
        it.get("/api/v1/news/1").apply {
            assertEquals(HttpStatusCode.InternalServerError, status)
            assertEquals(internalError(), body())
            assertNotNull(headers[REQUEST_ID_HEADER])
        }
    }

    @Test
    fun `POST specific news`() = testServer(NewsServiceMock()) {
        it.post("/api/v1/news") {
            contentType(ContentType.Application.Json)
            setBody(defaultEntry)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(defaultEntry, body())
            assertNotNull(headers[REQUEST_ID_HEADER])
        }
    }

    @Test
    fun `POST specific news error`() = testServer(ErrorServiceMock()) {
        it.post("/api/v1/news") {
            contentType(ContentType.Application.Json)
            setBody(defaultEntry)
        }.apply {
            assertEquals(HttpStatusCode.InternalServerError, status)
            assertEquals(internalError(), body())
            assertNotNull(headers[REQUEST_ID_HEADER])
        }
    }

}

private class NewsServiceMock : NewsService {
    override suspend fun getAllNewsEntries(): List<NewsDTO> {
        return listOf(defaultEntry)
    }

    override suspend fun getNewsEntry(id: Long): NewsDTO {
        return defaultEntry
    }

    override suspend fun addNewsEntry(newsEntry: NewsDTO): NewsDTO {
        return defaultEntry
    }
}

private class ErrorServiceMock : NewsService {
    override suspend fun getAllNewsEntries(): List<NewsDTO> {
        throw Exception("some error")
    }

    override suspend fun getNewsEntry(id: Long): NewsDTO {
        throw Exception("some error")
    }

    override suspend fun addNewsEntry(newsEntry: NewsDTO): NewsDTO {
        throw Exception("some error")
    }
}