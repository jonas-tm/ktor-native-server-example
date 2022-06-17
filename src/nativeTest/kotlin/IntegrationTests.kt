//import io.ktor.client.request.*
//import io.ktor.client.statement.*
//import io.ktor.http.HttpStatusCode
//import io.ktor.server.testing.*
//import plugins.configureRequestLogging
//import plugins.configureRouting
//import plugins.configureSerialization
//import kotlin.test.Test
//import kotlin.test.assertEquals
//
//class IntegrationTests {
//
//    @Test
//    fun testRoot() = testApplication {
//
//        application {
//            configureSerialization()
//            configureRequestLogging()
//            configureRouting()
//        }
//
//        val response = client.get("/")
//        assertEquals(HttpStatusCode.OK, response.status)
//        assertEquals("Hello, world!", response.bodyAsText())
//    }
//
//}