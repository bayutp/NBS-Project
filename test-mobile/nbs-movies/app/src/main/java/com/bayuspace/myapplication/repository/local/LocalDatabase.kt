package com.bayuspace.myapplication.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bayuspace.myapplication.model.entity.MovieEntity
import com.bayuspace.myapplication.repository.local.dao.MovieDao

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao
}