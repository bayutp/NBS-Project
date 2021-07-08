package com.bayuspace.myapplication.api

import com.bayuspace.myapplication.BuildConfig
import com.bayuspace.myapplication.model.response.MovieCastingResponse
import com.bayuspace.myapplication.model.response.MovieDetailResponse
import com.bayuspace.myapplication.model.response.MovieResponse
import com.bayuspace.myapplication.utils.getCurrentDate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.Year

interface MovieApiService {
    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("sort_by") sort: String = "popularity.desc"
    ): Response<MovieResponse>

    @GET("discover/movie")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("sort_by") sort: String = "popularity.desc",
        @Query("year") year: Int = getCurrentDate().toInt() + 1
    ): Response<MovieResponse>

    @GET("movie/{id_movie}")
    suspend fun getMovieDetail(
        @Path("id_movie") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieDetailResponse>

    @GET("movie/{id_movie}/credits")
    suspend fun getMovieCasting(
        @Path("id_movie") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieCastingResponse>

}