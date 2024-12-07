package com.appsv.missionexpensemanager.core.presentation.nav_graph

import kotlinx.serialization.Serializable




@Serializable
data object MainScreen

@Serializable
data class TransactionCreationScreen(
    val id : String  = "",
    val transactionType: String = "Expense",
    val transactionNumber: Int = 0,
    val description : String = "",
    val date: String = "",
    val amount: String = "0.00"
)

@Serializable
data class EditTransactionScreen(
    val id : String  = "",
    val transactionType: String = "Expense",
    val transactionNumber: Int = 0,
    val description : String = "",
    val date: String = "",
    val amount: String = "0.00"
)