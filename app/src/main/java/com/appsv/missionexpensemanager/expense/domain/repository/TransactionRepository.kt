package com.appsv.missionexpensemanager.expense.domain.repository

import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun getTransactions() : Flow<List<Transaction>?>


    /**
     * Firebase's setValue() function handles both saving and updating data.
     * It prevents saving the same value again.
     * The logic here is used for both "save" and "update" actions.
     *
     * - If in edit mode, we update the existing data by passing its ID.
     * - If not in edit mode, we create a new record with a new ID.
     *
     * We also have updateChildren() function but this too works well.
     */
    suspend fun saveOrUpdateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(id : String)
}