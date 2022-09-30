package com.example.movies.models

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.Year

@Serializable
data class Customer(val id: String, var firstName: String, var lastName: String, var email: String)

val customerStorage = mutableListOf<Customer>()

@Serializable
class Movie(
    val id: Int,
    val title: String,
    val year: Date.Year,
    val gender: String,
    val director: String)

@Serializable
class Comment(
    val id: Int, 
    val movieID: Int, 
    val comment: String,
    val createDate: Date
)

@Serializable
class Date(
    val day: Day,
    val month: Month,
    val year: Year,
) {
    @Serializable
    class Day() {

    }

    @Serializable
    class Month(val month: String) {
        override fun toString() = month
    }

    @Serializable
    class Year(val year: String) {
        override fun toString() = year
    }

}