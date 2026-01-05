package com.david.seriesapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.seriesapp.data.remote.TvSeriesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor() : ViewModel() {

    private val _recommendations = MutableStateFlow<List<TvSeriesDto>>(emptyList())
    val recommendations: StateFlow<List<TvSeriesDto>> = _recommendations.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun generateRecommendations(allSeries: List<TvSeriesDto>, viewedSeries: List<TvSeriesDto>) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                // Si no hay series vistas, usar series populares como recomendaciones
                val recommendations = if (viewedSeries.isEmpty()) {
                    generatePopularRecommendations(allSeries)
                } else {
                    generatePersonalizedRecommendations(allSeries, viewedSeries)
                }

                _recommendations.value = recommendations
            } catch (e: Exception) {
                // En caso de error, devolver lista vacía
                _recommendations.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun generatePersonalizedRecommendations(
        allSeries: List<TvSeriesDto>,
        viewedSeries: List<TvSeriesDto>
    ): List<TvSeriesDto> {
        val favoriteGenres = extractFavoriteGenres(viewedSeries)

        val viewedIds = viewedSeries.map { it.id }.toSet()

        val recommendations = allSeries
            .filter { it.id !in viewedIds }
            .sortedByDescending { series ->
                calculateGenreMatchScore(series.genreIds, favoriteGenres)
            }
            .take(5)

        return if (recommendations.size < 5) {
            recommendations + generatePopularRecommendations(allSeries)
                .filter { it.id !in viewedIds && it.id !in recommendations.map { r -> r.id } }
                .take(5 - recommendations.size)
        } else {
            recommendations
        }
    }


    private fun generatePopularRecommendations(allSeries: List<TvSeriesDto>): List<TvSeriesDto> {
        return allSeries
            .sortedByDescending { it.voteAverage }
            .take(5)
    }


    private fun extractFavoriteGenres(viewedSeries: List<TvSeriesDto>): Map<Int, Int> {
        return viewedSeries
            .flatMap { it.genreIds }
            .groupingBy { it }
            .eachCount()
            .entries.sortedByDescending { it.value }
            .take(3)
            .associate { it.key to it.value }
    }

    private fun calculateGenreMatchScore(
        seriesGenres: List<Int>,
        favoriteGenres: Map<Int, Int>
    ): Int {
        var score = 0

        seriesGenres.forEach { genre ->
            val genreWeight = favoriteGenres[genre] ?: 0
            score += genreWeight * 10
        }

        val matches = seriesGenres.count { it in favoriteGenres.keys }
        score += matches * 5

        return score
    }


    fun getRecommendationDescription(viewedSeries: List<TvSeriesDto>): String {
        return if (viewedSeries.isEmpty()) {
            "Basado en series populares"
        } else {
            val topGenres = extractFavoriteGenres(viewedSeries).keys.take(2)
            val genreNames = topGenres.joinToString(", ") { getGenreName(it) }
            "Basado en tu interés en: $genreNames"
        }
    }


    private fun getGenreName(genreId: Int): String {
        return when (genreId) {
            10759 -> "Acción"
            35 -> "Comedia"
            18 -> "Drama"
            10765 -> "Ciencia ficción"
            10768 -> "Guerra"
            80 -> "Crimen"
            99 -> "Documental"
            10762 -> "Kids"
            10763 -> "News"
            10764 -> "Reality"
            10766 -> "Soap"
            10767 -> "Talk"
            28 -> "Acción"
            12 -> "Aventura"
            16 -> "Animación"
            878 -> "Ciencia ficción"
            9648 -> "Misterio"
            27 -> "Terror"
            10751 -> "Familia"
            14 -> "Fantasía"
            36 -> "Historia"
            10402 -> "Música"
            10749 -> "Romance"
            53 -> "Suspense"
            10752 -> "Bélica"
            37 -> "Western"
            else -> "Series"
        }
    }


    fun clearRecommendations() {
        _recommendations.value = emptyList()
    }
}