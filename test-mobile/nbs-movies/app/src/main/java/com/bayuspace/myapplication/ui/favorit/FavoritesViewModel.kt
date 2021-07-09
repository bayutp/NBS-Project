package com.bayuspace.myapplication.ui.favorit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bayuspace.myapplication.base.BaseViewModel
import com.bayuspace.myapplication.model.entity.MovieEntity
import com.bayuspace.myapplication.repository.DataRepository
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repo: DataRepository) : BaseViewModel() {
    private val onGetFavMoviesSuccess = MutableLiveData<List<MovieEntity>>()
    private val onSearchFavMoviesSuccess = MutableLiveData<List<MovieEntity>>()

    fun observeGetFavMovies(): LiveData<List<MovieEntity>> = onGetFavMoviesSuccess
    fun observeSearchFavMovies(): LiveData<List<MovieEntity>> = onSearchFavMoviesSuccess

    fun getFavMovies() {
        viewModelScope.launch {
            val data = repo.getMovieBookmarked()
            isEmptyData.postValue(data.isEmpty())
            onGetFavMoviesSuccess.postValue(data)
        }
    }

    fun getSearchFavMovies(query: String) {
        viewModelScope.launch {
            val data = repo.searchMovieBookmarked(query)
            isEmptyData.postValue(data.isEmpty())
            onGetFavMoviesSuccess.postValue(data)
        }
    }

}