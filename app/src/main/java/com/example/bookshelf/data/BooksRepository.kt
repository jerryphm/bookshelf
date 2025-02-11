package com.example.bookshelf.data

class BooksRepository(
    val retrofitService: BooksService
) {
    suspend fun getData(): List<String> {
        val books = retrofitService.searchBooks("jazz+history").items
        val thumbnails = books.map { book -> book.volumeInfo.imageLinks.thumbnail }
        return thumbnails
    }
}