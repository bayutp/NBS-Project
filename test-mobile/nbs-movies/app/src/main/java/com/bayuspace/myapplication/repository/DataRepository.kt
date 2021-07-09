package com.bayuspace.myapplication.repository

import com.bayuspace.myapplication.model.entity.MovieEntity
import com.bayuspace.myapplication.model.entity.MoviePopularEntity
import com.bayuspace.myapplication.repository.local.LocalDataSource
import com.bayuspace.myapplication.repository.network.RemoteDataSource

class DataRepository(private val local: LocalDataSource, private val remote: RemoteDataSource) {
    suspend fun getDiscoverMovies() = remote.getDiscoverMovies()
    suspend fun getUpcomingMovies() = remote.getUpcomingMovies()
    suspend fun getMovieDetail(id: Int) = remote.getMovieDetail(id)
    suspend fun getMovieCasting(id: Int) = remote.getMovieCasting(id)
    suspend fun getTrailerMovies(id:Int) = remote.getTrailerMovies(id)

    suspend fun getMovieBookmarked() = local.getMovies()
    suspend fun searchMovieBookmarked(query: String) = local.searchMovies(query)
    suspend fun checkMovieBookmarked(id: Int) = local.getMovie(id)
    suspend fun saveBookmark(data: List<MovieEntity>) = local.insertMovies(data)

    suspend fun getPopMovies() = local.getPopMovies()
    suspend fun searchMovies(query: String) = local.searchPopMovies(query)
    suspend fun insertPopMovies(data: List<MoviePopularEntity>) = local.insertPopMovies(data)

}