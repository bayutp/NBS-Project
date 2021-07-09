package com.bayuspace.myapplication.repository.local

import androidx.room.TypeConverter
import com.bayuspace.myapplication.model.response.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun toGenres(genre: String): List<Genre> {
        val type = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(genre, type)
    }

    @TypeConverter
    fun fromGenres(genres: List<Genre>): String {
        val type = object : TypeToken<List<Genre>>() {}.type
        return Gson().toJson(genres, type)
    }
}