package com.bayuspace.myapplication.repository.local

import com.bayuspace.myapplication.base.BaseDataSource
import com.bayuspace.myapplication.base.ResourceState
import com.bayuspace.myapplication.base.ResponseWrapper
import com.bayuspace.myapplication.model.entity.MovieEntity
import com.bayuspace.myapplication.model.entity.MoviePopularEntity

class LocalDataSource(private val db: LocalDatabase) : BaseDataSource() {
    private suspend fun <T> getResult(request: suspend () -> T): ResourceState<ResponseWrapper<T>> {
        return try {
            val res = request.invoke()
            return ResourceState.Success(ResponseWrapper("OK", data = res, errorData = null))
        } catch (e: Exception) {
            errorState(msg = e.toString())
        }
    }

    suspend fun getMovies() = db.movieDao().getMovies()

    suspend fun searchMovies(query: String) = db.movieDao().searchMovies(query)

    suspend fun insertMovies(data: List<MovieEntity>) =
        suspendDataResult { getResult { db.movieDao().insertMovies(data) } }

    suspend fun getMovie(id: Int) =
        suspendDataResult { getResult { db.movieDao().getMovie(id) } }

    suspend fun getPopMovies() = db.moviePopularDao().getMovies()

    suspend fun searchPopMovies(query: String) = db.moviePopularDao().searchMovies(query)

    suspend fun insertPopMovies(data: List<MoviePopularEntity>) =
        suspendDataResult { getResult { db.moviePopularDao().insertMovies(data) } }
}