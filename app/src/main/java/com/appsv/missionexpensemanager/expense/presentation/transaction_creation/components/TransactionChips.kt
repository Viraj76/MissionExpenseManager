package com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomFilterChip(
    label: String,
    color: Color,
    selected: Boolean,
    alphaValue: Float = 0.8f,
    onClick: () -> Unit,
) {
    FilterChip(
        onClick = onClick,
        label = {
            Text(
                label,
                color = if (selected) Color.White else Color.White.copy(alpha = 0.7f)
            )
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = "Done icon",
                    tint = Color.White,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        colors = FilterChipDefaults.filterChipColors().copy(
            containerColor = color.copy(alpha = if (selected) 1f else alphaValue),
            labelColor = if (selected) Color.White else color,
            leadingIconColor = Color.White,
            selectedContainerColor = color
        ),
        border = null
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomFilterChip() {
    var selectedChip by remember { mutableStateOf("All") }
    val chipOptions = listOf("All", "Expense", "Income")
    val chipColors = listOf(Color.Blue, Color.Red, Color.Green)

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        chipOptions.forEachIndexed { index, label ->
            CustomFilterChip(
                label = label,
                color = chipColors[index],
                selected = selectedChip == label,
                onClick = { selectedChip = label }
            )
        }
    }
}
