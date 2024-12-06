package com.appsv.missionexpensemanager.expense.presentation.transaction_details

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsv.missionexpensemanager.R
import com.appsv.missionexpensemanager.core.component.YesNoAlertDialog
import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.TransactionCreationEvents
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailsScreen(
    selectedTransaction : Transaction = Transaction(),
    onEditIconClicked : (Transaction) -> Unit = {},
    events: (TransactionCreationEvents) -> Unit,
    goToTransactionDashBoard : () -> Unit = {}
) {
    var isYesNoDialogOpen by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    BackHandler {
        goToTransactionDashBoard()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Expense", color = Color.Black, fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = {
                        goToTransactionDashBoard()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = "Back",
                            tint = Color(0xFF6200EA)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onEditIconClicked(selectedTransaction)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "Edit",
                            tint = Color(0xFF6200EA)
                        )
                    }
                    IconButton(onClick = {
                        isYesNoDialogOpen = true
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.delete),
                            contentDescription = "Delete",
                            tint = Color(0xFF6200EA)
                        )
                    }
                },
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = Color(0xFFF8F8FF))
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),

                ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "${selectedTransaction.transactionType} #${selectedTransaction.transactionNumber}",
                        color = Color(0xFF6200EA),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = selectedTransaction.date,
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            DetailRow(label = "Description", value = selectedTransaction.description)

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 15.dp),
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Amount",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = "₹ ${selectedTransaction.amount}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                }
            }
        }
    }

    if(isYesNoDialogOpen){
        YesNoAlertDialog(
            message = "Are you sure you want to delete this transaction?",
            onYes = {
                isYesNoDialogOpen = false
                goToTransactionDashBoard()
                scope.launch{
                    events(TransactionCreationEvents.DeleteTransaction(selectedTransaction.id))
                }
            },
            onNo = {
                isYesNoDialogOpen = false
            },
            icon = R.drawable.delete
        )
    }
}

@Composable
fun DetailRow(label: String, value: String, isBold: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),

        ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = label,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
                )
            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if(value.isEmpty()) "NA" else value,
                color = Color.Black,
                style = if (isBold) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium
            )
        }
    }

}
