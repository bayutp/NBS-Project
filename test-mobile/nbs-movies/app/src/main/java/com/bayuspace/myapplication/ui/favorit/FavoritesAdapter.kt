package com.bayuspace.myapplication.ui.favorit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bayuspace.myapplication.databinding.ItemFavoritesBinding

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private val listItem = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            with(binding) {
                ivPosterFavorite.setImageResource(item)
                tvTitleFavorite.text = "Spiderman"
                tvReleaseFavorite.text = "2020"
                tvCategoryFavorite.text = "Action"
            }
        }
    }
}