package com.bayuspace.myapplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bayuspace.myapplication.R
import com.bayuspace.myapplication.databinding.FragmentHomeBinding
import com.bayuspace.myapplication.ui.home.banner.BannerAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarHome.apply {
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.menu_notif) Toast.makeText(
                    requireContext(),
                    R.string.coming_soon,
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
        }

        setupBanner()
    }

    private fun setupBanner() {
        binding.vpBanner.adapter =
            BannerAdapter(listOf(R.drawable.ic_logo, R.drawable.ic_logo, R.drawable.ic_logo))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}