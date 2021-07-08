package com.bayuspace.myapplication.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bayuspace.myapplication.model.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM tbl_movie WHERE is_bookmark = 1 ORDER BY created_at DESC")
    fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM tbl_movie WHERE title LIKE :query")
    fun searchMovies(query: String): List<MovieEntity>

    @Query("SELECT * from tbl_movie WHere id = :id AND is_bookmark = 1")
    fun getMovie(id: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(data: List<MovieEntity>)
}