package com.example.thebookofbooks.ui.screens

import android.media.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.thebookofbooks.R
import com.example.thebookofbooks.model.BookDetailsItem
import com.example.thebookofbooks.model.Dimensions
import com.example.thebookofbooks.model.ImageLinks
import com.example.thebookofbooks.model.ImageLinksX
import com.example.thebookofbooks.model.Item
import com.example.thebookofbooks.model.ReadingModes
import com.example.thebookofbooks.model.SaleInfo
import com.example.thebookofbooks.model.VolumeInfo
import com.example.thebookofbooks.model.VolumeInfoX
import com.example.thebookofbooks.ui.DetailsScreenUiState
import com.example.thebookofbooks.ui.theme.TheBookOfBooksTheme
import com.example.thebookofbooks.ui.theme.baskervilleFamily
import com.example.thebookofbooks.ui.theme.oswaldFamily

@Composable
fun BookDetailsLayout(
    bookDetails: BookDetailsItem
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            val imageUrl: String? = bookDetails.volumeInfo.imageLinks.large?.replace("http", "https")
            OutlinedCard(
                shape = CardDefaults.outlinedShape,
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(enable = true)
                        .build(),
                    contentDescription = bookDetails.volumeInfo.title,
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .padding(2.dp)
                )
            }
            Text(
                text = bookDetails.volumeInfo.title,
                fontSize = 36.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = oswaldFamily,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))

            ElevatedCard (
                shape = CardDefaults.outlinedShape,
            ) {
                Column(
                    modifier = Modifier.padding(4.dp)
                ){
                    Text(
                        text = "Description",
                        fontSize = 18.sp,
                        fontFamily = baskervilleFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = bookDetails.volumeInfo.description,
                        fontSize = 16.sp,
                        fontFamily = baskervilleFamily,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(Modifier.size(16.dp))
            ElevatedCard(
                shape = CardDefaults.outlinedShape,
            ) {
                Column(
                    modifier = Modifier.padding(4.dp)
                ){
                    Text(
                        text = "Authors",
                        fontSize = 18.sp,
                        fontFamily = baskervilleFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                    for(author in bookDetails.volumeInfo.authors) {
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = author,
                            fontSize = 16.sp,
                            fontFamily = baskervilleFamily,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }
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
            BookDetailsItem(
                id = "1",
                selfLink = "",
                etag = "",
                saleInfo = SaleInfo(
                    country = "India", isEbook = true,
                    saleability = "Yes",
                ),
                volumeInfo = VolumeInfoX(
                    authors = listOf("Johnny Storm", "Jane Austen", "Ron Weasly"),
                    categories = listOf("Fiction", "Romance"),
                    contentVersion = "",
                    description = "Based on the sexual experiences and kinks of Hermoine in the bed.",
                    imageLinks = ImageLinksX(
                        smallThumbnail = "",
                        thumbnail = "",
                        extraLarge = "",
                        large = "",
                        medium = "",
                        small = ""
                    ),
                    infoLink = "https://www.tushy.com",
                    language = "French",
                    pageCount = 69,
                    previewLink = "",
                    printType = "",
                    publishedDate = "",
                    publisher = "",
                    title = "The Love For Hermoine Granger",
                    allowAnonLogging = true,
                    canonicalVolumeLink = "",
                    dimensions = Dimensions("69","69","69"),
                    maturityRating = "Adult",
                    printedPageCount = 56,
                    readingModes = ReadingModes(text = true, image = true)
                ),
                kind = "Physical Book"
            )
        )
    }
}