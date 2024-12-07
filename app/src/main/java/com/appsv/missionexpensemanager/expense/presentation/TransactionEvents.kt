package com.appsv.missionexpensemanager.expense.presentation

import com.appsv.missionexpensemanager.expense.domain.models.Transaction

sealed class TransactionEvents {

    data class SaveOrUpdateTransaction(val transaction : Transaction,val isEditMode : Boolean) : TransactionEvents()

    data class DeleteTransaction(val id : String) : TransactionEvents()

    data object StartSearchingTransactions  : TransactionEvents()

    data class SearchTransactions(val searchingText : String) : TransactionEvents()

    data class FilterTransactions(val label : String) : TransactionEvents()
}