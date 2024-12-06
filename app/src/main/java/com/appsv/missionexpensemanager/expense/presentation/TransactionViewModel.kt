package com.appsv.missionexpensemanager.expense.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsv.missionexpensemanager.expense.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel
@Inject constructor(private val transactionRepository: TransactionRepository) : ViewModel() {

    init {
        getTransactions()
    }

    private fun getTransactions() {
        viewModelScope.launch{
            transactionRepository.getTransactions().collect{transactionList->
                for(i in transactionList){
                    Log.d("transactionList", i.toString())
                }
            }
        }
    }

}