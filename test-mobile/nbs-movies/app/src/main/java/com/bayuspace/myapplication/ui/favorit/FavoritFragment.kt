package com.bayuspace.myapplication.ui.favorit

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayuspace.myapplication.base.BaseFragment
import com.bayuspace.myapplication.databinding.FragmentPopularBinding
import com.bayuspace.myapplication.ui.detail.DetailActivity
import com.bayuspace.myapplication.ui.home.HomeFragment.Companion.KEY_MOVIE
import com.bayuspace.myapplication.utils.gone
import com.bayuspace.myapplication.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritFragment : BaseFragment() {
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private lateinit var favoriteAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        favoriteAdapter = FavoritesAdapter {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(KEY_MOVIE, it.id)
            startActivity(intent)
        }
        with(binding) {
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
                    LinearLayoutManager(requireContext())
                adapter = favoriteAdapter
            }
            svPopular.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    p0?.let {
                        favoritesViewModel.getSearchFavMovies(it)
                    }
                    resetSearchView()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

            })
        }
        favoritesViewModel.getFavMovies()
    }

    private fun resetSearchView(){
        binding.svPopular.apply {
            setQuery("",false)
            clearFocus()
        }
    }

    override fun observeData() {
        with(favoritesViewModel) {
            observeGetFavMovies().onResult {
                favoriteAdapter.setData(it)
            }

            observeSearchFavMovies().onResult {
                favoriteAdapter.setData(it)
            }

            observeEmptyData().onResult {
                with(binding) {
                    if (it) rvSearchResult.gone() else rvSearchResult.visible()
                    if (it) tvEmpty.visible() else tvEmpty.gone()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        resetSearchView()
        binding.svPopular.setQuery("",false)
        favoritesViewModel.getFavMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}