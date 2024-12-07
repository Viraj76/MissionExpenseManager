package com.appsv.missionexpensemanager.expense.presentation.transaction_details

import android.R.attr.label
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsv.missionexpensemanager.R
import com.appsv.missionexpensemanager.core.component.YesNoAlertDialog
import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.appsv.missionexpensemanager.core.component.NoInternetDialog
import com.appsv.missionexpensemanager.core.presentation.ui.theme.DarkGrayishPurple
import com.appsv.missionexpensemanager.core.presentation.ui.theme.getColorsForTheme
import com.appsv.missionexpensemanager.core.util.NetworkConnectionState
import com.appsv.missionexpensemanager.core.util.rememberConnectivityState
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
    var showNoInternetDialogForEdit by rememberSaveable { mutableStateOf(false) }
    var showNoInternetDialogForDelete by rememberSaveable { mutableStateOf(false) }
    val connectionState by rememberConnectivityState()

    val isConnected by remember(connectionState) {
        derivedStateOf {
            connectionState === NetworkConnectionState.Available
        }
    }

    val getColors = getColorsForTheme()
    val isDarkMode = isSystemInDarkTheme()

    var isYesNoDialogOpen by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    BackHandler {
        goToTransactionDashBoard()
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(if(!isDarkMode) Color.White else Color(0xFF303030)),
                title = { Text(text = "Expense", color = getColors.DarkGrayishPurple, fontWeight = FontWeight.Medium) },
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
                        if(isConnected) onEditIconClicked(selectedTransaction) else showNoInternetDialogForEdit = true
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "Edit",
                            tint = Color(0xFF6200EA)
                        )
                    }
                    IconButton(onClick = {
                        if(isConnected) isYesNoDialogOpen = true else showNoInternetDialogForDelete = true
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.delete),
                            contentDescription = "Delete",
                            tint = Color(0xFF6200EA)
                        )
                    }
                },
            )
        },
        containerColor = getColors.ColorSecondaryVariant
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if(!isDarkMode) Color.White else DarkGrayishPurple),

                ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "${selectedTransaction.transactionType} #${selectedTransaction.transactionNumber}",
                        color = getColors.DarkGrayishPurple,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = selectedTransaction.date,
                        color = getColors.DarkGrayishPurple,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if(!isDarkMode) Color.White else DarkGrayishPurple),

                ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Description",
                        color = getColors.DarkGrayishPurple,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if(selectedTransaction.description.isEmpty()) "NA" else selectedTransaction.description,
                        color = getColors.DarkGrayishPurple,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if(!isDarkMode) Color.White else DarkGrayishPurple)
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
                        color = getColors.DarkGrayishPurple
                    )
                    Text(
                        text = "₹ ${selectedTransaction.amount}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = getColors.DarkGrayishPurple
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
    if(showNoInternetDialogForEdit){
        NoInternetDialog(
            message = "To edit a transaction, please turn on the internet."
        ){
            showNoInternetDialogForEdit = false
        }
    }
    if(showNoInternetDialogForDelete){
        NoInternetDialog(
            message = "To edit a transaction, please turn on the internet."
        ){
            showNoInternetDialogForDelete = false
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    isBold: Boolean = false,
    isDarkMode : Boolean, ) {


}



