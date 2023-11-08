package com.darooma.radmoviesrf.ui.fragments

import android.app.AlertDialog
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
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

    private lateinit var mp : MediaPlayer

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
                                ibOpenVideo.setOnClickListener {
                                    //Toast.makeText(context, "Aqui abre video", Toast.LENGTH_LONG).show()
                                    binding.vvVideo.setVideoURI(Uri.parse(response.body()?.video))
                                    val mc = MediaController(context) //Establecemos un mediacontroller para tener controles en el video
                                    mc.setAnchorView(binding.vvVideo)
                                    binding.vvVideo.setMediaController(mc)
                                    binding.vvVideo.start()
                                }

                                ibMapsLocation.setOnClickListener {
                                    requireActivity().getSupportFragmentManager().popBackStack();
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, MapLocationFragment.newInstance(
                                            response.body()?.latitud,
                                            response.body()?.longitud,
                                            response.body()?.maptext
                                        ))
                                        .addToBackStack("showMap")
                                        .commit()
                                }

                                Glide.with(requireContext())
                                    .load(response.body()?.image)
                                    .into(ivImage)
                            }
                        }

                        override fun onFailure(call: Call<MovieDetailDto>, t: Throwable) {
                            binding.pbLoading.visibility = View.GONE

//                            val builder: AlertDialog.Builder? = activity?.let {
//                                AlertDialog.Builder(it)
//                            }
//
//                            builder?.setMessage("Mensaje")?.setTitle("titulo")
//                                ?.setPositiveButton("Aceptar") { dialog, which ->
//                                    Toast.makeText(requireActivity(), "${R.string.error_no_conexion} ${t.message}", Toast.LENGTH_SHORT).show()
//                                }
//
//                            val dialog: AlertDialog? = builder?.create()
                            AlertDialog.Builder(requireContext())
                                .setTitle(getString(R.string.error_msg))
                                .setMessage(getString(R.string.check_internet_msg))
                                .setNeutralButton(getString(R.string.acept_title)){ dialog, _ ->
                                    //Toast.makeText(requireActivity(), "${R.string.error_no_conexion} ${t.message}", Toast.LENGTH_SHORT).show()

                                    dialog.dismiss()
                                    //val ft = parentFragmentManager.beginTransaction()
                                    //ft.detach(this@MoviesListFragment).attach(this@MoviesListFragment).commit()

                                    //val ft = parentFragmentManager.beginTransaction()
                                    //ft.detach(this@MoviesListFragment)
                                    //    .attach(this@MoviesListFragment).commit()

                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, MoviesListFragment.newInstance())
                                        .addToBackStack(null)
                                        .commit()
                                }
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .create()
                                .show()
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
                    if(movieId.isNotEmpty()) {
                        putString(MOVIE_ID, movieId)
                    }else{
                        putString(MOVIE_ID, getString(R.string.default_movie))
                    }
                }
            }
    }
}