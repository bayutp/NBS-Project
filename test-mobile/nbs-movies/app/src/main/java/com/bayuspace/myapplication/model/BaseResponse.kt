package com.bayuspace.myapplication.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("message")
    val msg: String,
    @SerializedName("data")
    val data: T
)