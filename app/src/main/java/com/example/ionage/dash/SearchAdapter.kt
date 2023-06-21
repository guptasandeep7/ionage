package com.example.ionage.dash

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ionage.databinding.ItemViewBinding
import com.example.ionage.model.SearchResult
import com.example.ionage.model.SearchResults

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var searchList = mutableListOf<SearchResult>();

    fun updateList(searchResults: SearchResults) {
        searchList = searchResults.Search as MutableList<SearchResult>
        notifyDataSetChanged()
    }

    private var mlistner: onItemClickListener? = null

    interface onItemClickListener {
        fun onItemClick(MovieId: String)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistner = listener
    }

    class ViewHolder(val binding: ItemViewBinding, listener: onItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: SearchResult) {
            binding.movieImage.load(movie.Poster)
            binding.title.text = movie.Title
            binding.year.text = movie.Year
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, mlistner!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = searchList[holder.adapterPosition]
        holder.bind(movie)

        holder.itemView.setOnClickListener {
            mlistner?.onItemClick(movie.imdbID)
        }

    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}