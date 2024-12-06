package com.appsv.missionexpensemanager.core.presentation.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appsv.missionexpensemanager.expense.presentation.transaction_dashboard.TransactionDashboardScreen

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
            TransactionDashboardScreen()
        }
    }
}