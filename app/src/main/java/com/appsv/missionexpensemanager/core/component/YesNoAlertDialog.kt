package com.appsv.missionexpensemanager.core.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@Composable
fun YesNoAlertDialog(
    message: String,
    onYes: () -> Unit,
    onNo: () -> Unit,
    icon: Int = 0
) {
        AlertDialog(
            onDismissRequest = {
                onNo()
            },
            title = {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Dialog Icon",
                    tint = Color.Red
                )
            },
            text = {
                Text(text = message, fontSize = 22.sp)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onYes()
                    }
                ) {
                    Text("Yes",fontSize = 18.sp)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onNo()
                    }
                ) {
                    Text("No", fontSize = 18.sp)
                }
            }
        )

}
