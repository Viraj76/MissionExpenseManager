package com.appsv.missionexpensemanager.expense.domain.models

data class Transaction(
    val transactionType: String,
    val transactionNumber: Int,
    val description : String,
    val date: String,
    val amount: Double
)