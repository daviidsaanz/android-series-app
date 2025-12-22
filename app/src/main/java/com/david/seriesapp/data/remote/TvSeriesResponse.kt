package com.david.seriesapp.data.remote

import com.google.gson.annotations.SerializedName


data class TvSeriesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TvSeriesDto>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)


data class TvSeriesDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>
) {

    val fullPosterPath: String
        get() = if (!posterPath.isNullOrEmpty()) {
            "https://image.tmdb.org/t/p/w500$posterPath"
        } else {
            ""
        }
}