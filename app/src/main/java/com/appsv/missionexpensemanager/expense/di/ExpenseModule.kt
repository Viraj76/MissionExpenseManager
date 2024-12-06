package com.appsv.missionexpensemanager.expense.di

import com.appsv.missionexpensemanager.expense.data.repository.TransactionRepositoryImpl
import com.appsv.missionexpensemanager.expense.domain.repository.TransactionRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideTransactionRepository(firebaseDatabase: FirebaseDatabase) : TransactionRepository {
        return TransactionRepositoryImpl(firebaseDatabase)
    }

}