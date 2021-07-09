package com.bayuspace.myapplication.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bayuspace.myapplication.model.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM tbl_movie WHERE is_bookmark = 1 ORDER BY created_at DESC")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM tbl_movie WHERE title LIKE :query")
    suspend fun searchMovies(query: String): List<MovieEntity>

    @Query("SELECT * from tbl_movie WHere id = :id AND is_bookmark = 1")
    suspend fun getMovie(id: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(data: List<MovieEntity>)
}