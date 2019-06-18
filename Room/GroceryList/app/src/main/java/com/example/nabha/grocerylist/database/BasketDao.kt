package com.example.nabha.grocerylist.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.nabha.grocerylist.model.Basket
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE


/**
 * Created by nabha on 26/03/18.
 */

@Dao
interface BasketDao {

    @Query("SELECT * FROM basket_list")
    fun getAll(): Array<Basket>

    @Query("SELECT * FROM basket_list where basketId LIKE  :id")
    fun findByBasketId(id: Int): Basket

    @Query("SELECT COUNT(*) from basket_list")
    fun countBaskets(): Int

    @Insert(onConflict = IGNORE)
    fun insert(basket: Basket)

    @Delete
    fun delete(basket: Basket)

    @Query("DELETE FROM basket_list")
    fun nukeTable()
}