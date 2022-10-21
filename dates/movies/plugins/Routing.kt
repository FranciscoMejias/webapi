package com.example.movies.plugins

import com.example.customer.routes.customerRouting
import com.example.movies.routes.moviesRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        moviesRouting()
    }
}
