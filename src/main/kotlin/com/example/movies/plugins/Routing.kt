package com.example.movies.plugins

import com.example.customer.routes.customerRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        customerRouting()
    }
}
