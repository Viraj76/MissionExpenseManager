package com.appsv.missionexpensemanager.expense.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import com.appsv.missionexpensemanager.expense.domain.repository.TransactionRepository
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.TransactionCreationEvents
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.TransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel
@Inject constructor(private val transactionRepository: TransactionRepository) : ViewModel() {


    private val _transactionsState = MutableStateFlow(TransactionState())
    val transactionState = _transactionsState.asStateFlow()

    init {
        getTransactions()
    }

    fun onEvent(event : TransactionCreationEvents){
        when(event){
            is TransactionCreationEvents.SaveTransaction -> {
                saveTransaction(event.transaction)
            }
        }
    }

    private fun saveTransaction(transaction: Transaction) {

    }


    private fun getTransactions() {
        viewModelScope.launch {
            transactionRepository.getTransactions().collect { transactionList ->
                if(transactionList != null){
                    _transactionsState.value = transactionState.value.copy(
                        isLoading = false,
                        transactionList = transactionList,
                        error = ""
                    )
                }
                else{
                    _transactionsState.value = transactionState.value.copy(
                        isLoading = false,
                        transactionList = emptyList(),
                        error = "Error! Try Again Later."
                    )
                }

            }
        }
    }

}