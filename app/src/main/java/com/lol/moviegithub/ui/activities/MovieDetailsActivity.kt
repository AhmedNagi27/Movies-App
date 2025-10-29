package com.lol.movies.ui.activities

import MovieViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.lol.movies.R
import com.lol.movies.databinding.ActivityMovieDetailsBinding
import com.lol.movies.util.UiState
import kotlin.getValue
import kotlin.math.roundToInt

class MovieDetailsActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMovieDetailsBinding

  private val viewModel: MovieViewModel by viewModels()

  @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
    setContentView(binding.root)
    enableEdgeToEdge()
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.movieDetailsScreen)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val movieId: Int = intent.getIntExtra("movieId", 0)

    binding.backBtn.setOnClickListener { finish() }

    viewModel.movieDetailsState.observe(this) { state ->
      when (state) {
        is UiState.Loading -> {
          binding.progressBar.visibility = View.VISIBLE
          binding.scrollView.visibility = View.GONE
        }
        is UiState.Success -> {
          binding.progressBar.visibility = View.GONE
          binding.scrollView.visibility = View.VISIBLE
          val movie = state.data
          val rating =
              if (movie.vote_average != null)
                  (((movie.vote_average * 10).roundToInt() / 10.0).toFloat())
              else 0.0f
          binding.tvMovieTitle.text = "${movie.title?:"Not available"}"
          binding.tvMovieOverview.text =
              "Overview: ${ if(movie.overview.isNullOrEmpty()) "Not available" else movie.overview }"
          binding.tvMovieRating.text = rating.toString()

          if (movie.poster_path != null) {
            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            Glide.with(this).load(posterUrl).into(binding.ivMoviePoster)
          } else binding.ivMoviePoster.setImageResource(R.drawable.img_not_found)
        }
        is UiState.Error -> {
          binding.progressBar.visibility = View.GONE
          binding.scrollView.visibility = View.GONE
          finish()
          println("Error: ${state.message}")
          Toast.makeText(this, "Error: ${state.message}", Toast.LENGTH_LONG).show()
        }
        else -> {}
      }
    }
    viewModel.movieTrailerState.observe(this) { state ->
      when (state) {
        is UiState.Loading -> {
          // You could show a loading spinner here
        }
        is UiState.Success -> {
          val trailer = state.data
          val firstTrailer = trailer.results?.firstOrNull()
          if (firstTrailer?.key != null) {
            binding.tvMovieTrailer.setOnClickListener {
              binding.videoViewTrailer.visibility = View.VISIBLE
              binding.videoViewTrailer.settings.javaScriptEnabled = true
              binding.videoViewTrailer.webViewClient = WebViewClient()
              binding.videoViewTrailer.loadUrl("https://www.youtube.com/embed/${firstTrailer.key}")
            }
          } else {
            binding.tvMovieTrailer.visibility = View.GONE
            Toast.makeText(this, "No trailer found", Toast.LENGTH_LONG).show()
          }
        }
        is UiState.Error -> {
          println("Error: ${state.message}")
          Toast.makeText(this, "Error: ${state.message}", Toast.LENGTH_LONG).show()
        }
        // It's good practice to handle all states of a sealed class
        else -> {}
      }
    }

    viewModel.fetchMovieDetails(movieId)
    viewModel.fetchMovieTrailer(movieId)
  }
}
