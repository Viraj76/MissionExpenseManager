package com.appsv.missionexpensemanager.expense.utils

import com.appsv.missionexpensemanager.expense.domain.models.Transaction


/**
 * Just a dummy list of transactions
 */
val transactionsDummyList = listOf(
    Transaction(
        transactionType = "Expense",
        transactionNumber = 1,
        description = "Groceries",
        date = "2024-12-06",
        amount = "45.50"
    ),
    Transaction(
        transactionType = "Income",
        transactionNumber = 2,
        description = "Salary",
        date = "2024-12-01",
        amount = "1500.00"
    ),
    Transaction(
        transactionType = "Expense",
        transactionNumber = 3,
        description = "Electricity Bill",
        date = "2024-12-05",
        amount = "75.30"
    ),
    Transaction(
        transactionType = "Income",
        transactionNumber = 4,
        description = "Freelance Project",
        date = "2024-12-02",
        amount ="500.00"
    ),
    Transaction(
        transactionType = "Expense",
        transactionNumber = 5,
        description = "Dining Out",
        date = "2024-12-04",
        amount = "60.75"
    )
)

/**
 * Firebase Paths
 */

const val TRANSACTION = "Transactions"
