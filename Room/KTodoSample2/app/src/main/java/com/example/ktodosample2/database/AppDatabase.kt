package com.example.ktodosample2.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.ktodosample2.dao.ItemDao
import com.example.ktodosample2.dao.TaskDao
import com.example.ktodosample2.entity.Item
import com.example.ktodosample2.entity.Task

@Database(entities = [Task::class, Item::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun itemDao(): ItemDao

}