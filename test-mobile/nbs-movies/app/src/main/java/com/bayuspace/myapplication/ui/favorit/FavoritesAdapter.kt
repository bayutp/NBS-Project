package com.bayuspace.myapplication.ui.favorit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bayuspace.myapplication.BuildConfig
import com.bayuspace.myapplication.databinding.ItemFavoritesBinding
import com.bayuspace.myapplication.model.entity.MovieEntity
import com.bayuspace.myapplication.utils.formatDate
import com.bayuspace.myapplication.utils.loadImage

class FavoritesAdapter(private val listener: (MovieEntity) -> Unit, private val deleteListener: (MovieEntity) -> Unit) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private val listItem = mutableListOf<MovieEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    fun setData(items: List<MovieEntity>) {
        listItem.clear()
        listItem.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieEntity) {
            with(binding) {
                ivPosterFavorite.loadImage("${BuildConfig.IMAGE_BASE_URL}${item.posterPath}")
                tvTitleFavorite.text = item.title
                tvReleaseFavorite.text = item.releaseDate.formatDate("yyyy-MM-dd", "yyyy")
                var genre = ""
                item.genre.forEachIndexed { index, dataGenre ->
                    genre += if (index != item.genre.lastIndex) "${dataGenre.name}, " else dataGenre.name
                }
                tvCategoryFavorite.text = genre
                ivFavorite.setOnClickListener { deleteListener(item) }
                itemView.setOnClickListener { listener(item) }
            }
        }
    }
}