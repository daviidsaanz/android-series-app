package com.david.seriesapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvSeriesApi {
    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "es-ES"
    ): TvSeriesResponse

    @GET("tv/{series_id}")
    suspend fun getSeriesDetail(
        @Path("series_id") seriesId: Int,
        @Query("language") language: String = "es-ES"
    ): SeriesDetailResponse
}