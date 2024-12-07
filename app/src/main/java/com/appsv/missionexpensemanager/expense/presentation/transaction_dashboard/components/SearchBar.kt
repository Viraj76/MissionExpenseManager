package com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


@Composable
fun SearchBar(
    text : String,
    onExecuteSearch : () -> Unit,
    onSearchingTransactions : (String) -> Unit
) {

    val isDarkMode = isSystemInDarkTheme()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(if(!isDarkMode) Color.White else Color(0xFF303030))
    )
    {
        TextField(
            value = text,
            onValueChange = {
                onSearchingTransactions(it)
                onExecuteSearch()
            },
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 12.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            placeholder = { Text("Search Transactions") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            colors = TextFieldDefaults.colors().copy(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

    }

}