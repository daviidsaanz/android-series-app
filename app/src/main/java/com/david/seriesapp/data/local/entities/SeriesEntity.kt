package com.david.seriesapp.data.local.entities

import androidx.room.*
import com.david.seriesapp.data.local.converters.GenreListConverter
import com.david.seriesapp.data.local.converters.StringListConverter

@Entity(tableName = "series")
@TypeConverters(GenreListConverter::class, StringListConverter::class)
data class SeriesEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val firstAirDate: String?,
    val lastAirDate: String?,
    val numberOfSeasons: Int,
    val numberOfEpisodes: Int,
    val status: String,
    val tagline: String?,
    val genres: List<GenreEntity>,
    val createdBy: List<String>?,
    val networks: List<String>?,
    val language: String = "es-ES",
    val page: Int = 1,
    val lastUpdated: Long = System.currentTimeMillis()
)