package com.bayuspace.myapplication.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bayuspace.myapplication.databinding.ItemPopularBinding

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    private val listItem = mutableListOf<Int>()

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

    fun setData(items: List<Int>) {
        listItem.clear()
        listItem.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            with(binding) {
                ivPosterPopular.setImageResource(item)
                tvTitlePopular.text = "Spiderman"
                tvDescPoppular.text = "Spider-Man is a superhero created by writer-editor Stan Lee and writer-artist Steve Ditko. He first appeared in the anthology comic book Amazing Fantasy #15 in the Silver Age of Comic Books."
            }
        }
    }
}