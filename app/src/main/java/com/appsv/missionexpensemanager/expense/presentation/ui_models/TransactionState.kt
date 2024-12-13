package com.appsv.missionexpensemanager.expense.presentation.ui_models

import com.appsv.missionexpensemanager.expense.data.local.room.TransactionEntity

data class TransactionState(
    val isLoading : Boolean = true,
    val originalTransactionList : List<TransactionEntity> = emptyList(),
    val modifiedFilteredTransactionList : List<TransactionEntity> = emptyList(),
    val error : String = "",
    val searchingText : String = "",
)