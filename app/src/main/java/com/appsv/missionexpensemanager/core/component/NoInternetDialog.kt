package com.appsv.missionexpensemanager.core.component


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.appsv.missionexpensemanager.R

@Composable
fun NoInternetDialog(
    message : String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = null,
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                val icon: Painter = painterResource(id = R.drawable.baseline_wifi_off_24)
                Icon(painter = icon, contentDescription = "No Internet", modifier = Modifier.size(80.dp), tint = Color.Red)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No Internet!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("OK",fontSize = 15.sp,)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel",fontSize = 15.sp,)
            }
        }
    )
}

