package com.david.seriesapp.presentation.navigation

sealed class Routes(val route: String) {
    data object SeriesList : Routes("series_list")
    data object SeriesDetail : Routes("series_detail/{seriesId}") {
        fun createRoute(seriesId: Int) = "series_detail/$seriesId"
    }
}