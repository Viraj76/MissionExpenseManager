package com.appsv.missionexpensemanager.expense.di

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

}