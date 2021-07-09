package com.bayuspace.myapplication.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bayuspace.myapplication.base.BaseViewModel
import com.bayuspace.myapplication.base.ResourceState
import com.bayuspace.myapplication.model.response.MovieCastingResponse
import com.bayuspace.myapplication.model.response.MovieDetailResponse
import com.bayuspace.myapplication.repository.DataRepository
import com.bayuspace.myapplication.repository.network.RemoteDataSource
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: DataRepository) : BaseViewModel() {
    private val onGetDetailMovieSuccess = MutableLiveData<MovieDetailResponse>()
    private val onGetMovieCastingsSuccess = MutableLiveData<MovieCastingResponse>()

    fun observeDetailMovie(): LiveData<MovieDetailResponse> = onGetDetailMovieSuccess
    fun observeMovieCastings(): LiveData<MovieCastingResponse> = onGetMovieCastingsSuccess

    fun getDetailMovie(id: Int) {
        isLoading.postValue(true)
        viewModelScope.launch {
            when (val state = repo.getMovieDetail(id)) {
                is ResourceState.Success -> {
                    isLoading.postValue(false)
                    state.result.data?.let { result ->
                        onGetDetailMovieSuccess.postValue(result)
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

    fun getMovieCastings(id: Int) {
        isLoading.postValue(true)
        viewModelScope.launch {
            when (val state = repo.getMovieCasting(id)) {
                is ResourceState.Success -> {
                    isLoading.postValue(false)
                    state.result.data?.let { result ->
                        onGetMovieCastingsSuccess.postValue(result)
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