package com.example.ionage.dash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ionage.R
import com.example.ionage.databinding.FragmentSearchBinding
import com.example.ionage.util.ApiResponse
import com.example.ionage.viewmodel.SearchViewModel

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
            search()
        }

        binding.plusBtn.setOnClickListener {
            if(viewModel.pageno<100) {
                viewModel.pageno++
                search()
            }
        }

        binding.minusBtn.setOnClickListener {
            if(viewModel.pageno>1){
                viewModel.pageno--
                search()
            }
        }

        viewModel._searchData.observe(viewLifecycleOwner){
            when (it) {
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.linearLayout.visibility = View.VISIBLE
                    val data = it.data!!
                    binding.recyclerView.adapter = searchAdapter
                    searchAdapter.updateList(data)
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.pageNo.text = "Page ${viewModel.pageno}"
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

        }

            searchAdapter.setOnItemClickListener(object : SearchAdapter.onItemClickListener {
                override fun onItemClick(MovieId: String) {
                    val bundle = Bundle()
                    bundle.putString("MovieID", MovieId)
                    findNavController().navigate(R.id.action_searchFragment_to_movieFragment, bundle)
                }
            })

    }

    private fun search() {
        val text = binding.searchEditText.editableText.toString();
        viewModel.getSearch(text).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.linearLayout.visibility = View.VISIBLE
                    val data = it.data!!
                    binding.recyclerView.adapter = searchAdapter
                    searchAdapter.updateList(data)
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.pageNo.text = "Page ${viewModel.pageno}"
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