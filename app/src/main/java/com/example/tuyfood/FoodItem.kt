package com.example.tuyfood

data class FoodItem(
    val name: String,
    val price: Int,
    val emoji: String,
    val id: Int = 0,
    val description: String = "",
    val category: String = "",
    val rating: Double = 0.0,
    val badge: String = ""
)