package com.appsv.missionexpensemanager.core.presentation.nav_graph

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
import androidx.compose.runtime.setValue
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.TransactionCreationScreen

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
                transactionState = transactionState
            ){
                navController.navigate(TransactionCreationScreen)
            }
        }

        composable<TransactionCreationScreen>{
            val transactionViewModel  = hiltViewModel<TransactionViewModel>()

            TransactionCreationScreen(
                events = transactionViewModel::onEvent
            )
        }
    }
}