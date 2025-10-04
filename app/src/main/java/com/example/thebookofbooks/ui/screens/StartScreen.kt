package com.example.thebookofbooks.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thebookofbooks.R
import com.example.thebookofbooks.data.BooksViewModel
import com.example.thebookofbooks.ui.screens.common.SearchBar
import com.example.thebookofbooks.ui.theme.baskervilleFamily
import com.example.thebookofbooks.ui.theme.oswaldFamily
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BooksStartScreen(
    booksViewModel: BooksViewModel,
    onSearchButtonClicked: () -> Unit = {}
) {
    val query = booksViewModel.searchQuery
    val onTextFieldChanged = { newQuery: String -> booksViewModel.updateSearchQuery(newQuery) }

    // Dropdown menu options
    val orderByOptions = listOf<String>("relevance", "newest")
    val printTypeOptions = listOf<String>("all", "books", "magazines")

    var orderByTypeExpanded by remember { mutableStateOf(false) }
    var printTypeExpanded by remember { mutableStateOf(false) }

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
            delay(200)
            for (i in 1..titleText.length) {
                displayTitleText = titleText.substring(0, i)
                delay(150)
            }
        }
        Text(
            text = displayTitleText,
            fontSize = 60.sp,
            fontFamily = oswaldFamily,
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.size(32.dp))
        /*** Search Bar ***/
        SearchBar(
            query = query,
            onSearchButtonClicked = onSearchButtonClicked,
            onTextFieldChanged = onTextFieldChanged
        )
        Spacer(modifier = Modifier.size(16.dp))

        /*** Row for Filter Options ***/
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Options for Order By Type
            ExposedDropdownMenuBox(
                expanded = orderByTypeExpanded,
                onExpandedChange = { orderByTypeExpanded = !orderByTypeExpanded },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = booksViewModel.orderBy ?: "relevance",
                    onValueChange = { },
                    shape = RoundedCornerShape(percent = 33),
                    readOnly = true,
                    label = { Text("Order By", fontFamily = oswaldFamily) },
                    leadingIcon = {
                        Icon(
                            Icons.AutoMirrored.Filled.Sort,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = orderByTypeExpanded)
                    },
                    textStyle = TextStyle(
                        fontFamily = baskervilleFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )
                ExposedDropdownMenu(
                    expanded = orderByTypeExpanded,
                    onDismissRequest = { orderByTypeExpanded = false },
                ) {
                    for (i in 0 until orderByOptions.size) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = orderByOptions[i],
                                    fontFamily = baskervilleFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            onClick = {
                                booksViewModel.orderBy = orderByOptions[i]
                                orderByTypeExpanded = false
                            }
                        )
                        if (i < orderByOptions.size - 1) HorizontalDivider(
                            modifier = Modifier.padding(
                                horizontal = 8.dp
                            )
                        )
                    }
                }
            }

            // Options for Print type
            ExposedDropdownMenuBox(
                expanded = printTypeExpanded,
                onExpandedChange = { printTypeExpanded = !printTypeExpanded },
                modifier = Modifier.weight(1f),
            ) {
                OutlinedTextField(
                    value = booksViewModel.printType ?: "all",
                    onValueChange = { },
                    shape = RoundedCornerShape(percent = 33),
                    readOnly = true,
                    label = { Text("Print Type", fontFamily = oswaldFamily) },
                    leadingIcon = {
                        Icon(
                            Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = printTypeExpanded)
                    },
                    textStyle = TextStyle(
                        fontFamily = baskervilleFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )
                ExposedDropdownMenu(
                    expanded = printTypeExpanded,
                    onDismissRequest = { printTypeExpanded = false }
                ) {
                    for (i in 0 until printTypeOptions.size) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    printTypeOptions[i],
                                    fontFamily = baskervilleFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            onClick = {
                                booksViewModel.printType = printTypeOptions[i]
                                printTypeExpanded = false
                            }
                        )
                        if (i < printTypeOptions.size - 1) HorizontalDivider(
                            modifier = Modifier.padding(
                                horizontal = 8.dp
                            )
                        )
                    }
                }
            }
        }
    }
}


