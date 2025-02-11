package com.example.bookshelf.data

data class Book(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val imageLinks: ImageLinks
)

data class ImageLinks(
    val thumbnail: String
)