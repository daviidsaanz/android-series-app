package com.david.seriesapp

import android.content.Context
import android.content.res.Configuration
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
import com.david.seriesapp.presentation.theme.SeriesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar idioma inicial
        setupLanguage()

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
                            SeriesListScreen(
                                navController = navController,
                                onLanguageChange = { switchLanguage() } // Parámetro añadido
                            )
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

    private fun setupLanguage() {
        // Opcional: Aquí podrías cargar la preferencia de idioma guardada
        // Por ahora, usamos el idioma del sistema
    }

    private fun switchLanguage() {
        val currentLocale = resources.configuration.locale
        val newLocale: Locale

        if (currentLocale.language == "en") {
            // Cambiar a español
            newLocale = Locale("es")
        } else {
            // Cambiar a inglés
            newLocale = Locale.ENGLISH
        }

        val config = Configuration(resources.configuration)
        config.setLocale(newLocale)
        config.setLayoutDirection(newLocale)

        resources.updateConfiguration(config, resources.displayMetrics)

        // Reiniciar la actividad para aplicar los cambios
        recreate()
    }
}

// Función de extensión para facilitar el cambio de idioma
fun Context.switchAppLanguage(): Context {
    val currentLocale = resources.configuration.locale
    val newLocale = if (currentLocale.language == "en") Locale("es") else Locale.ENGLISH

    val config = Configuration(resources.configuration)
    config.setLocale(newLocale)

    return createConfigurationContext(config)
}