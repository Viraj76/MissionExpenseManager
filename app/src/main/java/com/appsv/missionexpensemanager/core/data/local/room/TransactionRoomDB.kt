package com.appsv.missionexpensemanager.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appsv.missionexpensemanager.expense.data.local.room.TransactionDao
import com.appsv.missionexpensemanager.expense.data.local.room.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1)
abstract class TransactionRoomDB : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
