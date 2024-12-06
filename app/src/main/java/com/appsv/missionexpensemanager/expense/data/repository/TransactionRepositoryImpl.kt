package com.appsv.missionexpensemanager.expense.data.repository

import android.util.Log
import com.appsv.missionexpensemanager.expense.data.local.room.TransactionDao
import com.appsv.missionexpensemanager.expense.data.local.room.TransactionEntity
import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import com.appsv.missionexpensemanager.expense.domain.repository.TransactionRepository
import com.appsv.missionexpensemanager.expense.utils.TRANSACTION
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val transactionDao: TransactionDao
): TransactionRepository {

    private val transactionRef = firebaseDatabase.getReference(TRANSACTION)

    override suspend fun getTransactions(): Flow<List<TransactionEntity>?> = callbackFlow {
        val roomTransactions = transactionDao.fetchAllTransactions()
        if (roomTransactions.isNotEmpty()) {
            trySend(roomTransactions)
        }

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val transactionListRemote: List<TransactionEntity> = snapshot.children.mapNotNull {
                    it.getValue(Transaction::class.java)
                }.map {
                    it.toTransactionEntity()
                }

                CoroutineScope(Dispatchers.IO).launch {
                    transactionDao.saveTransactions(transactionListRemote)
                    Log.d("TransactionRepo", "Transactions saved to Room: ${transactionListRemote.size}")
                }

                trySend(transactionListRemote)
            }

            override fun onCancelled(error: DatabaseError) {
                close(null)
            }
        }

        transactionRef.addValueEventListener(listener)

        awaitClose {
            transactionRef.removeEventListener(listener)
        }
    }

    override suspend fun saveOrUpdateTransaction(transaction: Transaction) {
        transactionRef.child(transaction.id).setValue(transaction)
    }

    override suspend fun deleteTransaction(id: String) {
        transactionRef.child(id).removeValue()
    }
}

fun Transaction.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        id = this.id,
        transactionType = this.transactionType,
        transactionNumber = this.transactionNumber,
        description = this.description,
        date = this.date,
        amount = this.amount,
        isDeleteOptionRevealed = this.isDeleteOptionRevealed
    )
}
