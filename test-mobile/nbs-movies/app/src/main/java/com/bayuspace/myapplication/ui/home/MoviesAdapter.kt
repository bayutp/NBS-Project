package com.bayuspace.myapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bayuspace.myapplication.databinding.ItemMoviesBinding

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val listItem = mutableListOf<Int>()

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

    fun setData(items: List<Int>) {
        listItem.clear()
        listItem.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            binding.ivPosterMovies.setImageResource(item)
        }
    }
}