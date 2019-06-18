package com.example.ktodosample.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.ktodosample.dao.TaskDao
import com.example.ktodosample.entity.Task

@Database(entities = [Task::class], version = 1)

abstract class AppDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

}