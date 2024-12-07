package com.appsv.missionexpensemanager.expense.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
    return dateFormat.format(Date(timestamp))
}
