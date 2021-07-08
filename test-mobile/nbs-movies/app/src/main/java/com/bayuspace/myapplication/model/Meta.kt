package com.bayuspace.myapplication.model

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("code")
    val code: Int? = 0,
    @SerializedName("message")
    val message: String? = ""
)