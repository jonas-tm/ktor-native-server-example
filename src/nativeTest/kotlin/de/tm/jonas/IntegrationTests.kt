package de.tm.jonas

import de.tm.jonas.model.TestDTO
import de.tm.jonas.plugins.REQUEST_ID_HEADER
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class IntegrationTests {

    fun testServer(f: suspend (HttpClient) -> Unit) = testApplication {
        application {
            setupServer()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        f(client)
    }

    @Test
    fun `GET index`() = testServer {
        it.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello, world!", bodyAsText())
            assertNotNull(headers[REQUEST_ID_HEADER])
        }
    }

    @Test
    fun `GET index with request ID`() = testServer {
        val id = "123_asd-7747"
        it.get("/") {
            header(REQUEST_ID_HEADER, id)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello, world!", bodyAsText())
            assertEquals(id, headers[REQUEST_ID_HEADER])
        }
    }

    @Test
    fun `GET test`() = testServer {
        it.get("/test").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(TestDTO("Hello World"), body())
            assertNotNull(headers[REQUEST_ID_HEADER])
        }
    }

}