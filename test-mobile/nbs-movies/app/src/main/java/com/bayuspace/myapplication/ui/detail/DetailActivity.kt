package com.bayuspace.myapplication.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayuspace.myapplication.BuildConfig
import com.bayuspace.myapplication.base.BaseActivity
import com.bayuspace.myapplication.databinding.ActivityDetailBinding
import com.bayuspace.myapplication.ui.home.HomeFragment.Companion.KEY_MOVIE
import com.bayuspace.myapplication.utils.gone
import com.bayuspace.myapplication.utils.loadImage
import com.bayuspace.myapplication.utils.showMsg
import com.bayuspace.myapplication.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapterCasting: CastingAdapter
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.apply {
            title = null
            setDisplayHomeAsUpEnabled(true)
        }

        val id = intent.getIntExtra(KEY_MOVIE, 0)
        adapterCasting = CastingAdapter()
        with(binding.rvCasting) {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterCasting
        }

        if (id != 0) {
            with(detailViewModel) {
                getDetailMovie(id)
                getMovieCastings(id)
            }
        }

    }

    override fun observeData() {
        with(detailViewModel) {
            observeDetailMovie().onResult {
                with(binding) {
                    val hours = it.runtime / 60
                    val minutes = it.runtime % 60
                    var genre = ""
                    it.genres.forEachIndexed { index, data ->
                        genre += if (index != it.genres.lastIndex) "${data.name} - " else data.name
                    }

                    ivMovieDetail.loadImage("${BuildConfig.IMAGE_BASE_URL}${it.posterPath}")
                    tvTitle.text = it.title
                    tvTimes.text = if (hours < 1) "$minutes m" else "$hours h $minutes m"
                    tvCategory.text = genre
                    tvDescription.text = it.overview
                }
            }
            observeMovieCastings().onResult {
                adapterCasting.setData(it.cast)
            }
            observeLoading().onResult {
                if (it) {
                    binding.apply {
                        pbDetail.visible()
                        btnFavorites.gone()
                        btnTrailer.gone()
                        tvCasting.gone()
                    }
                } else {
                    binding.apply {
                        pbDetail.gone()
                        btnFavorites.visible()
                        btnTrailer.visible()
                        tvCasting.visible()
                    }
                }
            }
            observeError().onResult {
                with(binding) {
                    btnFavorites.gone()
                    btnTrailer.gone()
                    tvCasting.gone()
                }
                showMsg(
                    it.msg ?: "Terjadi kesalahan! silahkan coba beberapa saat lagi :("
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}