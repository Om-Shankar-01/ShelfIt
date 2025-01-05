package com.example.thebookofbooks.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thebookofbooks.model.ImageLinks
import com.example.thebookofbooks.model.Item
import com.example.thebookofbooks.model.VolumeInfo
import com.example.thebookofbooks.ui.DetailsScreenUiState
import com.example.thebookofbooks.ui.theme.TheBookOfBooksTheme
import com.example.thebookofbooks.ui.theme.baskervilleFamily
import com.example.thebookofbooks.ui.theme.oswaldFamily

@Composable
fun BookDetailsLayout(
    bookDetails: Item
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = bookDetails.volumeInfo.title,
                fontSize = 36.sp,
                lineHeight = 40.sp,
                fontFamily = oswaldFamily
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = bookDetails.volumeInfo.description,
                fontSize = 16.sp,
                fontFamily = baskervilleFamily
            )
        }
    }

}

@Composable
fun BookDetailsScreen(
    detailsScreenUiState: DetailsScreenUiState
) {
    when (detailsScreenUiState) {
        is DetailsScreenUiState.Loading -> LoadingScreen()
        is DetailsScreenUiState.Error -> ErrorScreen({}, detailsScreenUiState.errorMessage)
        is DetailsScreenUiState.Success -> BookDetailsLayout(
            bookDetails = detailsScreenUiState.bookDetails
        )
    }

}

@Preview(showBackground = true)
@Composable
fun BookDetailsLayoutPreview() {
    TheBookOfBooksTheme {
        BookDetailsLayout(
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
        )
    }
}