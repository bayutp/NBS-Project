package com.bayuspace.myapplication.ui.home.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bayuspace.myapplication.databinding.FragmentBannerBinding

class BannerAdapter(private val listBanner: List<Int>) : PagerAdapter() {
    override fun getCount(): Int {
        return listBanner.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            FragmentBannerBinding.inflate(LayoutInflater.from(container.context), container, false)
        binding.ivMovieBanner.setImageResource(listBanner[position])
        container.addView(binding.root)
        return binding.root
    }
}