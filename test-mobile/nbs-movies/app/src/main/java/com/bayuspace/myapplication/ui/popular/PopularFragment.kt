package com.bayuspace.myapplication.ui.popular

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
import androidx.recyclerview.widget.GridLayoutManager
import com.bayuspace.myapplication.base.BaseFragment
import com.bayuspace.myapplication.databinding.FragmentPopularBinding
import com.bayuspace.myapplication.model.response.Result
import com.bayuspace.myapplication.ui.detail.DetailActivity
import com.bayuspace.myapplication.ui.home.HomeFragment.Companion.KEY_MOVIE
import com.bayuspace.myapplication.ui.home.HomeViewModel
import com.bayuspace.myapplication.utils.gone
import com.bayuspace.myapplication.utils.showMsg
import com.bayuspace.myapplication.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularFragment : BaseFragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var popularAdapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        popularAdapter = PopularAdapter {
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
                    GridLayoutManager(requireContext(), 2)
                adapter = popularAdapter
            }
            svPopular.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    p0?.let {
                        homeViewModel.getSearchMovies("%$it%")
                    }
                    resetSearchView()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

            })
        }

        homeViewModel.getDiscoverMovies()
    }

    private fun resetSearchView() {
        binding.svPopular.apply {
            setQuery("", false)
            clearFocus()
        }
    }

    override fun observeData() {
        with(homeViewModel) {
            observeDiscoverMovies().onResult {
                popularAdapter.setData(it)
            }
            observeLoading().onResult {
                if (it) binding.pbPopular.visible()
                else binding.pbPopular.gone()
            }
            observeError().onResult {
                requireContext().showMsg(
                    it.msg ?: "Terjadi kesalahan! silahkan coba beberapa saat lagi :("
                )
            }
            observeSearchFavMovies().onResult { data ->
                popularAdapter.setData(data.flatMap { listOf(Result.mapToMovieResponse(it)) })
            }
            observeEmptyData().onResult {
                with(binding) {
                    if (it) rvSearchResult.gone() else rvSearchResult.visible()
                    if (it) tvEmpty.visible() else tvEmpty.gone()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        resetSearchView()
        homeViewModel.getDiscoverMovies()
    }
}