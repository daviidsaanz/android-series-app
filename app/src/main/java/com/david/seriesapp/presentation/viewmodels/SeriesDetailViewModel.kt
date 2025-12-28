package com.david.seriesapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.seriesapp.domain.repository.TvSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(
    private val repository: TvSeriesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<SeriesDetailUiState>(SeriesDetailUiState.Loading)
    val uiState: StateFlow<SeriesDetailUiState> = _uiState.asStateFlow()

    init {
        val seriesId = savedStateHandle.get<Int>("seriesId")
        seriesId?.let { loadSeriesDetail(it) }
    }

    private fun loadSeriesDetail(seriesId: Int) {
        viewModelScope.launch {
            _uiState.value = SeriesDetailUiState.Loading
            try {
                // Intenta cargar desde API
                val response = repository.getSeriesDetail(seriesId)
                _uiState.value = SeriesDetailUiState.Success(response)
            } catch (e: Exception) {
                // Si falla, intenta cargar desde cache
                loadSeriesDetailFromCache(seriesId, e)
            }
        }
    }

    private fun loadSeriesDetailFromCache(seriesId: Int, exception: Exception) {
        viewModelScope.launch {
            if (repository is com.david.seriesapp.data.repository.TvSeriesRepositoryImpl) {
                repository.getCachedSeriesById(seriesId).collect { cachedSeries ->
                    if (cachedSeries != null) {
                        // Convertir TvSeriesDto a algo que se pueda mostrar en detalle
                        // Necesitarás crear un mapper o extender TvSeriesDto
                        _uiState.value = SeriesDetailUiState.Offline(
                            series = cachedSeries,
                            message = "Modo offline. Mostrando información guardada."
                        )
                    } else {
                        _uiState.value = SeriesDetailUiState.Error(
                            message = "No hay conexión y no hay detalles guardados: ${exception.message}"
                        )
                    }
                }
            } else {
                _uiState.value = SeriesDetailUiState.Error(
                    message = "Modo offline no disponible: ${exception.message}"
                )
            }
        }
    }

    fun retry(seriesId: Int) {
        loadSeriesDetail(seriesId)
    }
}

sealed class SeriesDetailUiState {
    data object Loading : SeriesDetailUiState()
    data class Success(val seriesDetail: com.david.seriesapp.data.remote.SeriesDetailResponse) : SeriesDetailUiState()
    data class Offline(val series: com.david.seriesapp.data.remote.TvSeriesDto, val message: String) : SeriesDetailUiState()
    data class Error(val message: String) : SeriesDetailUiState()
}