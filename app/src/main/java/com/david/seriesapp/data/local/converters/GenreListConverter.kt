package com.david.seriesapp.data.local.converters

import androidx.room.TypeConverter
import com.david.seriesapp.data.local.entities.GenreEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<GenreEntity> {
        if (value.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<GenreEntity>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun toString(list: List<GenreEntity>?): String {
        return gson.toJson(list ?: emptyList<String>())
    }
}