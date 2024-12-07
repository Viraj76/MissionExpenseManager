package com.appsv.missionexpensemanager.expense.data.repository


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.appsv.missionexpensemanager.expense.domain.repository.CountRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


/**
 * Monitors the number of transactions saved by the user, categorized by income and expenses.
 */
private val Context.dataStore by preferencesDataStore("transaction_counts")

class CountRepositoryImpl(private val context: Context) : CountRepository {

    private val incomeKey = intPreferencesKey("income_count")
    private val expenseKey = intPreferencesKey("expense_count")

    override suspend fun getIncomeCount(): Int {
        return context.dataStore.data.map { preferences ->
            preferences[incomeKey] ?: 0
        }.first()
    }

    override suspend fun saveIncomeCount() {
        context.dataStore.edit { preferences ->
            val currentCount = preferences[incomeKey] ?: 0
            preferences[incomeKey] = currentCount + 1
        }
    }

    override suspend fun getExpenseCount(): Int {
        return context.dataStore.data.map { preferences ->
            preferences[expenseKey] ?: 0
        }.first()
    }

    override suspend fun saveExpenseCount() {
        context.dataStore.edit { preferences ->
            val currentCount = preferences[expenseKey] ?: 0
            preferences[expenseKey] = currentCount + 1
        }
    }
}
