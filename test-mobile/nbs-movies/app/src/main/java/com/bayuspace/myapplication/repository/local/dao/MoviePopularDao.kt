package com.bayuspace.myapplication.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bayuspace.myapplication.model.entity.MoviePopularEntity

@Dao
interface MoviePopularDao {
    @Query("SELECT * FROM tbl_movie_popular ORDER BY created_at ASC")
    suspend fun getMovies(): List<MoviePopularEntity>

    @Query("SELECT * FROM tbl_movie_popular WHERE title LIKE :query")
    suspend fun searchMovies(query: String): List<MoviePopularEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(data: List<MoviePopularEntity>)
}