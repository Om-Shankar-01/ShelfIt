package com.example.thebookofbooks.ui.screens

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thebookofbooks.R
import com.example.thebookofbooks.ui.theme.TheBookOfBooksTheme
import com.example.thebookofbooks.ui.theme.baskervilleFamily
import com.example.thebookofbooks.ui.theme.oswaldFamily
import com.example.thebookofbooks.ui.theme.prataFamily
import com.example.thebookofbooks.ui.theme.sabonFamily
import kotlinx.coroutines.delay

@Composable
fun BooksStartScreen(
    query: String,
    onTextFieldChanged: (String) -> Unit,
    onSearchButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val titleText = stringResource(R.string.app_name)
        var displayTitleText by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            for (i in 1..titleText.length) {
                displayTitleText = titleText.substring(0, i)
                delay(120)
            }
        }
        Text(
            text = displayTitleText,
            fontSize = 48.sp,
            lineHeight = 52.sp,
            fontFamily = oswaldFamily,
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.size(48.dp))
        TextField(
            value = query,
            keyboardActions = KeyboardActions(
                onDone = { onSearchButtonClicked() }
            ),
            textStyle = TextStyle(
                fontFamily = baskervilleFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            onValueChange = onTextFieldChanged,
            label = {
                Text(
                    text = stringResource(R.string.search_field_label),
                    fontFamily = baskervilleFamily,
                    )
            },
            shape = RoundedCornerShape(64.dp),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = onSearchButtonClicked,
            modifier = Modifier
        ) {
            Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search_button_label))
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(R.string.search_button_label),
                fontFamily = baskervilleFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BooksStartScreenPreview() {
    TheBookOfBooksTheme {
        BooksStartScreen(
            query = "Pride and Prejudice",
            onTextFieldChanged = {  }
        )
    }
}

