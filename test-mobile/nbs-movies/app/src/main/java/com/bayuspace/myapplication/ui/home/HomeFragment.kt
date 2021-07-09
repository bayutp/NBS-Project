package com.bayuspace.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayuspace.myapplication.R
import com.bayuspace.myapplication.base.BaseFragment
import com.bayuspace.myapplication.databinding.FragmentHomeBinding
import com.bayuspace.myapplication.ui.detail.DetailActivity
import com.bayuspace.myapplication.ui.home.banner.BannerAdapter
import com.bayuspace.myapplication.utils.gone
import com.bayuspace.myapplication.utils.showMsg
import com.bayuspace.myapplication.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var upcomingMovieAdapter: MoviesAdapter
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        moviesAdapter = MoviesAdapter { moveToDetail(it.id) }
        upcomingMovieAdapter = MoviesAdapter { moveToDetail(it.id) }
        with(binding) {
            toolbarHome.apply {
                inflateMenu(R.menu.home_menu)
                setOnMenuItemClickListener {
                    if (it.itemId == R.id.menu_notif) requireContext().showMsg(getString(R.string.coming_soon))
                    true
                }
            }
            rvPopular.apply {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = moviesAdapter
            }
            rvComingSoon.apply {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = upcomingMovieAdapter
            }

        }

        homeViewModel.apply {
            getDiscoverMovies()
            getUpcomingMovies()
        }
    }

    private fun moveToDetail(id: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(KEY_MOVIE, id)
        startActivity(intent)
    }

    override fun observeData() {
        with(homeViewModel) {
            observeDiscoverMovies().onResult {
                val listPoster = mutableListOf<String>()
                it.results.take(5).forEach { data ->
                    data.backdropPath?.let { poster -> listPoster.add(poster) }
                }
                binding.vpBanner.adapter = BannerAdapter(listPoster.toList())
                moviesAdapter.setData(it.results)
            }
            observeUpcomingMovies().onResult {
                upcomingMovieAdapter.setData(it.results)
            }
            observeLoading().onResult {
                if (it) {
                    binding.apply {
                        pbHome.visible()
                        containerHome.gone()
                    }
                } else {
                    binding.apply {
                        pbHome.gone()
                        containerHome.visible()
                    }
                }
            }
            observeError().onResult {
                binding.containerHome.gone()
                requireContext().showMsg(
                    it.msg ?: "Terjadi kesalahan! silahkan coba beberapa saat lagi :("
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_MOVIE = "MOVIE_EXTRA"
    }
}