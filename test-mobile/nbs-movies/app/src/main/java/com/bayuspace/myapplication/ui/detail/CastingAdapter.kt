package com.bayuspace.myapplication.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bayuspace.myapplication.BuildConfig
import com.bayuspace.myapplication.databinding.ItemCastingBinding
import com.bayuspace.myapplication.model.response.Casting
import com.bayuspace.myapplication.utils.loadImage

class CastingAdapter : RecyclerView.Adapter<CastingAdapter.ViewHolder>() {

    private val listItem = mutableListOf<Casting>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCastingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    fun setData(items: List<Casting>) {
        listItem.clear()
        listItem.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemCastingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Casting) {
            with(binding) {
                ivAvaCasting.loadImage("${BuildConfig.IMAGE_BASE_URL}${item.profilePath}")
                tvNameCasting.text = item.name
            }
        }
    }
}