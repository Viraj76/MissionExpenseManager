package com.appsv.missionexpensemanager.expense.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TransactionEntity::class], version = 1)
abstract class TransactionRoomDB : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
