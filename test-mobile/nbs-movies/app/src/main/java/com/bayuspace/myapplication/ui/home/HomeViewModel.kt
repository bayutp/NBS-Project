package com.bayuspace.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bayuspace.myapplication.base.BaseViewModel
import com.bayuspace.myapplication.base.ResourceState
import com.bayuspace.myapplication.model.entity.MoviePopularEntity
import com.bayuspace.myapplication.model.response.MovieResponse
import com.bayuspace.myapplication.model.response.Result
import com.bayuspace.myapplication.repository.DataRepository
import com.bayuspace.myapplication.repository.network.RemoteDataSource
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: DataRepository) : BaseViewModel() {
    private val onGetDiscoverMoviesSuccess = MutableLiveData<List<Result>>()
    private val onGetUpcomingMoviesSuccess = MutableLiveData<MovieResponse>()
    private val onSearchPopMoviesSuccess = MutableLiveData<List<MoviePopularEntity>>()

    fun observeSearchFavMovies(): LiveData<List<MoviePopularEntity>> = onSearchPopMoviesSuccess
    fun observeDiscoverMovies(): LiveData<List<Result>> = onGetDiscoverMoviesSuccess
    fun observeUpcomingMovies(): LiveData<MovieResponse> = onGetUpcomingMoviesSuccess

    fun getDiscoverMovies() {
        isLoading.postValue(true)
        viewModelScope.launch {
            val data = repo.getPopMovies()
            if (data.isEmpty()) getDiscoverMoviesFromRemote()
            else {
                onGetDiscoverMoviesSuccess.postValue(data.flatMap {
                    listOf(
                        Result.mapToMovieResponse(
                            it
                        )
                    )
                })
                getDiscoverMoviesFromRemote()
            }
        }
    }

    private suspend fun getDiscoverMoviesFromRemote() {
        when (val state = repo.getDiscoverMovies()) {
            is ResourceState.Success -> {
                isLoading.postValue(false)
                state.result.data?.let { result ->
                    onGetDiscoverMoviesSuccess.postValue(result.results)
                    repo.insertPopMovies(result.results.flatMap {
                        listOf(
                            MoviePopularEntity.mapToMoviePopEntity(it)
                        )
                    })
                }
            }

            is ResourceState.Error -> {
                isLoading.postValue(false)
                if (state.error.errorData?.msg?.contains(RemoteDataSource.NO_INTERNET) == true) noInternet.postValue(
                    true
                )
                else errorResponse.postValue(state.error.errorData)
            }

            is ResourceState.Loading -> isLoading.postValue(true)
        }
    }

    fun getUpcomingMovies() {
        isLoading.postValue(true)
        viewModelScope.launch {
            when (val state = repo.getUpcomingMovies()) {
                is ResourceState.Success -> {
                    isLoading.postValue(false)
                    state.result.data?.let { result ->
                        onGetUpcomingMoviesSuccess.postValue(result)
                    }
                }

                is ResourceState.Error -> {
                    isLoading.postValue(false)
                    if (state.error.errorData?.msg?.contains(RemoteDataSource.NO_INTERNET) == true) noInternet.postValue(
                        true
                    )
                    else errorResponse.postValue(state.error.errorData)
                }

                is ResourceState.Loading -> isLoading.postValue(true)
            }
        }
    }

    fun getSearchMovies(query: String) {
        viewModelScope.launch {
            val data = repo.searchMovies(query)
            isEmptyData.postValue(data.isEmpty())
            onSearchPopMoviesSuccess.postValue(data)
        }
    }

}