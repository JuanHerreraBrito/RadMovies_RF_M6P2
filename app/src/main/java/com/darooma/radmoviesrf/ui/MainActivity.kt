package com.darooma.radmoviesrf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.darooma.radmoviesrf.R
import com.darooma.radmoviesrf.data.MovieRepository
import com.darooma.radmoviesrf.data.remote.RetrofitHelper
import com.darooma.radmoviesrf.databinding.ActivityMainBinding
import com.darooma.radmoviesrf.ui.fragments.MoviesListFragment
import com.darooma.radmoviesrf.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var  repository: MovieRepository
    private lateinit var  retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RadMoviesRF)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MoviesListFragment())
                .commit()

        }

    }
}