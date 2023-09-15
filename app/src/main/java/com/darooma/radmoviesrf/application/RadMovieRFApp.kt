package com.darooma.radmoviesrf.application

import android.app.Application
import com.darooma.radmoviesrf.data.MovieRepository
import com.darooma.radmoviesrf.data.remote.RetrofitHelper

class RadMovieRFApp: Application() {

    private val retrofit by lazy {
        RetrofitHelper().getRetrofit()
    }
    val repository by lazy {
        MovieRepository(retrofit)
    }
}