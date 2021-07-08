package com.bayuspace.myapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onViewReady(savedInstanceState)
        observeData()
    }

    protected abstract fun onViewReady(savedInstanceState: Bundle?)
    protected abstract fun observeData()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseActivity, { data -> data?.let(action) })
    }
}