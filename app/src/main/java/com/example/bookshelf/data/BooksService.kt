package com.example.bookshelf.data

import retrofit2.http.GET
import retrofit2.http.Query

data class SearchBooksResponse(
    val kind: String,
    val totalItems: String,
    val items: List<Book>
)

interface BooksService {
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): SearchBooksResponse
}