package com.appsv.missionexpensemanager.expense.domain.models

data class Transaction(
    val id : String  = "",
    val transactionType: String = "Expense",
    val transactionNumber: Int = 0,
    val description : String = "",
    val date: String = "",
    val amount: String = "0.00"
)