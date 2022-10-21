package com.example.pages.routes

import com.example.pages.templates.HomeTemplate
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*

fun Route.pagesRouting() {
    route("/pages") {
        get {
            call.respondHtmlTemplate(HomeTemplate()) {
                this.content = "all"
                this.header = "all"
            }
        }
    }
}