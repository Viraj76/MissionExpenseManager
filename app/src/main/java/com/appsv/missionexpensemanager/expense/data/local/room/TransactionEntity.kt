package com.appsv.missionexpensemanager.expense.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val transactionType: String = "Expense",
    val transactionNumber: Int = 0,
    val description: String = "",
    val date: String = "",
    val amount: String = "0.00",
    val isDeleteOptionRevealed: Boolean = false
)
