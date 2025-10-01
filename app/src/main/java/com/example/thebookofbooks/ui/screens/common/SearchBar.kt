package com.example.thebookofbooks.ui.screens.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.thebookofbooks.R
import com.example.thebookofbooks.ui.theme.baskervilleFamily

@Composable
fun SearchBar (
    query : String,
    onSearchButtonClicked: () -> Unit,
    onTextFieldChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onTextFieldChanged,
        label = {
            Text(
                text = stringResource(R.string.search_field_label),
                fontFamily = baskervilleFamily,
                fontWeight = FontWeight.Bold
            )
        },
        shape = RoundedCornerShape(percent = 33),
        singleLine = true,
        leadingIcon = {
            FilledIconButton(
                onClick = onSearchButtonClicked,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
            ) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onTextFieldChanged("") }
                ) {
                    Icon(Icons.Default.Clear, contentDescription = null)
                }
            }
        },
        keyboardActions = KeyboardActions(
            onDone = { onSearchButtonClicked() }
        ),
        textStyle = TextStyle(
            fontFamily = baskervilleFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}
