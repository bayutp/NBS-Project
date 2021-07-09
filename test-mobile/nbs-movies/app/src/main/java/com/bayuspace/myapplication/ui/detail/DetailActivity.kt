package com.bayuspace.myapplication.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayuspace.myapplication.BuildConfig
import com.bayuspace.myapplication.R
import com.bayuspace.myapplication.base.BaseActivity
import com.bayuspace.myapplication.databinding.ActivityDetailBinding
import com.bayuspace.myapplication.model.entity.MovieEntity
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
    private var isBookmarked = false

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
                checkBookmarked(id)
            }
        }

    }

    override fun observeData() {
        with(detailViewModel) {
            observeDetailMovie().onResult { data ->
                with(binding) {
                    val hours = data.runtime / 60
                    val minutes = data.runtime % 60
                    var genre = ""
                    data.genres.forEachIndexed { index, dataGenre ->
                        genre += if (index != data.genres.lastIndex) "${dataGenre.name} - " else dataGenre.name
                    }

                    ivMovieDetail.loadImage("${BuildConfig.IMAGE_BASE_URL}${data.posterPath}")
                    tvTitle.text = data.title
                    tvTimes.text = if (hours < 1) "$minutes m" else "$hours h $minutes m"
                    tvCategory.text = genre
                    tvDescription.text = data.overview

                    btnFavorites.setOnClickListener {
                        detailViewModel.setBookmarked(
                            listOf(
                                MovieEntity.mapToMovieEntity(
                                    data,
                                    !isBookmarked
                                )
                            ), !isBookmarked
                        )
                    }
                }
            }
            observeMovieCastings().onResult {
                adapterCasting.setData(it.cast)
            }
            observeSetBookmarked().onResult {
                isBookmarked = it
                changeButton(isBookmarked)
                showMsg("${if (isBookmarked) "add to" else "remove from"} favorites")
            }
            observeCheckBookmarked().onResult {
                isBookmarked = it
                changeButton(it)
            }
            observeLoading().onResult {
                binding.apply {
                    if (it) {
                        pbDetail.visible()
                        btnFavorites.gone()
                        btnTrailer.gone()
                        tvCasting.gone()
                    } else {
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

    private fun changeButton(state: Boolean) {
        with(binding.btnFavorites) {
            if (state) {
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_delete
                    ), null, null, null
                )
                text = getString(R.string.remove_from_fav)
                backgroundTintList =
                    ContextCompat.getColorStateList(
                        this@DetailActivity,
                        android.R.color.holo_red_dark
                    )
            } else {
                setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_add
                    ), null, null, null
                )
                text = getString(R.string.add_to_favorite)
                backgroundTintList =
                    ContextCompat.getColorStateList(this@DetailActivity, R.color.blue)
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else super.onOptionsItemSelected(item)
    }
}