package com.example.thebookofbooks.data

import com.example.thebookofbooks.model.BookResponse
import com.example.thebookofbooks.model.Item
import com.example.thebookofbooks.network.BooksApi

interface BooksRepository {
    suspend fun getBooksList(query: String): BookResponse
    suspend fun getBook(id: String) : Item
}

class NetworkBooksRepository(
    private val booksApi: BooksApi
) : BooksRepository {
    override suspend fun getBooksList(query: String): BookResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getBook(id: String): Item {
        TODO("Not yet implemented")
    }
}