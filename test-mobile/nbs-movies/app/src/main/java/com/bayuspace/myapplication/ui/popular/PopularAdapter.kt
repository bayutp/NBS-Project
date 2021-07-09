package com.bayuspace.myapplication.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bayuspace.myapplication.BuildConfig
import com.bayuspace.myapplication.databinding.ItemPopularBinding
import com.bayuspace.myapplication.model.response.Result
import com.bayuspace.myapplication.utils.loadImage

class PopularAdapter(private val listener: (Result) -> Unit) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    private val listItem = mutableListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(private val binding: ItemPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            with(binding) {
                ivPosterPopular.loadImage("${BuildConfig.IMAGE_BASE_URL}${item.backdropPath}")
                tvTitlePopular.text = item.title
                tvDescPoppular.text = item.overview
                itemView.setOnClickListener { listener(item) }
            }
        }
    }
}