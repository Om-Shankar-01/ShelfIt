package com.example.thebookofbooks.network

import com.example.thebookofbooks.model.BookResponse
import com.example.thebookofbooks.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    suspend fun getBooks(query: String): BookResponse

    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id: String,
        @Query("key") key: String
    ): Item
}