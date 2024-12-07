package com.appsv.missionexpensemanager.expense.data.local.room

import androidx.room.*

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transaction: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransactions(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM transactions")
    suspend fun fetchAllTransactions(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun fetchTransactionById(id: String): TransactionEntity?

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransaction(id: String)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)
}
