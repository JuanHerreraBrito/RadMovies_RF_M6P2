package com.darooma.radmoviesrf.ui.adapters

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darooma.radmoviesrf.R
import com.darooma.radmoviesrf.data.remote.model.MovieDto
import com.darooma.radmoviesrf.databinding.MovieElementBinding


class MoviesAdapter(
    private val movies: List<MovieDto>,
    private val onMovieClicked: (MovieDto) -> Unit
): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private lateinit var mp : MediaPlayer

    class ViewHolder(private val binding: MovieElementBinding): RecyclerView.ViewHolder(binding.root){

        val ivThumbnail = binding.ivThumbnail

        fun bind(movie: MovieDto){
            binding.tvTitle.text = movie.title
            binding.tvYear.text = movie.year
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)

        //Con picasso
        /*
        Picasso.get()
            .load(game.thumbnail)
            .into(holder.ivThumbnail)
        */
        //Con Glide
        Glide.with(holder.itemView.context)
            .load(movie.image)
            .into(holder.ivThumbnail)

        //Procesar click al elemento
        holder.itemView.setOnClickListener {
            mp = MediaPlayer.create(holder.itemView.context, R.raw.listen_navi)
            mp.start()
            onMovieClicked(movie)
        }


    }
}