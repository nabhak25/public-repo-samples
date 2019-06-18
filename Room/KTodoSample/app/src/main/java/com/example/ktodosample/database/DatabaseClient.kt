package com.example.ktodosample.database

import android.content.Context
import android.arch.persistence.room.Room



object DatabaseClient{

    private lateinit var appDatabase: AppDatabase

    fun getInstance(context: Context): DatabaseClient {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "MyToDos").build()
        return this
    }

    fun getAppDatabase(): AppDatabase = appDatabase

}