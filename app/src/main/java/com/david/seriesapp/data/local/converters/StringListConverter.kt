package com.david.seriesapp.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.emptyList

class StringListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<String> {
        if (value.isNullOrEmpty()) return emptyList<String>()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList<String>()
    }

    @TypeConverter
    fun toString(list: List<String>?): String {
        return gson.toJson(list ?: emptyList<String>())
    }
}
