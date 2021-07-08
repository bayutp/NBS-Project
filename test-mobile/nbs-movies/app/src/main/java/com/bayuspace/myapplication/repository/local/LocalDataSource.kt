package com.bayuspace.myapplication.repository.local

import com.bayuspace.myapplication.base.BaseDataSource
import com.bayuspace.myapplication.base.ResourceState
import com.bayuspace.myapplication.base.ResponseWrapper
import com.bayuspace.myapplication.model.entity.MovieEntity

class LocalDataSource(private val db: LocalDatabase) : BaseDataSource() {
    private suspend fun <T> getResult(request: suspend () -> T): ResourceState<ResponseWrapper<T>> {
        return try {
            val res = request.invoke()
            return ResourceState.Success(ResponseWrapper("OK", data = res, errorData = null))
        } catch (e: Exception) {
            errorState(msg = e.toString())
        }
    }

    suspend fun getMovies() {
        suspendDataResult { getResult { db.movieDao().getMovies() } }
    }

    suspend fun searchMovies(query: String) {
        suspendDataResult { getResult { db.movieDao().searchMovies(query) } }
    }

    suspend fun insertMovies(data: List<MovieEntity>) =
        suspendDataResult { getResult { db.movieDao().insertMovies(data) } }

    suspend fun getMovie(id: Int) =
        suspendDataResult { getResult { db.movieDao().getMovie(id) } }
}