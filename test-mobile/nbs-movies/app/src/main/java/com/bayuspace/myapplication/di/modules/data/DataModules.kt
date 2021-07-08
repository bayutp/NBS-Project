package com.bayuspace.myapplication.di.modules.data

import com.bayuspace.myapplication.di.modules.BaseModule
import com.bayuspace.myapplication.repository.DataRepository
import com.bayuspace.myapplication.repository.local.LocalDataSource
import com.bayuspace.myapplication.repository.network.RemoteDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModules : BaseModule {
    override val modules: List<Module>
        get() = listOf(dataModule)

    private val dataModule = module {
        single { RemoteDataSource(get()) }
        single { LocalDataSource(get()) }
        single { DataRepository(get(), get()) }
    }
}