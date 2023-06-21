package com.example.ionage.dash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.ionage.R
import com.example.ionage.databinding.FragmentMovieBinding
import com.example.ionage.util.ApiResponse
import com.example.ionage.viewmodel.MovieViewModel


class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel
    lateinit var MOVIEID: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        MOVIEID = arguments?.getString("MovieID").toString()

        viewModel.getMovieDetails(MOVIEID).observe(viewLifecycleOwner) {

            when (it) {
                is ApiResponse.Success -> {
                    binding.progressBar2.visibility = View.GONE
                    binding.layout.visibility = View.VISIBLE
                    val data = it.data
                    binding.movieImage.load(data?.Poster)
                    binding.title.text = data?.Title
                    binding.rating.text = data?.Rated
                    binding.date.text = data?.Released
                    binding.genre.text = data?.Genre
                    binding.language.text = data?.Language
                    binding.country.text = data?.Country
                    binding.plot.text = data?.Plot
                    binding.actors.text = data?.Actors
                    binding.awards.text = data?.Awards

                    data?.Ratings?.forEach {
                        val view = layoutInflater.inflate(R.layout.review_item,null)
                        view.findViewById<TextView>(R.id.source).text = it.Source
                        view.findViewById<TextView>(R.id.value).text = it.Value
                        binding.ratings.addView(view)
                    }
                }
                is ApiResponse.Loading -> {
                    binding.progressBar2.visibility = View.VISIBLE
                }
                is ApiResponse.Error -> {
                    binding.progressBar2.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.errorMessage ?: "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}