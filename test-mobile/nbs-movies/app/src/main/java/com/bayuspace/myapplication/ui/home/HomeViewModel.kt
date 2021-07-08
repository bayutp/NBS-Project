package com.bayuspace.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bayuspace.myapplication.base.BaseViewModel
import com.bayuspace.myapplication.base.ResourceState
import com.bayuspace.myapplication.model.response.MovieResponse
import com.bayuspace.myapplication.repository.DataRepository
import com.bayuspace.myapplication.repository.network.RemoteDataSource
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: DataRepository) : BaseViewModel() {
    private val onGetDiscoverMoviesSuccess = MutableLiveData<MovieResponse>()
    private val onGetUpcomingMoviesSuccess = MutableLiveData<MovieResponse>()

    fun observeDiscoverMovies(): LiveData<MovieResponse> = onGetDiscoverMoviesSuccess
    fun observeUpcomingMovies(): LiveData<MovieResponse> = onGetUpcomingMoviesSuccess

    fun getDiscoverMovies() {
        isLoading.postValue(true)
        viewModelScope.launch {
            when (val state = repo.getDiscoverMovies()) {
                is ResourceState.Success -> {
                    isLoading.postValue(false)
                    state.result.data?.let { result ->
                        onGetDiscoverMoviesSuccess.postValue(result)
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

}