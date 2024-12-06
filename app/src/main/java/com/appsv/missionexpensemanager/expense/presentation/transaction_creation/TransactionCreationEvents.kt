package com.appsv.missionexpensemanager.expense.presentation.transaction_creation

import com.appsv.missionexpensemanager.expense.domain.models.Transaction

sealed class TransactionCreationEvents {

    data class SaveOrUpdateTransaction(val transaction : Transaction) : TransactionCreationEvents()

    data class DeleteTransaction(val id : String) : TransactionCreationEvents()
}