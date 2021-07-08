package com.bayuspace.myapplication.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bayuspace.myapplication.R
import com.bayuspace.myapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}