package com.david.seriesapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.seriesapp.data.remote.TvSeriesDto
import com.david.seriesapp.domain.repository.TvSeriesRepository
import com.david.seriesapp.utils.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesViewModel @Inject constructor(
    private val repository: TvSeriesRepository,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

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
                        isOffline = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                loadFromCache(page = 1, isFallback = true, exception = e)
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

                _uiState.update {
                    it.copy(
                        series = it.series + response.results,
                        isLoadingMore = false,
                        isOffline = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                loadMoreFromCache(currentPage + 1, e)
            }
        }
    }

    fun refreshSeries() {
        currentPage = 1
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
                        isOffline = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                loadFromCache(page = 1, isFallback = true, exception = e)
            }
        }
    }

    // ✅ MÉTODO QUE FALTABA
    fun checkConnectionAndRefresh() {
        viewModelScope.launch {
            val isConnected = connectivityManager.checkConnection()

            if (isConnected && _uiState.value.isOffline) {
                refreshSeries()
            } else if (!isConnected) {
                _uiState.update {
                    it.copy(
                        isOffline = true,
                        error = "Sin conexión a internet"
                    )
                }
            }
        }
    }

    private fun loadFromCache(
        page: Int,
        isFallback: Boolean,
        exception: Exception?
    ) {
        viewModelScope.launch {
            if (repository is com.david.seriesapp.data.repository.TvSeriesRepositoryImpl) {
                repository.getCachedSeries(page).collect { cachedSeries ->
                    if (cachedSeries.isNotEmpty()) {
                        _uiState.update {
                            it.copy(
                                series = cachedSeries,
                                isLoading = false,
                                isLoadingMore = false,
                                isOffline = true,
                                error = if (isFallback)
                                    "Modo offline. Mostrando series guardadas."
                                else null
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLoadingMore = false,
                                isOffline = true,
                                error = exception?.message
                                    ?: "No hay conexión y no hay series guardadas"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadMoreFromCache(nextPage: Int, exception: Exception) {
        viewModelScope.launch {
            if (repository is com.david.seriesapp.data.repository.TvSeriesRepositoryImpl) {
                repository.getCachedSeries(nextPage).collect { cachedSeries ->
                    if (cachedSeries.isNotEmpty()) {
                        currentPage = nextPage
                        _uiState.update {
                            it.copy(
                                series = it.series + cachedSeries,
                                isLoadingMore = false,
                                isOffline = true,
                                error = "Modo offline. Cargando más desde cache."
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoadingMore = false,
                                isOffline = true,
                                error = "No hay más series en cache"
                            )
                        }
                    }
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

data class TvSeriesUiState(
    val series: List<TvSeriesDto> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isOffline: Boolean = false,
    val error: String? = null
)
