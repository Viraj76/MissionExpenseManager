package com.appsv.missionexpensemanager.expense.domain.repository


interface CountRepository {
    suspend fun getIncomeCount(): Int
    suspend fun saveIncomeCount()
    suspend fun getExpenseCount(): Int
    suspend fun saveExpenseCount()
}
