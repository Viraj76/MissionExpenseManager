package com.appsv.missionexpensemanager.expense.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import com.appsv.missionexpensemanager.expense.domain.repository.CountRepository
import com.appsv.missionexpensemanager.expense.domain.repository.TransactionRepository
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.TransactionCreationEvents
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.TransactionCountState
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.TransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel
@Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val countRepository: CountRepository
) : ViewModel() {


    private val _transactionsState = MutableStateFlow(TransactionState())
    val transactionState = _transactionsState.asStateFlow()

    private val _transactionCountState = MutableStateFlow(TransactionCountState())
    val transactionCountState = _transactionCountState.asStateFlow()

    private val _transactionSaveStatus = MutableStateFlow(false)
    val transactionSaveStatus: StateFlow<Boolean> = _transactionSaveStatus.asStateFlow()


    init {
        loadCounts()
        getTransactions()
    }

    fun onEvent(
        events: TransactionCreationEvents,
    ) {
        when (events) {
            is TransactionCreationEvents.SaveOrUpdateTransaction -> {
                saveTransaction(events.transaction)
            }

            is TransactionCreationEvents.DeleteTransaction -> {
                deleteTransaction(events.id)
            }

            is TransactionCreationEvents.SearchTransactions -> {
                _transactionsState.value =
                    transactionState.value.copy(searchingText = events.searchingText)
            }

            is TransactionCreationEvents.StartSearchingTransactions -> {
                searchTransactions()
            }

            is TransactionCreationEvents.FilterTransactions -> {
                filterTransactions(events.label)
            }
        }
    }

    private fun filterTransactions(query: String) {
        val originalList = transactionState.value.originalTransactionList
        val filteredList = if (query == "All") {
            originalList
        } else {
            originalList.filter { transaction ->
                transaction.transactionType.contains(query, ignoreCase = true)
            }
        }

        _transactionsState.value = transactionState.value.copy(
            modifiedFilteredTransactionList = filteredList
        )
    }

    private fun saveTransaction(transaction: Transaction) {
        viewModelScope.launch {
            // Update the transactionNumber based on transaction type
            val updatedTransaction = if (transaction.transactionType == "Expense") {
                transaction.copy(transactionNumber = transactionCountState.value.expenseCount + 1)
            } else {
                transaction.copy(transactionNumber = transactionCountState.value.incomeCount + 1)
            }

            // Save the updated transaction
            transactionRepository.saveOrUpdateTransaction(updatedTransaction)

            // Increment the respective count based on transaction type
            if (transaction.transactionType == "Expense") {
                incrementExpenseCount()
            } else {
                incrementIncomeCount()
            }

            // Signal that the transaction save process is complete
            _transactionSaveStatus.value = true
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

        _transactionsState.value =
            transactionState.value.copy(modifiedFilteredTransactionList = searchedTransactionsList)
    }

    private fun getTransactions() {
        viewModelScope.launch {
            transactionRepository.getTransactions().collect { transactionList ->
                Log.d("Repository", "VM ${transactionList.toString()}")
                if (transactionList != null) {
                    _transactionsState.value = transactionState.value.copy(
                        isLoading = false,
                        originalTransactionList = transactionList,
                        error = ""
                    )
                    searchTransactions()
                } else {
                    _transactionsState.value = transactionState.value.copy(
                        isLoading = false,
                        originalTransactionList = emptyList(),
                        error = "Error! Try Again Later."
                    )
                }

            }
        }
    }

    private fun deleteTransaction(id: String) {
        viewModelScope.launch {
            transactionRepository.deleteTransaction(id)

        }

    }


    private fun loadCounts() {
        viewModelScope.launch {
            Log.d("Countss","${ countRepository.getExpenseCount()}, ${ countRepository.getIncomeCount()}")
            _transactionCountState.value = transactionCountState.value.copy(incomeCount = countRepository.getIncomeCount())
            _transactionCountState.value = transactionCountState.value.copy(expenseCount = countRepository.getExpenseCount())
        }
    }

    fun incrementIncomeCount() {
        viewModelScope.launch {
            countRepository.saveIncomeCount()
            loadCounts()
            _transactionCountState.value = transactionCountState.value.copy(incomeCount = countRepository.getIncomeCount())
        }
    }

    fun incrementExpenseCount() {
        viewModelScope.launch {
            countRepository.saveExpenseCount()
            loadCounts()
            _transactionCountState.value = transactionCountState.value.copy(expenseCount = countRepository.getExpenseCount())
        }
    }
}