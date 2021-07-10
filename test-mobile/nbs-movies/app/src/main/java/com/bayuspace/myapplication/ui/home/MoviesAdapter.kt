package com.bayuspace.myapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bayuspace.myapplication.BuildConfig
import com.bayuspace.myapplication.databinding.ItemMoviesBinding
import com.bayuspace.myapplication.model.response.Result
import com.bayuspace.myapplication.utils.loadImage

class MoviesAdapter(private val listener: (Result) -> Unit) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val listItem = mutableListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    fun setData(items: List<Result>) {
        listItem.clear()
        listItem.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.ivPosterMovies.loadImage("${BuildConfig.IMAGE_BASE_URL}${item.posterPath}")
            itemView.setOnClickListener { listener(item) }
        }
    }
}