package com.example.thebookofbooks.data

import com.example.thebookofbooks.BuildConfig
import com.example.thebookofbooks.R
import com.example.thebookofbooks.model.BookDetailsItem
import com.example.thebookofbooks.model.BookResponse
import com.example.thebookofbooks.model.Item
import com.example.thebookofbooks.network.BooksApi

interface BooksRepository {
    suspend fun getBooksList(
        query: String,
        orderBy: String?,
        printType: String?,
        startIndex: Int
    ): BookResponse

    suspend fun getBookDetails(id: String): BookDetailsItem
}

class NetworkBooksRepository(
    private val booksApi: BooksApi
) : BooksRepository {
    private val apiKey = BuildConfig.apiKey
    override suspend fun getBooksList(
        query: String,
        orderBy: String?,
        printType: String?,
        startIndex: Int,
    ): BookResponse =
        booksApi.searchBooks(query, apiKey, orderBy, printType, startIndex)

    override suspend fun getBookDetails(id: String): BookDetailsItem = booksApi.getBook(id, apiKey)
}