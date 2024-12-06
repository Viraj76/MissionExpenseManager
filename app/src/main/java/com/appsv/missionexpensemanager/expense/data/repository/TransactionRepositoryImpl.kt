package com.appsv.missionexpensemanager.expense.data.repository

import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import com.appsv.missionexpensemanager.expense.domain.repository.TransactionRepository
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val firebaseDatabase: FirebaseDatabase): TransactionRepository {
    override suspend fun getTransactions()  {
        TODO("Not yet implemented")
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }
}