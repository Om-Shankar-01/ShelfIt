package com.example.thebookofbooks.model

data class BookResponse(
    val items: List<Item>,
    val totalItems: Int
)