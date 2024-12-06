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

    fun onEvent(
        events : TransactionCreationEvents,
    ){
        when(events){
            is TransactionCreationEvents.SaveOrUpdateTransaction -> {
                saveTransaction(events.transaction)
            }

            is TransactionCreationEvents.DeleteTransaction -> {
                deleteTransaction(events.id)
            }

            is TransactionCreationEvents.SearchTransactions -> {
                _transactionsState.value = transactionState.value.copy(searchingText = events.searchingText)
            }

            TransactionCreationEvents.StartSearchingTransactions -> {
                searchTransactions()
            }
        }
    }

    private fun saveTransaction(transaction: Transaction) {
        viewModelScope.launch{
            transactionRepository.saveOrUpdateTransaction(transaction)
        }
    }


    private fun searchTransactions() {
        val searchText = transactionState.value.searchingText.lowercase()
        val searchedTransactionsList = transactionState.value.originalTransactionList.filter {
            it.description.lowercase().contains(searchText) ||
            it.transactionType.lowercase().contains(searchText) ||
            it.amount.lowercase().contains(searchText) ||
            it.transactionNumber.toString().lowercase().contains(searchText) ||
            it.date.toString().lowercase().contains(searchText)
        }
        Log.d("Searcingtext", searchedTransactionsList.size.toString())

        _transactionsState.value = transactionState.value.copy(modifiedFilteredTransactionList = searchedTransactionsList)
    }
    private fun getTransactions() {
        viewModelScope.launch {
            transactionRepository.getTransactions().collect { transactionList ->
                Log.d("listtt",transactionList.toString())
                if(transactionList != null){
                    _transactionsState.value = transactionState.value.copy(
                        isLoading = false,
                        originalTransactionList = transactionList,
                        error = ""
                    )
                    searchTransactions()
                }
                else{
                    _transactionsState.value = transactionState.value.copy(
                        isLoading = false,
                        originalTransactionList = emptyList(),
                        error = "Error! Try Again Later."
                    )
                }

            }
        }
    }
    private fun deleteTransaction(id : String){
        viewModelScope.launch{
            transactionRepository.deleteTransaction(id)

        }

    }
}