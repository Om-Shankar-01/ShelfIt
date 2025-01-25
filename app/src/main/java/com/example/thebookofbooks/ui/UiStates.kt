package com.example.thebookofbooks.ui

import com.example.thebookofbooks.model.BookDetailsItem
import com.example.thebookofbooks.model.BookResponse
import com.example.thebookofbooks.model.Item

sealed interface ResultScreenUiState {
    data class Success (
        val response : BookResponse,
        val isHomeScreen : Boolean = true
    ) : ResultScreenUiState
    data class Error (
        val errorMessage : String?,
        val isHomeScreen : Boolean = false
    ) : ResultScreenUiState
    data class Loading (
        val isHomeScreen : Boolean = false
    ): ResultScreenUiState
    data class Empty (
        val isHomeScreen : Boolean = false
    ) : ResultScreenUiState
}

sealed interface DetailsScreenUiState {
    data class Success (
        val bookDetails : BookDetailsItem,
        val isHomeScreen : Boolean = false
    ) : DetailsScreenUiState
    data class Error (
        val errorMessage : String?,
        val isHomeScreen : Boolean = false
    ) : DetailsScreenUiState
    data class Loading (
        val isHomeScreen : Boolean = false
    ): DetailsScreenUiState
//    data class Empty (
//        val isHomeScreen : Boolean = false
//    ) : DetailsScreenUiState
}