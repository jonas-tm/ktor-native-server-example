package routes.customer

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val CUSTOMER_ROUTE = "customers"
const val CUSTOMER_ID = "customerId"

fun Route.customerRoutes() {
    route("/$CUSTOMER_ROUTE") {
        get {
            getAllCustomers(call)
        }
        get("/{$CUSTOMER_ID}") {
            getCustomerById(call)
        }
    }
}

private suspend fun getAllCustomers(call: ApplicationCall) {
    call.respond(arrayListOf(
        Customer(1, "Jonas", "Test"),
        Customer(2, "Hans", "Test")
    ))
}

private suspend fun getCustomerById(call: ApplicationCall) {
    val idSt = call.parameters[CUSTOMER_ID]
    if (idSt == null) {
        call.response.status(HttpStatusCode.BadRequest)
        call.respondText("Missing path parameter customer id")
        return
    }
    var id: Int
    try {
        id = idSt.toInt()
    } catch (e: NumberFormatException) {
        call.response.status(HttpStatusCode.BadRequest)
        call.respondText("Invalid format of path parameter customer id")
        return
    }
    val customer = Customer(id, "Random", "Test")
    call.respond(customer)
}