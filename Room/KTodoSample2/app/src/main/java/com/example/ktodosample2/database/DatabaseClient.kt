package com.example.ktodosample2.database

import android.arch.persistence.room.Room
import android.content.Context

object DatabaseClient {

    private lateinit var appDatabase: AppDatabase

    fun getInstance(context: Context): DatabaseClient {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "TodoList").build()
        return this
    }

    fun getAppDatabase(): AppDatabase {
        return appDatabase
    }
}