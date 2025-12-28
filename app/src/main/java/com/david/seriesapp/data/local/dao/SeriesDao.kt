package com.david.seriesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.david.seriesapp.data.local.entities.SeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {


    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertSeries(series: SeriesEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllSeries(series: List<SeriesEntity>)


    @Query("SELECT * FROM series ORDER BY voteAverage DESC")
    fun getAllSeries(): Flow<List<SeriesEntity>>


    @Query("SELECT * FROM series WHERE id = :id")
    fun getSeriesById(id: Int): Flow<SeriesEntity?>


    @Query("SELECT * FROM series WHERE page = :page ORDER BY voteAverage DESC")
    fun getSeriesByPage(page: Int): Flow<List<SeriesEntity>>

    @Query("SELECT * FROM series WHERE name LIKE '%' || :query || '%' ORDER BY voteAverage DESC")
    fun searchSeries(query: String): Flow<List<SeriesEntity>>

    @Query("SELECT * FROM series WHERE genres LIKE '%' || :genreName || '%' ORDER BY voteAverage DESC")
    fun getSeriesByGenre(genreName: String): Flow<List<SeriesEntity>>

    @Query("DELETE FROM series")
    suspend fun deleteAllSeries()

    @Query("DELETE FROM series WHERE lastUpdated < :timestamp")
    suspend fun deleteOldSeries(timestamp: Long)

    @Query("SELECT COUNT(*) FROM series")
    suspend fun getSeriesCount(): Int

    @Query("SELECT MAX(page) FROM series")
    suspend fun getLastPage(): Int?
}