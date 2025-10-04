package com.example.thebookofbooks.model

data class VolumeInfoX(
    val allowAnonLogging: Boolean,
    val authors: List<String>?,
    val canonicalVolumeLink: String,
    val categories: List<String>?,
    val contentVersion: String,
    val description: String?,
    val dimensions: Dimensions?,
    val imageLinks: ImageLinksX?,
    val infoLink: String,
    val language: String,
    val maturityRating: String,
    val pageCount: Int?,
    val previewLink: String,
    val printType: String,
    val printedPageCount: Int?,
    val publishedDate: String?,
    val publisher: String?,
    val readingModes: ReadingModes,
    val title: String
)