package com.darooma.radmoviesrf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
//import com.darooma.videogamesrf.data.remote.PokemonApi
//import com.darooma.videogamesrf.data.remote.model.PokemonDetail2Dto
//import com.darooma.videogamesrf.databinding.ActivityTestPokemonBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestPokemon : AppCompatActivity() {
//
//    private lateinit var binding: ActivityTestPokemonBinding
//
//    private val BASE_URL = "https://pokeapi.co/"
//
//    private val LOGTAG = "LOGS"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityTestPokemonBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val pokemonApi: PokemonApi = retrofit.create(PokemonApi::class.java)
//
//        val call: Call<PokemonDetail2Dto> = pokemonApi.getPokemonDetail("93")
//
//        call.enqueue(object: Callback<PokemonDetail2Dto> {
//            override fun onResponse(
//                call: Call<PokemonDetail2Dto>,
//                response: Response<PokemonDetail2Dto>
//            ) {
//                Log.d(LOGTAG, "${response.body()?.sprites?.other?.officialArtwork?.front_shiny}")
//
//                //response.body()?.name
//
//                Glide.with(this@TestPokemon)
//                    .load(response.body()?.sprites?.other?.officialArtwork?.front_shiny)
//                    .into(binding.ivPokemon)
//            }
//
//            override fun onFailure(call: Call<PokemonDetail2Dto>, t: Throwable) {
//
//            }
//
//        })
//    }
}
