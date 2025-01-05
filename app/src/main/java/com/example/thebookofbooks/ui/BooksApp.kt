package com.example.thebookofbooks.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thebookofbooks.R
import com.example.thebookofbooks.data.BooksViewModel
import com.example.thebookofbooks.ui.screens.BookDetailsScreen
import com.example.thebookofbooks.ui.screens.BooksStartScreen
import com.example.thebookofbooks.ui.screens.ResultsScreen

@Composable
fun BooksApp(
    navController: NavHostController = rememberNavController()
) {

    val booksViewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory)
    val query = booksViewModel.query

    Scaffold(
        topBar = {
            BookTopAppBar()
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BookScreen.START.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BookScreen.START.name) {
                BooksStartScreen(
                    query = query,
                    onTextFieldChanged = { booksViewModel.updateQuery(it) },
                    onSearchButtonClicked = {
                        navController.navigate(BookScreen.RESULT.name)
                        booksViewModel.fetchResponse()
                    }
                )
            }

            composable(route = BookScreen.RESULT.name) { backStackEntry ->
                ResultsScreen(
                    resultScreenUiState = booksViewModel.resultScreenUiState.collectAsState().value,
                    updateBookId = { booksViewModel.updateBookId(it) },
                    retryAction = { booksViewModel.fetchResponse() },
                    onImageClicked = {
                        navController.navigate(BookScreen.DETAILS.name)
                        booksViewModel.onImageClicked()
                    }
                )
            }

            composable( route = BookScreen.DETAILS.name) { backStackEntry ->
                // val bookId = backStackEntry.arguments?.getString("bookId")
                val detailsScreenUiState = booksViewModel.detailsScreenUiState.collectAsState().value
//                if (bookId != null) {
                    BookDetailsScreen(detailsScreenUiState)
//                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) }
    )
}