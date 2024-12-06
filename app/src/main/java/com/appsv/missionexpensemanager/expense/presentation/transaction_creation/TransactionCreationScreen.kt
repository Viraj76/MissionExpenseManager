package com.appsv.missionexpensemanager.expense.presentation.transaction_creation


import AmountTextField
import android.util.Log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsv.missionexpensemanager.R
import com.appsv.missionexpensemanager.core.presentation.ui.theme.ColorPrimary
import com.appsv.missionexpensemanager.core.presentation.ui.theme.ColorSecondaryVariant
import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components.CustomTextField
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components.DatePickerModal
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components.ExpenseIncomeToggle
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components.RequiredText
import com.appsv.missionexpensemanager.expense.utils.formatDate
import com.appsv.missionexpensemanager.expense.utils.isNumberOrDouble
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionCreationScreen(
    selectedTransaction : Transaction = Transaction(),
    events: (TransactionCreationEvents) -> Unit
) {

    val isEditMode by remember { mutableStateOf(selectedTransaction != Transaction()) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Log.d("Amount", selectedTransaction.amount)
    }
    var showCalendarDialog by remember { mutableStateOf(false) }
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
        date != initialDate ||
                description != initialDescription ||
                enteredTotalAmount != initialEnteredTotalAmount ||
                selectedOption != initialSelectedOption
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Record Expense",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                }
            )
        },
        containerColor = ColorSecondaryVariant,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                ExpenseIncomeToggle(selectedOption) {
                    selectedOption = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),

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
                        .background(Color.White),

                    ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "DESCRIPTION",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF333366),
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
                        .background(Color.White),
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
                        .background(Color.White),
                ) {
                    Button(
                        onClick = {

                            val transaction = Transaction(
                                id = UUID.randomUUID().toString(),
                                transactionType = selectedOption,
                                description = description,
                                date = date,
                                amount = enteredTotalAmount
                            )

                            scope.launch(Dispatchers.IO){
                                events(TransactionCreationEvents.SaveTransaction(transaction))
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
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewRecordExpenseScreen() {
//    TransactionCreationScreen()
//}


