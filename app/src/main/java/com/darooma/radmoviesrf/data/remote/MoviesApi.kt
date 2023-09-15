package com.darooma.radmoviesrf.data.remote

import com.darooma.radmoviesrf.data.remote.model.MovieDto
import com.darooma.radmoviesrf.data.remote.model.MovieDetailDto
import com.darooma.radmoviesrf.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MoviesApi {

    @GET
    fun getMovies(
        //Se tiene que pasar el endpoint
        @Url url: String?
    ): Call<List<MovieDto>>
    //getGames("cm/games/games_list.php")

    //"cm/games/game_detail.php"
    @GET( Constants.PHP_GAME_DETAIL)
    fun getMovieDetail(
        @Query("id") id: String?
    ):Call<MovieDetailDto>

    //getGameDetail("21347")
    //"cm/games/game_detail.php?id=21347"

    //Para Apiary
    @GET(Constants.LINK_LISTMOVIES_APIARY)
    fun getMoviesApiary(): Call<List<MovieDto>>

    @GET("${Constants.LINK_DETAILMOVIES_APIARY}{id}")
    fun getMovieDetailApiary(
        @Path("id") id: String?
    ): Call<MovieDetailDto>

}