package com.appsv.missionexpensemanager.expense.di

import TransactionRepositoryImpl
import android.content.Context
import androidx.room.Room
import com.appsv.missionexpensemanager.expense.data.local.room.TransactionDao
import com.appsv.missionexpensemanager.expense.data.local.room.TransactionRoomDB
import com.appsv.missionexpensemanager.expense.data.repository.CountRepositoryImpl
import com.appsv.missionexpensemanager.expense.domain.repository.CountRepository
import com.appsv.missionexpensemanager.expense.domain.repository.TransactionRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ExpenseModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabaseInstance() : FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(firebaseDatabase: FirebaseDatabase,transactionDao: TransactionDao) : TransactionRepository {
        return TransactionRepositoryImpl(firebaseDatabase,transactionDao)
    }

    @Provides
    @Singleton
    fun provideTransactionRoomDatabase(@ApplicationContext context: Context): TransactionRoomDB {
        return Room.databaseBuilder(
            context,
            TransactionRoomDB::class.java,
            "transaction_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(appDatabase: TransactionRoomDB): TransactionDao {
        return appDatabase.transactionDao()
    }

    @Provides
    @Singleton
    fun provideCountRepository(@ApplicationContext context: Context) : CountRepository{
        return CountRepositoryImpl(context)
    }
}