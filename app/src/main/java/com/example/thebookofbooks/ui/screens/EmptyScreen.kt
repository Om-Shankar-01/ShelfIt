package com.example.thebookofbooks.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thebookofbooks.ui.theme.TheBookOfBooksTheme
import com.example.thebookofbooks.ui.theme.baskervilleFamily
import com.example.thebookofbooks.ui.theme.prataFamily

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EmptyScreen(
    retryAction: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No results found",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            fontFamily = prataFamily
        )
        Button(onClick = retryAction) {
            Text(
                text = "Retry",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = baskervilleFamily,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview (showBackground = true, heightDp = 600)
@Composable
fun EmptyScreenPreview() {
    TheBookOfBooksTheme { EmptyScreen(retryAction = {}) }
}