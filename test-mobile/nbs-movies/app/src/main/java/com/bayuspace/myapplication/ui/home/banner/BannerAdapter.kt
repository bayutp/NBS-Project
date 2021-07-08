package com.bayuspace.myapplication.ui.home.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bayuspace.myapplication.BuildConfig
import com.bayuspace.myapplication.databinding.FragmentBannerBinding
import com.bayuspace.myapplication.utils.loadImage

class BannerAdapter(private val listBanner: List<String>) : PagerAdapter() {
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
        binding.ivMovieBanner.loadImage("${BuildConfig.IMAGE_BASE_URL}${listBanner[position]}")
        container.addView(binding.root)
        return binding.root
    }
}