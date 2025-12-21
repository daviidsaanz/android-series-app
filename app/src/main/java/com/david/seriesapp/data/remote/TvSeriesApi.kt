package com.david.seriesapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TvSeriesApi {
    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("page") page: Int = 1
    ): String
}