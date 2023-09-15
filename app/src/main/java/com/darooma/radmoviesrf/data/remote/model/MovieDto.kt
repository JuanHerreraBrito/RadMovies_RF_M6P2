package com.darooma.radmoviesrf.data.remote.model

import com.google.gson.annotations.SerializedName

//Dto Data Tranfer Object
data class MovieDto(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("year")
    var year: String?= null,
    @SerializedName("image")
    var image: String?= null,
    @SerializedName("title")
    var title: String? = null
)