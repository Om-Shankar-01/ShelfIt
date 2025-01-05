package com.example.thebookofbooks.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thebookofbooks.R
import com.example.thebookofbooks.ui.theme.TheBookOfBooksTheme


@Composable
fun ErrorScreen (
    retryAction: () -> Unit = {},
    errorMessage : String?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
    ) {
        item {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(id = R.string.warning_icon_desc),
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.error_screen_heading_text),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.displaySmall,
                // textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.error_screen_subheading_text),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
                ),
                // textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
                    ),
                    // textAlign = TextAlign.Justify
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = retryAction, contentPadding = PaddingValues(4.dp)) {
                Text(
                    text = stringResource(id = R.string.error_screen_retry_button),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ErrorScreenPreview () {
    TheBookOfBooksTheme {
        ErrorScreen(errorMessage = "Error 404")
    }
}