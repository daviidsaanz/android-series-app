package com.david.seriesapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.david.seriesapp.data.local.converters.GenreListConverter
import com.david.seriesapp.data.local.converters.StringListConverter
import com.david.seriesapp.data.local.dao.SeriesDao
import com.david.seriesapp.data.local.entities.GenreEntity
import com.david.seriesapp.data.local.entities.SeriesEntity

@Database(
    entities = [SeriesEntity::class, GenreEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenreListConverter::class, StringListConverter::class)
abstract class SeriesDatabase : RoomDatabase() {

    abstract fun seriesDao(): SeriesDao

    companion object {
        @Volatile
        private var INSTANCE: SeriesDatabase? = null

        fun getInstance(context: Context): SeriesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SeriesDatabase::class.java,
                    "series_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}