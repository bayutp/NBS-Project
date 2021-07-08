package com.bayuspace.myapplication.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

abstract class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideToolbar(true)
        onViewReady(savedInstanceState)
        observeData()
    }

    protected abstract fun onViewReady(savedInstanceState: Bundle?)
    protected abstract fun observeData()

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseFragment, { data -> data?.let(action) })
    }

    protected fun hideToolbar(isHide: Boolean) {
        (activity as AppCompatActivity).supportActionBar?.let {
            if (isHide) it.hide() else it.show()
        }
    }
}