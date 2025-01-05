package com.example.thebookofbooks.model

data class VolumeInfo(
    val authors: List<String>,
    val averageRating: Double,
    val categories: List<String>,
    val contentVersion: String,
    val description: String,
    val imageLinks: ImageLinks,
    val infoLink: String,
    val language: String,
    val pageCount: Int,
    val previewLink: String,
    val printType: String,
    val publishedDate: String,
    val publisher: String,
    val subtitle: String,
    val title: String
)