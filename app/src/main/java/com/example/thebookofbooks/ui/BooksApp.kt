package com.example.thebookofbooks.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thebookofbooks.R
import com.example.thebookofbooks.data.BooksViewModel
import com.example.thebookofbooks.ui.screens.BookDetailsScreen
import com.example.thebookofbooks.ui.screens.BooksStartScreen
import com.example.thebookofbooks.ui.screens.ResultsScreen
import com.example.thebookofbooks.ui.theme.baskervilleFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val booksViewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory)
    val query = booksViewModel.displayedQuery

    val currentScreenRoute = backStackEntry?.destination?.route ?: BookScreen.START.name

    val title = when (currentScreenRoute) {
        BookScreen.RESULT.name -> "Results for \"$query\""
        BookScreen.DETAILS.name -> "Book Details"
        else -> stringResource(R.string.app_name)
    }

    val canNavigateBack = navController.previousBackStackEntry != null

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                if (currentScreenRoute != BookScreen.START.name) {
                    BookTopAppBar(
                        title = title,
                        canNavigateBack = canNavigateBack,
                        navigateUp = { navController.navigateUp() }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = BookScreen.START.name,
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(durationMillis = 500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(durationMillis = 500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(durationMillis = 500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(durationMillis = 500)
                    )
                }
            ) {
                composable(route = BookScreen.START.name) {
                    BooksStartScreen(
                        booksViewModel = booksViewModel,
                        onSearchButtonClicked = {
                            navController.navigate(BookScreen.RESULT.name)
                            booksViewModel.executeSearch()
                        }
                    )
                }

                composable(route = BookScreen.RESULT.name) { backStackEntry ->
                    ResultsScreen(
                        booksViewModel = booksViewModel,
                        retryAction = { booksViewModel.fetchBooksForCurrentPage() },
                        onImageClicked = {
                            navController.navigate(route = BookScreen.DETAILS.name)
                            booksViewModel.onImageClicked()
                        },
                        onNewSearch = {
                            booksViewModel.executeSearch()
                            navController.navigate(route = BookScreen.RESULT.name)
                        }
                    )
                }

                composable(route = BookScreen.DETAILS.name) { backStackEntry ->
                    val detailsScreenUiState =
                        booksViewModel.detailsScreenUiState.collectAsState().value
                    BookDetailsScreen(detailsScreenUiState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(title, fontFamily = baskervilleFamily, fontWeight = FontWeight.Bold) },
        modifier = modifier,
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        navigationIcon = {
            if (canNavigateBack) {
                FilledIconButton(
                    onClick = navigateUp, colors = IconButtonDefaults.filledIconButtonColors(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onPrimary,
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}