package com.david.seriesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.david.seriesapp.presentation.navigation.Routes
import com.david.seriesapp.presentation.screens.SeriesDetailScreen
import com.david.seriesapp.presentation.screens.SeriesListScreen
import com.david.seriesapp.theme.SeriesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            SeriesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.SeriesList.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Routes.SeriesList.route) {
                            SeriesListScreen(navController = navController)
                        }

                        composable(
                            route = Routes.SeriesDetail.route,
                            arguments = listOf(
                                navArgument("seriesId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            SeriesDetailScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}