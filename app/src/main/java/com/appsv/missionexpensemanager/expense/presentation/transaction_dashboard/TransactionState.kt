package com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard

import com.appsv.missionexpensemanager.expense.data.local.room.TransactionEntity
import com.appsv.missionexpensemanager.expense.domain.models.Transaction

data class TransactionState(
    val isLoading : Boolean = true,
    val transactionList : List<TransactionEntity> = emptyList(),
    val error : String = ""
)
