package com.appsv.missionexpensemanager.expense.presentation.transaction_creation


import AmountTextField
import androidx.activity.compose.BackHandler

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsv.missionexpensemanager.R
import com.appsv.missionexpensemanager.core.presentation.ui.theme.ColorPrimary
import com.appsv.missionexpensemanager.core.presentation.ui.theme.DarkGrayishPurple
import com.appsv.missionexpensemanager.core.presentation.ui.theme.getColorsForTheme
import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components.CustomTextField
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components.DatePickerModal
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components.ExpenseIncomeToggle
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components.RequiredText
import com.appsv.missionexpensemanager.expense.utils.formatDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionCreationScreen(
    selectedTransaction: Transaction = Transaction(),
    events: (TransactionCreationEvents) -> Unit,
    goToTransactionDashBoard: () -> Unit,
) {

    BackHandler {
        goToTransactionDashBoard()
    }

    val isEditMode by remember { mutableStateOf(selectedTransaction != Transaction()) }

    val scope = rememberCoroutineScope()

    val getColors = getColorsForTheme()
    val isDarkMode = isSystemInDarkTheme()

    var showCalendarDialog by rememberSaveable { mutableStateOf(false) }
    var isEnteringAmount by remember { mutableStateOf(false) }

    val initialDate = if(isEditMode) selectedTransaction.date else formatDate(System.currentTimeMillis())
    val initialDescription = if(isEditMode) selectedTransaction.description else ""
    val initialEnteredTotalAmount = if (isEditMode) selectedTransaction.amount else "0.00"
    val initialSelectedOption = if(isEditMode) selectedTransaction.transactionType else "Expense"

    var date by remember { mutableStateOf(initialDate) }
    var description by remember { mutableStateOf(initialDescription) }
    var enteredTotalAmount by remember { mutableStateOf(initialEnteredTotalAmount) }
    var selectedOption by remember { mutableStateOf(initialSelectedOption) }

    val isButtonEnabled = remember(date, description, enteredTotalAmount, selectedOption) {
        (date != initialDate && enteredTotalAmount != "0.00") ||
                (description != initialDescription && enteredTotalAmount != "0.00") ||
                (enteredTotalAmount != initialEnteredTotalAmount && enteredTotalAmount != "0.00") ||
                (selectedOption != initialSelectedOption && enteredTotalAmount != "0.00")
    }

    Scaffold(
        modifier = Modifier.background(if(!isDarkMode) Color.White else Color(0xFF303030)),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Record Expense",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = getColors.DarkGrayishPurple
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            goToTransactionDashBoard()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = getColors.DarkGrayishPurple
                        )
                    }
                }
            )
        },
        containerColor = getColors.ColorSecondaryVariant,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                ExpenseIncomeToggle(selectedOption) {
                    selectedOption = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if(!isDarkMode) Color.White else DarkGrayishPurple),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        RequiredText("DATE")
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextField(
                            value = date,
                            readOnly = true,
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        showCalendarDialog = true
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_calendar_today_24),
                                        contentDescription = "Calendar Icon",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        )
                    }
                }
                if (showCalendarDialog) {
                    DatePickerModal(
                        onDateSelected = { selectedDate ->
                            date = if (selectedDate != null) {
                                formatDate(selectedDate)
                            } else {
                                formatDate(System.currentTimeMillis())
                            }
                        },
                        onDismiss = {
                            showCalendarDialog = false
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if(!isDarkMode) Color.White else DarkGrayishPurple),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "DESCRIPTION",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = getColors.DarkGrayishPurple,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextField(
                            value = description,
                            onValueChange = {
                                description = it
                            },
                            label = {
                                Text(text = "Enter description")
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if(!isDarkMode) Color.White else DarkGrayishPurple),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RequiredText(
                            text = "Total Amount"
                        )
                        AmountTextField(
                            amount =  enteredTotalAmount,
                            isEnteringAmount = isEnteringAmount,
                            onDone = { totalAmount ->
                                isEnteringAmount = false
                                enteredTotalAmount = if (totalAmount.isNotEmpty()) totalAmount else initialEnteredTotalAmount
                            },
                            onAmountClick = {
                                isEnteringAmount = true
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if(!isDarkMode) Color.White else DarkGrayishPurple),
                ) {
                    Button(
                        onClick = {
                            goToTransactionDashBoard()
                            scope.launch{
                                saveOrUpdateTransaction(
                                    isEditMode = isEditMode,
                                    selectedTransaction = selectedTransaction,
                                    selectedOption = selectedOption,
                                    description = description,
                                    date = date,
                                    enteredTotalAmount = enteredTotalAmount,
                                    scope = scope,
                                    events = events
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = ColorPrimary
                        ),
                        enabled = isButtonEnabled
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = "Save",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEBEFF5)
                        )
                    }
                }
            }
        }
    )
}

fun saveOrUpdateTransaction(
    isEditMode: Boolean,
    selectedTransaction: Transaction,
    selectedOption: String,
    description: String,
    date: String,
    enteredTotalAmount: String,
    scope: CoroutineScope,
    events: (TransactionCreationEvents) -> Unit
) {
    if (isEditMode) {
        val transaction =  Transaction(
            id = selectedTransaction.id ,
            transactionType = selectedOption,
            transactionNumber = selectedTransaction.transactionNumber,
            description = description,
            date = date,
            amount = enteredTotalAmount
        )
        scope.launch(Dispatchers.IO) {
            events(TransactionCreationEvents.SaveOrUpdateTransaction(transaction,true))
        }
    } else {

        val transaction =  Transaction(
            id = UUID.randomUUID().toString(),
            transactionType = selectedOption,
            description = description,
            date = date,
            amount = enteredTotalAmount
        )
        scope.launch(Dispatchers.IO) {
            events(TransactionCreationEvents.SaveOrUpdateTransaction(transaction,false))
        }
    }

}


