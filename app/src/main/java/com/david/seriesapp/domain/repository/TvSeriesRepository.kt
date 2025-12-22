package com.david.seriesapp.domain.repository

import com.david.seriesapp.data.remote.TvSeriesResponse

interface TvSeriesRepository {
    suspend fun getPopularTvSeries(page: Int): TvSeriesResponse
}