package de.tm.jonas

import de.tm.jonas.model.TestDTO
import de.tm.jonas.plugins.configureRequestLogging
import de.tm.jonas.plugins.configureRouting
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

class IntegrationTests {

    fun testServer(f: suspend (HttpClient) -> Unit) = testApplication {
        application {
            configureRequestLogging()
            configureRouting()
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
        }
    }

    @Test
    fun `GET test`() = testServer {
        it.get("/test").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(TestDTO("Hello World"), body())
        }
    }


}