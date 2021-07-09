package com.bayuspace.myapplication.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bayuspace.myapplication.model.response.Result
import com.bayuspace.myapplication.utils.getCurrentDate

@Entity(tableName = "tbl_movie_popular")
data class MoviePopularEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String
){
    companion object{
        fun mapToMoviePopEntity(data: Result) = MoviePopularEntity(
            id = data.id,
            title = data.title,
            posterPath = data.posterPath,
            releaseDate = data.releaseDate?:"2021-01-01",
            overview = data.overview?:"-",
            backdropPath = data.backdropPath?:"",
            createdAt = getCurrentDate()
        )
    }
}