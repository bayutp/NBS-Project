package com.bayuspace.myapplication.di.modules.viewmodel

import com.bayuspace.myapplication.di.modules.BaseModule
import com.bayuspace.myapplication.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ViewModelModules : BaseModule{
    override val modules: List<Module>
        get() = listOf(viewModelModule)

    private val viewModelModule = module{
        viewModel { HomeViewModel(get()) }
    }

}