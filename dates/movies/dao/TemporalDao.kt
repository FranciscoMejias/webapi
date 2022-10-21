package com.example.movies.dao

import com.example.customer.models.Customer
import com.example.movies.models.Image
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.io.path.*
import kotlin.io.path.Path

class TemporalDao {
    private val tmpPath = Path("/tmp")
    private val uploadPath = Path(tmpPath.pathString, "upload")

    private val customersListRead: List<Image> get() =
            Json.decodeFromString(uploadPath.readText())

    init {
        if (uploadPath.notExists()) {
            uploadPath.createDirectory()
        }
    }

    fun post(customer: Customer) {
        if (uploadPath.readText().isEmpty()){
            uploadPath.writeText(Json.encodeToString(listOf(customer)))
        } else {
            val customers: MutableList<Customer> = Json.decodeFromString(uploadPath.readText())
            customers.add(customer)
            uploadPath.writeText(Json.encodeToString(customers))
        }
    }
}

