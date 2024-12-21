package com.example.thebookofbooks.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebookofbooks.data.BooksRepository
import com.example.thebookofbooks.model.Item
import kotlinx.coroutines.launch

sealed interface BooksUiState {
    data class Success(val books: List<Item>) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
}

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {
    var booksUiState : BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    init {
        getBooksDetails()
    }

    private fun getBooksDetails () {
        viewModelScope.launch {

        }
    }

}