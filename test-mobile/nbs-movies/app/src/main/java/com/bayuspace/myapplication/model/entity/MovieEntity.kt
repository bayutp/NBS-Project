package com.bayuspace.myapplication.model.entity

import androidx.room.*
import com.bayuspace.myapplication.model.response.Genre
import com.bayuspace.myapplication.model.response.MovieDetailResponse
import com.bayuspace.myapplication.repository.local.Converter
import com.bayuspace.myapplication.utils.getCurrentDate

@Entity(tableName = "tbl_movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "genres")
    @TypeConverters(Converter::class)
    val genre: List<Genre>,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "is_bookmark")
    val isBookmark: Boolean = false,
    @ColumnInfo(name = "created_at")
    val createdAt: String
) {
    companion object {
        fun mapToMovieEntity(data: MovieDetailResponse, isBookmark: Boolean) = MovieEntity(
            data.id,
            data.title,
            data.posterPath,
            data.genres,
            data.releaseDate,
            isBookmark,
            getCurrentDate()
        )
    }
}