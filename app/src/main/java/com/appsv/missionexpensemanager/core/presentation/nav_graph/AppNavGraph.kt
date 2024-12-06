package com.appsv.missionexpensemanager.core.presentation.nav_graph

import android.util.Log
import android.view.SurfaceControl
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appsv.missionexpensemanager.expense.presentation.TransactionViewModel
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.TransactionDashboardScreen
import androidx.compose.runtime.getValue
import androidx.navigation.toRoute
import com.appsv.missionexpensemanager.expense.domain.models.Transaction
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.TransactionCreationScreen
import com.appsv.missionexpensemanager.expense.presentation.transaction_details.TransactionDetailsScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TransactionDashboardScreen
    ){

        composable<TransactionDashboardScreen>{
            val transactionViewModel  = hiltViewModel<TransactionViewModel>()

            val transactionState by transactionViewModel.transactionState.collectAsStateWithLifecycle()
            TransactionDashboardScreen(
                transactionState = transactionState,
                onTransactionCardClick = {
                    navController.navigate(EditTransactionScreen(
                        id = it.id,
                        transactionType = it.transactionType,
                        transactionNumber = it.transactionNumber,
                        description = it.description,
                        date = it.date,
                        amount = it.amount
                    ))
                },
                onFabClick = {
                    navController.navigate(TransactionCreationScreen())
                },
                events = transactionViewModel::onEvent
            )
        }

        composable<TransactionCreationScreen>{
            val transactionViewModel  = hiltViewModel<TransactionViewModel>()
            val args = it.toRoute<TransactionCreationScreen>()

            val transaction = Transaction(
                id = args.id,
                transactionType = args.transactionType,
                transactionNumber = args.transactionNumber,
                description = args.description,
                date = args.date,
                amount = args.amount
            )

            LaunchedEffect(Unit) {
                Log.d("Details", transaction.toString())
            }

            TransactionCreationScreen(
                selectedTransaction = transaction,
                events = transactionViewModel::onEvent,
                goToTransactionDashBoard = {
                    navController.navigate(TransactionDashboardScreen){
                        popUpTo<TransactionDashboardScreen>{
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<EditTransactionScreen>{
            val transactionViewModel  = hiltViewModel<TransactionViewModel>()

            val args = it.toRoute<EditTransactionScreen>()

            TransactionDetailsScreen(
                events = transactionViewModel::onEvent,
                selectedTransaction = Transaction(
                    id = args.id,
                    transactionType = args.transactionType,
                    transactionNumber = args.transactionNumber,
                    description = args.description,
                    date = args.date,
                    amount = args.amount
                ),
                onEditIconClicked = {
                    navController.navigate(TransactionCreationScreen(
                        id = it.id,
                        transactionType = it.transactionType,
                        transactionNumber = it.transactionNumber,
                        description = it.description,
                        date = it.date,
                        amount = it.amount
                    ))
                },
                goToTransactionDashBoard = {
                    navController.navigate(TransactionDashboardScreen){
                        popUpTo<TransactionDashboardScreen>{
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}