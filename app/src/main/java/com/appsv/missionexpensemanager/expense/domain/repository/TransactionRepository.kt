package com.appsv.missionexpensemanager.expense.domain.repository

import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun getTransactions() : Flow<List<Transaction>?>

    suspend fun saveTransaction(transaction: Transaction)
}