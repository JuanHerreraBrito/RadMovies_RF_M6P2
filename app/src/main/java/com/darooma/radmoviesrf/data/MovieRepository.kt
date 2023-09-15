package com.darooma.radmoviesrf.data

import com.darooma.radmoviesrf.data.remote.MoviesApi
import com.darooma.radmoviesrf.data.remote.model.MovieDetailDto
import com.darooma.radmoviesrf.data.remote.model.MovieDto
import retrofit2.Call
import retrofit2.Retrofit

class MovieRepository(private val retrofit: Retrofit) {

    private val moviesApi: MoviesApi = retrofit.create(MoviesApi::class.java)

    fun getMovies(url: String): Call<List<MovieDto>> =
        moviesApi.getMovies(url)

    fun getMovieDetail(id: String?): Call<MovieDetailDto> =
        moviesApi.getMovieDetail(id)

    fun getMoviesApiary(): Call<List<MovieDto>> =
        moviesApi.getMoviesApiary()

    fun getMovieDetailApiary(id: String?): Call<MovieDetailDto> =
        moviesApi.getMovieDetailApiary(id)


}