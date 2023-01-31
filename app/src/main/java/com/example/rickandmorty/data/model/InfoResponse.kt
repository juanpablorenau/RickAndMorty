package com.example.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("count") var count: Int? = 0,
    @SerializedName("pages") var pages: Int? = 0,
    @SerializedName("next") var next: String? = "null",
    @SerializedName("prev") var prev: String? = ""
)
