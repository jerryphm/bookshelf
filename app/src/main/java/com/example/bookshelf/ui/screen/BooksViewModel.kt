package com.example.bookshelf.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface BooksData {
    data class Success(val thumbnails: List<String>) : BooksData
    class Loading : BooksData
    class Error : BooksData
}

class BooksViewModel(
    val booksRepository: BooksRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<BooksData> = MutableStateFlow(BooksData.Loading())
    val uiState = _uiState.asStateFlow()

    private fun getBooks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.value = try {
                    BooksData.Success(booksRepository.getThumbnails())
                } catch (e: Exception) {
                    BooksData.Error()
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(booksRepository)
            }
        }
    }

    init {
        getBooks()
    }
}