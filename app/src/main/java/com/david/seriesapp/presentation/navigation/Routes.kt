package com.david.seriesapp.view
sealed class Routes(val route:String) {
    data object Pantalla0: Routes("pantalla0")
    data object Pantalla1: Routes("pantalla1")
}