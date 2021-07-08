package com.bayuspace.myapplication.ui.popular

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bayuspace.myapplication.R
import com.bayuspace.myapplication.databinding.FragmentPopularBinding


class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val popularAdapter = PopularAdapter()
        popularAdapter.setData(listOf(
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background))
        with(binding){
            svPopular.apply {
                val color = ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
                findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn).setColorFilter(
                    color,
                    PorterDuff.Mode.SRC_IN
                )
                findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon).setColorFilter(
                    color,
                    PorterDuff.Mode.SRC_IN
                )
                findViewById<EditText>(androidx.appcompat.R.id.search_src_text).apply {
                    setTextColor(color)
                    setHintTextColor(color)
                }
            }
            rvSearchResult.apply {
                setHasFixedSize(true)
                layoutManager =
                    GridLayoutManager(requireContext(),2)
                adapter = popularAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}