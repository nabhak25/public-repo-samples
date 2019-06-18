package com.example.ktodosample2.dao

import android.arch.persistence.room.*
import com.example.ktodosample2.entity.Item
import com.example.ktodosample2.entity.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM item where taskId == :id")
    fun getItemsById(id: Int): List<Item>

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)

}