package com.darooma.radmoviesrf.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.darooma.radmoviesrf.R
import com.darooma.radmoviesrf.application.RadMovieRFApp
import com.darooma.radmoviesrf.data.MovieRepository
import com.darooma.radmoviesrf.data.remote.model.MovieDto
import com.darooma.radmoviesrf.databinding.FragmentMoviesListBinding
import com.darooma.radmoviesrf.ui.adapters.MoviesAdapter
import com.darooma.radmoviesrf.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = (requireActivity().application as RadMovieRFApp).repository
        lifecycleScope.launch {
            //val call: Call<List<GameDto>> = repository.getGames("cm/games/games_list.php")
            val call: Call<List<MovieDto>> = repository.getMoviesApiary()

            call.enqueue(object : Callback<List<MovieDto>>{
                override fun onResponse(
                    call: Call<List<MovieDto>>,
                    response: Response<List<MovieDto>>
                ) {
                    binding.pbLoading.visibility = View.GONE

                    Log.d(Constants.LOGTAG, "${R.string.response_server} ${response.body()}")
                    response.body()?.let {movies ->
                        binding.rvMovies.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = MoviesAdapter(movies){ movie ->
                                movie.id?.let {id ->
                                    //Aquí va el código para la operación para ver los detalles
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, MovieDetailFragment.newInstance(id))
                                        .addToBackStack(null)
                                        .commit()
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<MovieDto>>, t: Throwable) {
                    Log.d(Constants.LOGTAG, "${R.string.error_dots} ${t.message}")

                    Toast.makeText(requireActivity(), R.string.error_no_conexion, Toast.LENGTH_SHORT).show()

                    binding.pbLoading.visibility = View.GONE

                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}