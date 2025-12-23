package com.david.seriesapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.seriesapp.data.remote.SeriesDetailResponse
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
                val response = repository.getSeriesDetail(seriesId)
                _uiState.value = SeriesDetailUiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = SeriesDetailUiState.Error(
                    message = e.message ?: "Error al cargar los detalles"
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
    data class Success(val seriesDetail: SeriesDetailResponse) : SeriesDetailUiState()
    data class Error(val message: String) : SeriesDetailUiState()
}