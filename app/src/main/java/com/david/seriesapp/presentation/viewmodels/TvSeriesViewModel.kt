package com.david.seriesapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.seriesapp.data.remote.TvSeriesDto
import com.david.seriesapp.domain.repository.TvSeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesViewModel @Inject constructor(private val repository: TvSeriesRepository) : ViewModel() {


    private val _uiState = MutableStateFlow<TvSeriesUiState>(TvSeriesUiState.Loading)
    val uiState: StateFlow<TvSeriesUiState> = _uiState.asStateFlow()

    init {
        loadSeries()
    }

    fun loadSeries(page: Int = 1) {
        viewModelScope.launch {
            _uiState.value = TvSeriesUiState.Loading
            try {
                val response = repository.getPopularTvSeries(page)
                _uiState.value = TvSeriesUiState.Success(response.results)
            } catch (e: Exception) {
                _uiState.value = TvSeriesUiState.Error(
                    message = e.message ?: "Error desconocido"
                )
            }
        }
    }
}


sealed class TvSeriesUiState {
    data object Loading : TvSeriesUiState()
    data class Success(val series: List<TvSeriesDto>) : TvSeriesUiState()
    data class Error(val message: String) : TvSeriesUiState()
}