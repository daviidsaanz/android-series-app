// En domain/repository/TvSeriesRepository.kt
package com.david.seriesapp.domain.repository

import com.david.seriesapp.data.remote.SeriesDetailResponse
import com.david.seriesapp.data.remote.TvSeriesResponse
import com.david.seriesapp.data.remote.TvSeriesDto
import kotlinx.coroutines.flow.Flow

interface TvSeriesRepository {
    suspend fun getPopularTvSeries(page: Int): TvSeriesResponse
    suspend fun getSeriesDetail(seriesId: Int): SeriesDetailResponse

    fun getCachedSeries(page: Int): Flow<List<TvSeriesDto>>
    fun getCachedSeriesById(id: Int): Flow<TvSeriesDto?>
    suspend fun clearCache()
    suspend fun getCacheSize(): Int
}