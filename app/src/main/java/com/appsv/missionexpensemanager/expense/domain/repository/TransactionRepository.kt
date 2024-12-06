package com.appsv.missionexpensemanager.expense.domain.repository

import com.appsv.missionexpensemanager.expense.domain.models.Transaction

interface TransactionRepository {

    suspend fun getTransactions()

    suspend fun saveTransaction(transaction: Transaction)
}