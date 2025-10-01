package com.example.thebookofbooks.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.thebookofbooks.R
import com.example.thebookofbooks.data.BooksViewModel
import com.example.thebookofbooks.model.BookResponse
import com.example.thebookofbooks.model.ImageLinks
import com.example.thebookofbooks.model.Item
import com.example.thebookofbooks.model.VolumeInfo
import com.example.thebookofbooks.ui.ResultScreenUiState
import com.example.thebookofbooks.ui.screens.common.SearchBar
import com.example.thebookofbooks.ui.theme.TheBookOfBooksTheme
import com.example.thebookofbooks.ui.theme.baskervilleFamily


@Composable
fun ResultsScreen(
    booksViewModel: BooksViewModel,
    retryAction: () -> Unit,
    onImageClicked: () -> Unit,
    onNewSearch: () -> Unit,
) {
    val resultScreenUiState by booksViewModel.resultScreenUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        /*** Search Bar ***/
        SearchBar(
            query = booksViewModel.searchQuery,
            onTextFieldChanged = { booksViewModel.updateSearchQuery(it) },
            onSearchButtonClicked = { onNewSearch() },
        )
        Spacer(modifier = Modifier.size(16.dp))
        Box(modifier = Modifier.weight(1f)) {
            when (val uiState = resultScreenUiState) {
                is ResultScreenUiState.Success -> ResultsGridScreen(
                    bookResponse = uiState.response,
                    updateBookId = { booksViewModel.updateBookId(it) },
                    onImageClicked = onImageClicked,
                    modifier = Modifier
                )

                is ResultScreenUiState.Loading -> LoadingScreen()
                /*** Error Screen ***/
                is ResultScreenUiState.Error -> ErrorScreen(
                    retryAction = retryAction,
                    errorMessage = uiState.errorMessage
                )

                is ResultScreenUiState.Empty -> EmptyScreen()
            }

        }
        if (resultScreenUiState is ResultScreenUiState.Success) {
            PaginationControls(
                canNavigateNext = booksViewModel.canNavigateNext,
                canNavigatePrevious = booksViewModel.canNavigatePrevious,
                onNextClicked = { booksViewModel.nextPage() },
                onPreviousClicked = { booksViewModel.previousPage() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PaginationControls(
    canNavigateNext: Boolean,
    canNavigatePrevious: Boolean,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        TextButton(
            onClick = onPreviousClicked,
            enabled = canNavigatePrevious,
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back_button)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Previous")
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onNextClicked,
            enabled = canNavigateNext,
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(text = "Next")
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(R.string.next_button)
            )
        }
    }
}

@Composable
fun ResultsGridScreen(
    bookResponse: BookResponse,
    updateBookId: (String) -> Unit,
    onImageClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        bookResponse.items?.let {
            items(it.size) { item ->
                BookPhotoCard(
                    bookId = it[item].id,
                    bookInfo = it[item].volumeInfo,
                    updateBookId = updateBookId,
                    onImageClicked = onImageClicked,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun BookPhotoCard(
    bookInfo: VolumeInfo,
    updateBookId: (String) -> Unit,
    onImageClicked: () -> Unit,
    bookId: String,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val thumbnailUrl: String = bookInfo.imageLinks.thumbnail.replace("http", "https")
        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = modifier
                .padding(8.dp)
                .aspectRatio(0.5625f)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = bookInfo.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .padding(2.dp)
                    .clickable {
                        updateBookId(bookId)
                        onImageClicked()
                    }
            )
        }
        Text(
            text = bookInfo.title,
            fontFamily = baskervilleFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 0.dp)
                .clickable { onImageClicked() },
        )
        Spacer(modifier = Modifier.size(16.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun ResultsGridScreenPreview() {
    TheBookOfBooksTheme {
        val mockData = BookResponse(
            listOf(
                Item(
                    id = "horny_hermoine",
                    selfLink = "",
                    volumeInfo = VolumeInfo(
                        authors = listOf("Johnny Storm", "Jane Austen", "Ron Weasly"),
                        averageRating = 5.0,
                        categories = listOf("Fiction", "Romance"),
                        contentVersion = "",
                        description = "Based on the sexual experiences and kinks of Hermoine in the bed.",
                        imageLinks = ImageLinks(
                            smallThumbnail = "",
                            thumbnail = ""
                        ),
                        infoLink = "https://www.tushy.com",
                        language = "French",
                        pageCount = 69,
                        previewLink = "",
                        printType = "",
                        publishedDate = "",
                        publisher = "",
                        subtitle = "",
                        title = "The Love For Hermoine Granger"
                    )
                ),
                Item(
                    id = "lusty_leopard",
                    selfLink = "",
                    volumeInfo = VolumeInfo(
                        authors = listOf("Sack Jarrow", "Ron Granger"),
                        averageRating = 5.0,
                        categories = listOf("Fiction", "Romance"),
                        contentVersion = "",
                        description = "Based on the sexual experiences and kinks of Hermoine in the bed.",
                        imageLinks = ImageLinks(
                            smallThumbnail = "",
                            thumbnail = ""
                        ),
                        infoLink = "https://www.natgeowild.com",
                        language = "Dutch",
                        pageCount = 69,
                        previewLink = "",
                        printType = "",
                        publishedDate = "",
                        publisher = "",
                        subtitle = "",
                        title = "The Adventures of the Leopard"
                    )
                ),
                Item(
                    id = "2",
                    selfLink = "",
                    volumeInfo = VolumeInfo(
                        authors = listOf("Johnny Storm", "Jane Austen", "Ron Weasly"),
                        averageRating = 5.0,
                        categories = listOf("Fiction", "Romance"),
                        contentVersion = "",
                        description = "Based on the sexual experiences and kinks of Hermoine in the bed.",
                        imageLinks = ImageLinks(
                            smallThumbnail = "",
                            thumbnail = ""
                        ),
                        infoLink = "https://www.tushy.com",
                        language = "French",
                        pageCount = 69,
                        previewLink = "",
                        printType = "",
                        publishedDate = "",
                        publisher = "",
                        subtitle = "",
                        title = "The Love For Hermoine Granger"
                    )
                ),
                Item(
                    id = "1",
                    selfLink = "",
                    volumeInfo = VolumeInfo(
                        authors = listOf("Johnny Storm", "Jane Austen", "Ron Weasly"),
                        averageRating = 5.0,
                        categories = listOf("Fiction", "Romance"),
                        contentVersion = "",
                        description = "Based on the sexual experiences and kinks of Hermoine in the bed.",
                        imageLinks = ImageLinks(
                            smallThumbnail = "",
                            thumbnail = ""
                        ),
                        infoLink = "https://www.tushy.com",
                        language = "French",
                        pageCount = 69,
                        previewLink = "",
                        printType = "",
                        publishedDate = "",
                        publisher = "",
                        subtitle = "",
                        title = "The Love For Hermoine Granger"
                    )
                )
            ), 4
        )
        ResultsGridScreen(
            bookResponse = mockData,
            onImageClicked = {},
            updateBookId = {},
            modifier = Modifier,
        )

    }
}