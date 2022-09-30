package com.example.customer.models

import kotlinx.serialization.Serializable

@Serializable
data class Customer(val id: String, var firstName: String, var lastName: String, var email: String)

val customerStorage = mutableListOf<Customer>()