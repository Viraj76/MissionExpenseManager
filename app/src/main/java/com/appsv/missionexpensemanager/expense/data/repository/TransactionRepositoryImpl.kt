package com.appsv.missionexpensemanager.expense.data.repository

import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import com.appsv.missionexpensemanager.expense.domain.repository.TransactionRepository
import com.appsv.missionexpensemanager.expense.utils.TRANSACTION
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val firebaseDatabase: FirebaseDatabase): TransactionRepository {

    private val transactionRef = firebaseDatabase.getReference(TRANSACTION)

    override suspend fun getTransactions() : Flow<List<Transaction>?> = callbackFlow {

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val toDoUIItems: List<Transaction> = snapshot.children.mapNotNull {
                    it.getValue(Transaction::class.java)
                }
                trySend(toDoUIItems)
            }
            override fun onCancelled(error: DatabaseError) {
                close(null)
            }
        }

        transactionRef.addValueEventListener(listener)

        awaitClose { transactionRef.removeEventListener(listener) }
    }

    override suspend fun saveOrUpdateTransaction(transaction: Transaction) {
        transactionRef.child(transaction.id).setValue(transaction)
    }

    override suspend fun deleteTransaction(id: String) {
        transactionRef.child(id).removeValue()
    }
}