package com.david.seriesapp.data.local.mappers

import com.david.seriesapp.data.local.entities.SeriesEntity
import com.david.seriesapp.data.remote.SeriesDetailResponse
import com.david.seriesapp.data.remote.TvSeriesDto
import com.david.seriesapp.data.local.entities.GenreEntity

// QUITA 'object SeriesMapper' - solo funciones de extensión
fun TvSeriesDto.toSeriesEntity(page: Int = 1, language: String = "es-ES"): SeriesEntity {
    return SeriesEntity(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        voteCount = 0,
        firstAirDate = firstAirDate,
        lastAirDate = null,
        numberOfSeasons = 0,
        numberOfEpisodes = 0,
        status = "",
        tagline = null,
        genres = emptyList(),
        createdBy = null,
        networks = null,
        language = language,
        page = page
    )
}

fun SeriesDetailResponse.toSeriesEntity(page: Int = 1, language: String = "es-ES"): SeriesEntity {
    return SeriesEntity(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        firstAirDate = firstAirDate,
        lastAirDate = lastAirDate,
        numberOfSeasons = numberOfSeasons,
        numberOfEpisodes = numberOfEpisodes,
        status = status,
        tagline = tagline,
        genres = genres.map { genreDto ->
            GenreEntity(
                id = genreDto.id,
                name = genreDto.name
            )
        },
        createdBy = createdBy?.map { it.name },
        networks = networks?.map { it.name },
        language = language,
        page = page
    )
}

fun SeriesEntity.toTvSeriesDto(): TvSeriesDto {
    return TvSeriesDto(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        firstAirDate = firstAirDate,
        genreIds = genres.map { it.id }
    )
}