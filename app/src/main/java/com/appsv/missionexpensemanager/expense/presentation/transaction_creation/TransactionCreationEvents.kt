package com.appsv.missionexpensemanager.expense.presentation.transaction_creation

import com.appsv.missionexpensemanager.expense.domain.models.Transaction

sealed class TransactionCreationEvents {

    data class SaveTransaction(val transaction : Transaction) : TransactionCreationEvents()

}