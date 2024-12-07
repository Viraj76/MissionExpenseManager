package com.appsv.missionexpensemanager.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.appsv.missionexpensemanager.core.presentation.nav_graph.AppNavGraph
import com.appsv.missionexpensemanager.core.presentation.ui.theme.MissionExpenseManagerTheme
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.TransactionCreationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            MissionExpenseManagerTheme {
                AppNavGraph()
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MissionExpenseManagerTheme {
        Greeting("Android")
    }
}