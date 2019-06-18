package com.example.nabha.grocerylist.database

import android.arch.persistence.room.*
import com.example.nabha.grocerylist.model.Basket
import com.example.nabha.grocerylist.model.GroceryItem

@Dao
interface GroceryItemDao {

    @Query("SELECT * FROM item_list")
    fun getAll(): Array<GroceryItem>

    @Query("SELECT * FROM item_list where itemId LIKE  :id")
    fun findByItemById(id: Int): GroceryItem

    @Query("SELECT * FROM basket_list where basketId LIKE  :id")
    fun findByBasketId(id: Int): Basket

    @Query("SELECT COUNT(*) from item_list")
    fun countItems(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: GroceryItem)

    @Delete
    fun delete(item: GroceryItem)

    @Query("DELETE FROM item_list")
    fun nukeTable()
}