package com.bayuspace.myapplication.di.modules.room

import android.app.Application
import androidx.room.Room
import com.bayuspace.myapplication.di.modules.BaseModule
import com.bayuspace.myapplication.repository.local.LocalDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

object RoomModules : BaseModule {
    override val modules: List<Module>
        get() = listOf(roomModule)

    private val roomModule = module {
        single { provideDatabase(androidApplication()) }
        single { provideMovieDao(get()) }
        single { provideMoviePopDao(get()) }
    }

    private fun provideDatabase(application: Application) =
        Room.databaseBuilder(application, LocalDatabase::class.java, "local-db")
            .addMigrations()
            .fallbackToDestructiveMigration()
            .build()

    private fun provideMovieDao(db: LocalDatabase) = db.movieDao()
    private fun provideMoviePopDao(db: LocalDatabase) = db.moviePopularDao()
}