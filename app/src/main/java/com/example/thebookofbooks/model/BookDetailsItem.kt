package com.example.thebookofbooks.model

data class BookDetailsItem(
    val etag: String,
    val id: String,
    val kind: String,
    val saleInfo: SaleInfo,
    val selfLink: String,
    val volumeInfo: VolumeInfoX
)