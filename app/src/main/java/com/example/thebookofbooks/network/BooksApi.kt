package com.example.thebookofbooks.network

import com.example.thebookofbooks.model.BookDetailsItem
import com.example.thebookofbooks.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("api_key") key: String,
        @Query("orderBy") orderBy: String?,
        @Query("printType") printType: String?,
        @Query("startIndex") startIndex: Int
    ): BookResponse

    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id: String,
        @Query("key") key: String
    ): BookDetailsItem
}