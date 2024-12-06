package com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun RequiredText(
    text : String
) {
    Row {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF333366),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "*",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Red,
            textAlign = TextAlign.Start
        )
    }
}

