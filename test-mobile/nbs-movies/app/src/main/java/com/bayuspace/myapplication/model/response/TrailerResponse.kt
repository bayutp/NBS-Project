package com.bayuspace.myapplication.model.response

import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<ResultVideo>
)

data class ResultVideo(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("type")
    val type: String
)