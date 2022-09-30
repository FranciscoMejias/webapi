package com.example.movies.routes

import com.example.customer.dao.TemporalDao
import com.example.customer.models.Customer
import com.example.customer.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    val temporalDao = TemporalDao()

    route("/customer") {
        get {
            temporalDao.getAll() ?:
            call.respondText("No customers found", status = HttpStatusCode.OK)
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        post {
            val customer = call.receive<Customer>()
            temporalDao.post(customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (customerStorage.removeIf { it.id == id }) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
        put("{id?}") {
            val customer = call.receive<Customer>()
            customerStorage.find { it.id == customer.id }?.let {
                it.firstName = customer.firstName
                it.lastName = customer.lastName
                it.email = customer.email
                call.respond(HttpStatusCode.OK)
            } ?: return@put call.respond(HttpStatusCode.NotFound)
        }
    }
}