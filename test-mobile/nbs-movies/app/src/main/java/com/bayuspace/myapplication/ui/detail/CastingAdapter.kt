package com.bayuspace.myapplication.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bayuspace.myapplication.databinding.ItemCastingBinding

class CastingAdapter : RecyclerView.Adapter<CastingAdapter.ViewHolder>() {

    private val listItem = mutableListOf<Int>()

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

    fun setData(items: List<Int>) {
        listItem.clear()
        listItem.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemCastingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            with(binding) {
                ivAvaCasting.setImageResource(item)
                tvNameCasting.text = "Ahmad Saefudin"
            }
        }
    }
}