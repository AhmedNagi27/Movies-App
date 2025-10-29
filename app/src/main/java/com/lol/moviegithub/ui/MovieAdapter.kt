package com.lol.movies.ui

import android.R.id.message
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lol.movies.R
import com.lol.movies.data.model.Movie
import com.lol.movies.databinding.ItemMovei2Binding
import com.lol.moviegithub.ui.activities.MovieDetailsActivity
import kotlin.jvm.java
import kotlin.math.roundToInt

class MovieAdapter(private var movies: List<Movie>, val onMovieClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

  inner class MovieViewHolder(val binding: ItemMovei2Binding) :
      RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val binding = ItemMovei2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MovieViewHolder(binding)
  }

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val movie = movies[position]
    val rating =
        if (movie.vote_average != null)
            ((movie.vote_average.times(10)).roundToInt().div(10.0)).toString()
        else "0.0"
    holder.binding.tvTitle.text = movie.title ?: "Not Available "
    holder.binding.tvRating.text = rating
    holder.binding.tvReleaseDate.text = movie.release_date ?: "Not Available"
    holder.binding.root.setOnClickListener {
      val intent =
          Intent(holder.itemView.context, MovieDetailsActivity::class.java).apply {
            try {
              putExtra("movieId", movie.id)
            } catch (e: Exception) {
              println("Error: $message")
              Toast.makeText(holder.itemView.context, "Error: $message", Toast.LENGTH_SHORT).show()
            }
          }

      startActivity(holder.itemView.context, intent, null)
      onMovieClick(movie)
      true
    }
    if (movie.poster_path != null) {
      val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
      Glide.with(holder.itemView.context).load(posterUrl).into(holder.binding.ivPoster)
    } else holder.binding.ivPoster.setImageResource(R.drawable.img_not_found)
  }

  override fun getItemCount(): Int = movies.size

  fun updateData(newMovies: List<Movie>) {
    movies = newMovies
    notifyDataSetChanged()
  }
}
