package com.example.ionage.dash

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.ionage.R
import com.example.ionage.databinding.FragmentSearchBinding
import com.example.ionage.model.SearchResults
import com.example.ionage.util.ApiResponse
import com.example.ionage.viewmodel.SearchViewModel
import kotlin.math.log

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    val searchAdapter =  SearchAdapter()
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding.searchBtn.setOnClickListener {
            val text = binding.searchEditText.editableText.toString();
            viewModel.getSearch(text).observe(viewLifecycleOwner) {

                when (it) {
                    is ApiResponse.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = it.data!!
                        binding.recyclerView.adapter = searchAdapter
                        searchAdapter.updateList(data)
                        binding.recyclerView.visibility = View.VISIBLE

                    }

                    is ApiResponse.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ApiResponse.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            it.errorMessage ?: "Something went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                searchAdapter.setOnItemClickListener(object : SearchAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        findNavController().navigate(R.id.action_searchFragment_to_movieFragment)
                    }
                })

            }
        }

    }

    private fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }


    override fun onResume() {
        super.onResume()
        binding.searchEditText.requestFocus()
        binding.searchEditText.showKeyboard()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}