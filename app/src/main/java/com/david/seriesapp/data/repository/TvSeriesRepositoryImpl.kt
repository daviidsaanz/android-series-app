package com.david.seriesapp.data.repository

import com.david.seriesapp.data.remote.SeriesDetailResponse
import com.david.seriesapp.data.remote.TvSeriesApi
import com.david.seriesapp.data.remote.TvSeriesResponse
import com.david.seriesapp.domain.repository.TvSeriesRepository
import javax.inject.Inject

class TvSeriesRepositoryImpl @Inject constructor(
    private val api: TvSeriesApi
) : TvSeriesRepository {
    override suspend fun getPopularTvSeries(page: Int): TvSeriesResponse {
        return api.getPopularTvSeries(page = page)
    }

    override suspend fun getSeriesDetail(seriesId: Int): SeriesDetailResponse {
        return api.getSeriesDetail(seriesId = seriesId)
    }
}