package com.phonepe.phonepemovies.ui.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.phonepe.phonepemovies.data.entities.Movie
import com.phonepe.phonepemovies.databinding.FragmentMoviesBinding
import com.phonepe.phonepemovies.ui.MovieDetailActivity
import com.phonepe.phonepemovies.utils.State
import com.phonepe.phonepemovies.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


/**
 * A fragment for listing of Movies.
 */
@AndroidEntryPoint
class MoviesFragment : Fragment(), MoviesViewHolder.OnMovieClicked {

    private var binding: FragmentMoviesBinding by autoCleared()
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("MovieTest", "onCreateView: ")
        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MovieTest", "onViewCreated: ")
        setupRecyclerView()
        setupObservers()

    }


    private fun setupRecyclerView() {
        Log.d("MovieTest", "setupRecyclerView:")
        activity?.let {
            adapter = MoviesAdapter(it, this)
            val layoutManager = LinearLayoutManager(requireContext())

            binding.moviesRv.layoutManager = layoutManager
            binding.moviesRv.adapter = adapter

            val dividerItemDecoration = DividerItemDecoration(
                binding.moviesRv.context,
                layoutManager.orientation
            )
            binding.moviesRv.addItemDecoration(dividerItemDecoration)
        }

    }

    private fun setupObservers() {
        Log.d("MovieTest", "setupObservers: ")
        viewModel.movies.observe(viewLifecycleOwner, {
            Log.d("MovieTest", "setupObservers: ${it.status}")
            when (it.status) {
                State.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        Log.d("MovieTest", "isNullOrEmpty:")
                        binding.animationView.visibility = View.GONE
                        adapter.setItems(ArrayList(it.data))
                        setupSearchListener()
                        //adapter.notifyDataSetChanged()
                    }
                }
                State.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                State.Status.LOADING ->
                    binding.animationView.visibility = View.VISIBLE
            }
        })
    }


    private fun setupSearchListener() {

        binding.searchBar.addTextChangedListener {
            adapter.getFilter()?.filter(it.toString())
        }
    }

    override fun onMovieClicked(movie: Movie) {

        val intent = Intent(requireContext(),MovieDetailActivity::class.java)
        intent.putExtra("original_title",movie.original_title)
        intent.putExtra("releaseDateValue",movie.release_date)
        intent.putExtra("ratingValue",movie.vote_average)
        intent.putExtra("popularityValue",movie.popularity)
        intent.putExtra("image",movie.poster_path)
        intent.putExtra("overView",movie.overview)
        startActivity(intent)

    }


}
