package com.example.movies.routes

import com.example.customer.dao.TemporalDao
import com.example.customer.models.Customer
import com.example.customer.models.customerStorage
import com.example.movies.models.Image
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.io.path.Path
import kotlin.io.path.writeBytes
import kotlin.properties.Delegates

fun Route.customerRouting() {
    val temporalDao = TemporalDao()
    route("/images") {
        get {
            val data = call.receiveMultipart()
            var id by Delegates.notNull<Int>()
            var name by Delegates.notNull<String>()
            var fileName by Delegates.notNull<String>()
            val guyList = mutableListOf<Image>()
            data.forEachPart {
                when (it) {
                    is PartData.FormItem -> {
                        if(it.name == "id"){
                            id = it.value.toInt()
                        }
                        else{
                            name = it.value
                        }
                    }
                    is PartData.FileItem -> {
                        fileName = it.originalFileName as String
                        val fileBytes = it.streamProvider().readBytes()
                        Path("uploads/$fileName").writeBytes(fileBytes)
                    }
                    else -> {}
                }}
            val guy = Image(id, name, fileName)
            guyList.add(guy)
            call.respondText("Guy stored correctly and \"$fileName is uploaded to 'uploads/$fileName'\"", status = HttpStatusCode.Created)

            temporalDao.getAll() ?:
            call.respondText("No customers found", status = HttpStatusCode.OK)
        }
        get("/uploads/{imageName}") {
            val imageName = call.parameters["imageName"]
            var file = File("./uploads/$imageName")
            if(file.exists()){
                call.respondFile(File("./uploads/$imageName"))
            }
            else{
                call.respondText("Image not found", status = HttpStatusCode.NotFound)
            }
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