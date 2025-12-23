package com.david.seriesapp.domain.repository

import com.david.seriesapp.data.remote.SeriesDetailResponse
import com.david.seriesapp.data.remote.TvSeriesResponse

interface TvSeriesRepository {
    suspend fun getPopularTvSeries(page: Int): TvSeriesResponse
    suspend fun getSeriesDetail(seriesId: Int): SeriesDetailResponse
}