package com.darooma.radmoviesrf.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.darooma.radmoviesrf.R
import com.darooma.radmoviesrf.application.RadMovieRFApp
import com.darooma.radmoviesrf.data.MovieRepository
import com.darooma.radmoviesrf.data.remote.model.MovieDetailDto
import com.darooma.radmoviesrf.databinding.FragmentMovieDetailBinding
import com.darooma.radmoviesrf.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MOVIE_ID = Constants.ID_MOVIE_BEWARE

class MovieDetailFragment : Fragment() {
    private var movieId: String? = null
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getString(MOVIE_ID)

            Log.d(Constants.LOGTAG, "${R.string.gnrl_id_received} $movieId")

            repository = (requireActivity().application as RadMovieRFApp).repository

            lifecycleScope.launch {
                movieId?.let{ id->
                    //val call: Call<MovieDetailDto> = repository.getGameDetail(id)
                    val call: Call<MovieDetailDto> = repository.getMovieDetailApiary(id)


                    call.enqueue(object : Callback<MovieDetailDto>{
                        override fun onResponse(
                            call: Call<MovieDetailDto>,
                            response: Response<MovieDetailDto>
                        ) {
                            binding.apply {
                                pbLoading.visibility = View.GONE

                                tvTitle.text = response.body()?.title
                                tvLongDesc.text = response.body()?.synopsis
                                tvYear.text = response.body()?.year
                                tvGenres.text = response.body()?.genres
                                tvProducer.text = response.body()?.producer
                                tvRating.text = response.body()?.rating

                                Glide.with(requireContext())
                                    .load(response.body()?.image)
                                    .into(ivImage)
                            }
                        }

                        override fun onFailure(call: Call<MovieDetailDto>, t: Throwable) {
                            binding.pbLoading.visibility = View.GONE

                            Toast.makeText(requireActivity(), "${R.string.error_no_conexion} ${t.message}", Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: String) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(MOVIE_ID, movieId)
                }
            }
    }
}