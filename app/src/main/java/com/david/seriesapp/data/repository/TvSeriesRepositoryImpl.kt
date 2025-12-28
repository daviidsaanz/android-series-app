package com.david.seriesapp.data.repository

import com.david.seriesapp.data.local.dao.SeriesDao
import com.david.seriesapp.data.local.mappers.toSeriesEntity
import com.david.seriesapp.data.local.mappers.toTvSeriesDto
import com.david.seriesapp.data.remote.SeriesDetailResponse
import com.david.seriesapp.data.remote.TvSeriesApi
import com.david.seriesapp.data.remote.TvSeriesDto
import com.david.seriesapp.data.remote.TvSeriesResponse
import com.david.seriesapp.domain.repository.TvSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvSeriesRepositoryImpl @Inject constructor(
    private val api: TvSeriesApi,
    private val seriesDao: SeriesDao
) : TvSeriesRepository {

    override suspend fun getPopularTvSeries(page: Int): TvSeriesResponse {
        val response = api.getPopularTvSeries(page = page)

        // Guardar en Room usando la función de extensión
        val seriesEntities = response.results.map { dto ->
            dto.toSeriesEntity(page = page)
        }
        seriesDao.insertAllSeries(seriesEntities)

        return response
    }

    override suspend fun getSeriesDetail(seriesId: Int): SeriesDetailResponse {
        val response = api.getSeriesDetail(seriesId = seriesId)

        // Guardar detalles usando la función de extensión
        val seriesEntity = response.toSeriesEntity(page = 1)
        seriesDao.insertSeries(seriesEntity)

        return response
    }

    // MÉTODOS PARA MODO OFFLINE
    override fun getCachedSeries(page: Int): Flow<List<TvSeriesDto>> {
        return seriesDao.getSeriesByPage(page).map { entities ->
            entities.map { entity ->
                entity.toTvSeriesDto()
            }
        }
    }

    override fun getCachedSeriesById(id: Int): Flow<TvSeriesDto?> {
        return seriesDao.getSeriesById(id).map { entity ->
            entity?.toTvSeriesDto()
        }
    }

    fun getAllCachedSeries(): Flow<List<TvSeriesDto>> {
        return seriesDao.getAllSeries().map { entities ->
            entities.map { entity ->
                entity.toTvSeriesDto()
            }
        }
    }

    override suspend fun clearCache() {
        seriesDao.deleteAllSeries()
    }

    override suspend fun getCacheSize(): Int {
        return seriesDao.getSeriesCount()
    }
}