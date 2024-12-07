package com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.appsv.missionexpensemanager.core.presentation.ui.theme.ColorPrimary

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Settings",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = ColorPrimary
        )
    }
}
