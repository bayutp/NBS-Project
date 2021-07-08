package com.bayuspace.myapplication.repository.network

import com.bayuspace.myapplication.api.MovieApiService
import com.bayuspace.myapplication.base.BaseDataSource
import com.bayuspace.myapplication.base.ResourceState
import com.bayuspace.myapplication.base.ResponseWrapper
import retrofit2.Response
import java.net.UnknownHostException

class RemoteDataSource(private val service: MovieApiService) : BaseDataSource() {
    private suspend fun <T> getResult(request: suspend () -> Response<T>): ResourceState<ResponseWrapper<T>> {
        return try {
            val response = request()
            val body = response.body()
            if (response.isSuccessful.not() || body == null) {
                return errorState(response.code(), response.message())
            }

            return ResourceState.Success(
                ResponseWrapper(
                    null,
                    null,
                    body,
                    null
                )
            )
        } catch (e: Exception) {
            errorState(msg = if (e is UnknownHostException) NO_INTERNET else e.localizedMessage.orEmpty())
        }
    }

    suspend fun getDiscoverMovies() =
        suspendDataResult { getResult { service.getDiscoverMovies() } }

    suspend fun getMovieDetail(movieId: Int) =
        suspendDataResult { getResult { service.getMovieDetail(movieId) } }

    suspend fun getMovieCasting(movieId: Int) =
        suspendDataResult { getResult { service.getMovieCasting(movieId) } }

    companion object {
        const val NO_INTERNET = "No internet connection!"
    }
}