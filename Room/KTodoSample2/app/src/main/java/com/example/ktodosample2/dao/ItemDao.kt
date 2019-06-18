package com.example.ktodosample2.dao

import android.arch.persistence.room.*
import com.example.ktodosample2.entity.Item

@Dao
interface ItemDao {

    @Query("SELECT * FROM item")
    fun getAll(): List<Item>

    @Insert
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)

    @Update
    fun update(item: Item)
}