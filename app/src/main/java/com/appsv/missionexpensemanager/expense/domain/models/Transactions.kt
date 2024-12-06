package com.appsv.missionexpensemanager.expense.domain.models

data class Transaction(
    val transactionType: String = "Expense",
    val transactionNumber: Int = 0,
    val description : String = "",
    val date: String = "",
    val amount: Double = 0.00
)