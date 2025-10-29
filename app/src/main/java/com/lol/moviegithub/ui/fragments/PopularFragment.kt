package com.lol.movies.ui.fragments

import com.lol.movies.ui.MovieAdapter
import MovieViewModel
import android.R
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.lol.movies.databinding.FragmentPopularBinding
import com.lol.movies.util.UiState
import kotlin.getValue

class PopularFragment : Fragment() {
  private lateinit var binding: FragmentPopularBinding
  private val viewModel: MovieViewModel by viewModels()
  private lateinit var adapter: MovieAdapter

  private var currentPage = 1
  private var totalPages = 1

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
  ): View? {
    binding = FragmentPopularBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    adapter = MovieAdapter(emptyList()) {}
    binding.popularRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    binding.popularRecyclerView.adapter = adapter

    viewModel.uiState.observe(viewLifecycleOwner) { state ->
      when (state) {
        is UiState.Loading -> binding.pProgressBar.visibility = View.VISIBLE
        is UiState.Success -> {
          binding.pProgressBar.visibility = View.GONE
          adapter.updateData(state.data)
        }
        is UiState.Error -> {
          binding.pProgressBar.visibility = View.GONE
          println("Error: ${state.message}")
          Toast.makeText(requireContext(), "Error: ${state.message}", Toast.LENGTH_LONG).show()
        }
      }
    }

    binding.btnPrev.setOnClickListener {
      if (currentPage > 1) {
        currentPage--
        renderPagination(binding.pagesContainer)
        viewModel.fetchPopular(currentPage)
      }
    }

    binding.btnNext.setOnClickListener {
      if (currentPage < totalPages && currentPage <= 500) {
        currentPage++
        renderPagination(binding.pagesContainer)
        viewModel.fetchPopular(currentPage)
      }
    }

    viewModel.TotalPages.observe(viewLifecycleOwner) { pages ->
      totalPages = pages
      renderPagination(binding.pagesContainer)
    }

    renderPagination(binding.pagesContainer)

    viewModel.fetchPopular(1)
  }

  private fun renderPagination(container: LinearLayout) {
    container.removeAllViews()

    val pages = getDisplayedPages(currentPage, totalPages)

    for (page in pages) {
      val tv =
          TextView(requireContext()).apply {
            text = page
            textSize = 24f
            setPadding(20, 12, 20, 12)

            if (page == "...") {
              setTextColor(Color.GRAY)
            } else {
              setTextColor(Color.WHITE)

              if (page.toInt() == currentPage) {
                setTextColor(Color.BLUE)
                setBackgroundResource(R.color.darker_gray)
              }
              setOnClickListener {
                currentPage = page.toInt()
                renderPagination(container)
                viewModel.fetchPopular(currentPage)
              }
            }
          }
      container.addView(tv)
    }
  }

  private fun getDisplayedPages(current: Int, total: Int): List<String> {
    val pages = mutableListOf<String>()
    val displayTotal = if (total > 500) 500 else total

    if (displayTotal <= 7) {
      // Show all pages if they are few
      for (i in 1..displayTotal) pages.add(i.toString())
    } else {
      if (current <= 4) {
        // Beginning of pagination
        pages.addAll(listOf("1", "2", "3", "4", "...", displayTotal.toString()))
      } else if (current >= displayTotal - 3) {
        // End of pagination
        pages.add("1")
        pages.add("...")
        for (i in (displayTotal - 3)..displayTotal) pages.add(i.toString())
      } else {
        // Middle of pagination
        pages.add("1")
        pages.add("...")
        pages.addAll(listOf((current - 1).toString(), current.toString(), (current + 1).toString()))
        pages.add("...")
        pages.add(displayTotal.toString())
      }
    }

    return pages
  }
}
