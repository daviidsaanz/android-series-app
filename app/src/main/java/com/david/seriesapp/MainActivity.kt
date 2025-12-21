package com.david.seriesapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.david.seriesapp.theme.SeriesAppTheme
import com.david.seriesapp.viewmodel.ViewModelList

class MainActivity : ComponentActivity() {
    val viewModelList = ViewModelList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            SeriesAppTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Pantalla0.route,
                        modifier = Modifier.padding(innerPadding)
                    )
                    {
                        composable(Routes.Pantalla0.route) { ViewList(navController, viewModelList) }
                        composable(Routes.Pantalla1.route) { ViewList(navController, viewModelList) }
                    }
                }
            }
        }
    }
}