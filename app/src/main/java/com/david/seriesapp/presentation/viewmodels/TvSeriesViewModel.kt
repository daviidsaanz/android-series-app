package com.david.seriesapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.seriesapp.data.remote.TvSeriesDto
import com.david.seriesapp.domain.repository.TvSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesViewModel @Inject constructor( private val repository: TvSeriesRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(TvSeriesUiState())
    val uiState: StateFlow<TvSeriesUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var totalPages = 1

    init {
        loadInitialSeries()
    }

    fun loadInitialSeries() {
        if (_uiState.value.isLoading) return

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val response = repository.getPopularTvSeries(page = 1)
                currentPage = response.page
                totalPages = response.totalPages

                _uiState.update {
                    it.copy(
                        series = response.results,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error al cargar series"
                    )
                }
            }
        }
    }

    fun loadMoreSeries() {
        if (_uiState.value.isLoadingMore || currentPage >= totalPages) return

        _uiState.update { it.copy(isLoadingMore = true) }

        viewModelScope.launch {
            try {
                val nextPage = currentPage + 1
                val response = repository.getPopularTvSeries(page = nextPage)

                currentPage = response.page

                // Combinar la lista actual con la nueva
                val updatedList = _uiState.value.series + response.results

                _uiState.update {
                    it.copy(
                        series = updatedList,
                        isLoadingMore = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingMore = false,
                        error = e.message ?: "Error al cargar más series"
                    )
                }
            }
        }
    }

    fun refreshSeries() {
        currentPage = 1
        loadInitialSeries()
    }
}

// Estado de la UI con toda la información necesaria para paginación
data class TvSeriesUiState(
    val series: List<TvSeriesDto> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null
)