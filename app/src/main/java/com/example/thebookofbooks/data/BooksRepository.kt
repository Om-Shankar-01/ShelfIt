package com.example.thebookofbooks.data

import com.example.thebookofbooks.BuildConfig
import com.example.thebookofbooks.R
import com.example.thebookofbooks.model.BookResponse
import com.example.thebookofbooks.model.Item
import com.example.thebookofbooks.network.BooksApi

interface BooksRepository {
    suspend fun getBooksList(query: String): BookResponse
    suspend fun getBookDetails(id: String) : Item
}

class NetworkBooksRepository(
    private val booksApi: BooksApi
) : BooksRepository {
    private val apiKey = BuildConfig.apiKey
    override suspend fun getBooksList(query: String): BookResponse = booksApi.searchBooks(query, apiKey)

    override suspend fun getBookDetails(id: String): Item = booksApi.getBook(id, apiKey)
}