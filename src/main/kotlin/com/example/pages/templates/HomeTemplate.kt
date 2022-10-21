package com.example.pages.templates

import io.ktor.server.html.*
import kotlinx.html.*

class HomeTemplate: Template<HTML> {
    lateinit var header: String
    lateinit var content: String
    override fun HTML.apply() {
        head {
            if(header == "all"){
                insert(Header(), TemplatePlaceholder())
            }
        }
        body {
            if(content == "all"){
                insert(AllFilmsTemplate(), TemplatePlaceholder())
            }
        }
    }
}

class Header: Template<HEAD> {
    override fun HEAD.apply() {
        link(rel = "stylesheet", href = "/static/main.css", type = "text/css")
        link(rel = "icon", href = "/static/dani.png", type="image/png")
    }
}

class AllFilmsTemplate: Template<BODY> {
    override fun BODY.apply() {
        img {
            src = "/static/dani.png"
            alt = "Italian Trulli"
            height = "50px"
            width = "50px"
        }
        table {
            style = "border-color:black;"
            tr {
                style = "border-color:black;"
                td {
                    style = "border-color:black;"
                    +"""hola"""
                }
                td {
                    style = "border-color:black;"
                    +"""hola"""
                }
            }
            tr {
                style = "border-color:black;"
                td {
                    style = "border-color:black;"
                    +"""adios"""
                }
                td {
                    style = "border-color:black;"
                    +"""adios"""
                }
                td {
                    style = "border-color:black;"
                    +"""adios"""
                }
            }
        }
    }
}

