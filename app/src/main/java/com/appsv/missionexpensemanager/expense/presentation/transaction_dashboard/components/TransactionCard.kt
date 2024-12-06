package com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsv.missionexpensemanager.core.presentation.ui.theme.DarkGrayishPurple
import com.appsv.missionexpensemanager.core.presentation.ui.theme.GrayishPurple
import com.appsv.missionexpensemanager.expense.domain.models.Transaction

@Preview
@Composable
fun TransactionCard(
    transaction: Transaction =
        Transaction(
            transactionType = "Expense",
            transactionNumber = 1,
            description = "",
            date = "2024-12-06",
            amount = "45.50"
        )
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(

            ) {
                if(transaction.description.isNotEmpty()){
                    Text(
                        transaction.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkGrayishPurple,
                        fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "${transaction.transactionType} #${transaction.transactionNumber}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = if(transaction.description.isNotEmpty()) 13.sp else 20.sp,
                    color = if(transaction.description.isNotEmpty()) GrayishPurple else DarkGrayishPurple
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    transaction.date,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 13.sp
                )


            }



            Text(
                "₹ ${transaction.amount}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = DarkGrayishPurple,
                fontSize = 20.sp
            )
        }


    }
}