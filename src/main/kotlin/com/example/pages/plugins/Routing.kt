package com.example.pages.plugins

import com.example.pages.routes.pagesRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*

fun Application.configureRouting() {
    routing {
        pagesRouting()
        static("/static") {
            resources("files")
        }
    }
}
