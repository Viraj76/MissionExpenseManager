package com.appsv.missionexpensemanager.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.appsv.missionexpensemanager.core.presentation.nav_graph.AppNavGraph
import com.appsv.missionexpensemanager.core.presentation.ui.theme.MissionExpenseManagerTheme
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