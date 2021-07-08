package com.bayuspace.myapplication.di.modules

import com.bayuspace.myapplication.di.modules.data.DataModules
import com.bayuspace.myapplication.di.modules.network.NetworkModules
import com.bayuspace.myapplication.di.modules.room.RoomModules
import com.bayuspace.myapplication.di.modules.viewmodel.ViewModelModules
import org.koin.core.module.Module

object DepsModuleProvider {
    val modules: List<Module>
        get() = mutableListOf<Module>().apply {
            addAll(DataModules.modules)
            addAll(NetworkModules.modules)
            addAll(RoomModules.modules)
            addAll(ViewModelModules.modules)
        }

}