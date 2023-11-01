package com.darooma.radmoviesrf.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("year")
    var year: String? = null,
    @SerializedName("genres")
    var genres: String? = null,
    @SerializedName("producer")
    var producer: String? = null,
    @SerializedName("rating")
    var rating: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("synopsis")
    var synopsis: String? = null,
    @SerializedName("video")
    var video: String? = null
)
