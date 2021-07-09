package com.bayuspace.myapplication.model.response

import android.os.Parcelable
import com.bayuspace.myapplication.model.entity.MoviePopularEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

@Parcelize
data class Result(
    @SerializedName("adult")
    val adult: Boolean = false,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String?="",
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean = false,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
):Parcelable{
    companion object{
        fun mapToMovieResponse(data: MoviePopularEntity) = Result(
            id = data.id,
            title = data.title,
            posterPath = data.posterPath,
            releaseDate = data.releaseDate,
            overview = data.overview,
            backdropPath = data.backdropPath
        )
    }
}