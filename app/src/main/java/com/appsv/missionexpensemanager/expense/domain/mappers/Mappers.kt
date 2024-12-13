package com.appsv.missionexpensemanager.expense.domain.mappers

import com.appsv.missionexpensemanager.expense.data.local.room.TransactionEntity
import com.appsv.missionexpensemanager.expense.domain.models.Transaction

fun Transaction.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        id = this.id,
        transactionType = this.transactionType,
        transactionNumber = this.transactionNumber,
        description = this.description,
        date = this.date,
        amount = this.amount,
    )
}