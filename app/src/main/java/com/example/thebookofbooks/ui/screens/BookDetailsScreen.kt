package com.example.thebookofbooks.ui.screens

import android.app.Activity
import android.icu.text.IDNA
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.thebookofbooks.R
import com.example.thebookofbooks.model.BookDetailsItem
import com.example.thebookofbooks.model.Dimensions
import com.example.thebookofbooks.model.ImageLinksX
import com.example.thebookofbooks.model.ReadingModes
import com.example.thebookofbooks.model.SaleInfo
import com.example.thebookofbooks.model.VolumeInfoX
import com.example.thebookofbooks.ui.DetailsScreenUiState
import com.example.thebookofbooks.ui.theme.TheBookOfBooksTheme
import com.example.thebookofbooks.ui.theme.baskervilleFamily
import com.example.thebookofbooks.ui.theme.prataFamily
import java.util.Locale

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun BookDetailsLayout(
    bookDetails: BookDetailsItem
) {
    val activity = LocalContext.current as Activity
    val windowSizeClass = calculateWindowSizeClass(activity)

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            BookDetailsTabletLayout(bookDetails)
        }

        else -> {
            BookDetailsPortraitLayout(bookDetails)
        }
    }

}

@Composable
fun BookDetailsTabletLayout(bookDetails: BookDetailsItem) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.4f) // Takes 40% of the width
                .verticalScroll(rememberScrollState()), // Allows scrolling if content is too tall
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BookCover(bookDetails)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PublishedDateCard(bookDetails, modifier = Modifier.weight(1f))
                InfoPill(text = "${bookDetails.volumeInfo.printedPageCount} pages")
            }
        }

        LazyColumn(
            modifier = Modifier.weight(0.6f), // Takes 60% of the width
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item { BookTitle(bookDetails) }
            item { DescriptionCard(bookDetails) }
            item { BookAuthorList(bookDetails) }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    CategoriesCard(bookDetails, modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f))
                    Column (verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(0.6f)) {
                        InfoPill(
                            bookDetails.volumeInfo.maturityRating,
                            modifier = Modifier.fillMaxWidth()
                        )
                        InfoPillAlt(bookDetails.volumeInfo.language, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }

}

@Composable
fun BookDetailsPortraitLayout(bookDetails: BookDetailsItem) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item { BookCover(bookDetails) }
        item { BookTitle(bookDetails) }
        item { DescriptionCard(bookDetails) }
        item { BookAuthorList(bookDetails) }
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp), // Spacing is handled by Arrangement here
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                InfoPill(
                    text = "${bookDetails.volumeInfo.printedPageCount} pages",
                    modifier = Modifier.fillMaxHeight()
                )
                PublishedDateCard(bookDetails, modifier = Modifier.weight(1f))
            }
        }
        item { CategoriesCard(bookDetails, modifier = Modifier.fillMaxWidth()) }
        item {
            Row (horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoPillAlt(bookDetails.volumeInfo.language, modifier = Modifier.weight(1f))
                InfoPill(
                    bookDetails.volumeInfo.maturityRating,
                )
            }
        }
    }


}

@Composable
fun BookTitle(bookDetails: BookDetailsItem) {
    Text(
        text = bookDetails.volumeInfo.title,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = prataFamily,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}

@Composable
fun BookCover(bookDetails: BookDetailsItem) {
    val imageUrl: String? =
        if (bookDetails.volumeInfo.imageLinks.large != null) bookDetails.volumeInfo.imageLinks.large
        else if (bookDetails.volumeInfo.imageLinks.medium != null) bookDetails.volumeInfo.imageLinks.medium
        else if (bookDetails.volumeInfo.imageLinks.small != null) bookDetails.volumeInfo.imageLinks.small
        else bookDetails.volumeInfo.imageLinks.thumbnail

    val replacedImageUrl = imageUrl?.replace("http", "https")

    OutlinedCard(
        shape = CardDefaults.outlinedShape,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(replacedImageUrl)
                .crossfade(enable = true)
                .build(),
            contentDescription = bookDetails.volumeInfo.title,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
//                .padding(2.dp)
        )
    }
}

@Composable
fun CategoriesCard(bookDetails: BookDetailsItem, modifier: Modifier = Modifier) {
    Card(
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        modifier = modifier,
    ) {
            Text(
                text = "Categories",
                fontSize = 18.sp,
                fontFamily = baskervilleFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            val listOfCategories: List<String>? = bookDetails.volumeInfo.categories
            if (listOfCategories != null) {
                for (category in listOfCategories) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = category,
                        fontSize = 14.sp,
                        fontFamily = baskervilleFamily,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                    )
                }
            } else {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    "No Categories Found",
                    fontSize = 14.sp,
                    fontFamily = baskervilleFamily,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                )
            }
    }
}


@Composable
fun DescriptionCard(bookDetails: BookDetailsItem) {
    Card(
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = "Description",
                fontSize = 18.sp,
                fontFamily = baskervilleFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 4.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = bookDetails.volumeInfo.description,
                fontSize = 14.sp,
                fontFamily = baskervilleFamily,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun BookAuthorList(bookDetails: BookDetailsItem) {
    Card(
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = "Authors",
                fontSize = 18.sp,
                fontFamily = baskervilleFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            for (author in bookDetails.volumeInfo.authors) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = author,
                    fontSize = 14.sp,
                    fontFamily = baskervilleFamily,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun PublishedDateCard(bookDetails: BookDetailsItem, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        modifier = modifier
            .fillMaxHeight()
    ) {
        Text(
            text = stringResource(R.string.published_date_text),
            fontFamily = baskervilleFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            bookDetails.volumeInfo.publishedDate,
            fontFamily = baskervilleFamily,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun InfoPill(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(color = MaterialTheme.colorScheme.tertiary)
    ) {
        Text(
            text = text.trim().lowercase().replace('_', ' '),
            fontSize = 20.sp,
            fontFamily = prataFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun InfoPillAlt(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(color = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontFamily = prataFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.padding(16.dp)
        )
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

val sampleBookItem = BookDetailsItem(
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
        pageCount = 6978,
        previewLink = "",
        printType = "",
        publishedDate = "1999-08-05",
        publisher = "",
        title = "The Lovey For Hermoine Granger",
        allowAnonLogging = true,
        canonicalVolumeLink = "",
        dimensions = Dimensions("69", "69", "69"),
        maturityRating = "Adult",
        printedPageCount = 56,
        readingModes = ReadingModes(text = true, image = true)
    ),
    kind = "Physical Book"
)

@Preview(showBackground = true, heightDp = 1200)
@Composable
fun BookDetailsPortraitLayoutPreview() {
    TheBookOfBooksTheme {
        BookDetailsPortraitLayout(sampleBookItem)
    }
}


@Preview(showBackground = true, widthDp = 800, heightDp = 600)
@Composable
fun BookDetailsTabletLayoutPreview() {
    TheBookOfBooksTheme {
        BookDetailsTabletLayout(sampleBookItem)
    }
}
