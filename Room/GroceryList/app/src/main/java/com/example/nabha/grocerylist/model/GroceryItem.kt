package com.example.nabha.grocerylist.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

/**
 * Created by nabha on 26/03/18.
 */
@Entity(tableName = "item_list", foreignKeys =
(arrayOf(ForeignKey(entity = Basket::class, parentColumns = arrayOf("basketId"), childColumns = arrayOf("basket_id") , onDelete = CASCADE))))

class GroceryItem {

    @PrimaryKey(autoGenerate = false)
    private var itemId : Int = 0

    @ColumnInfo(name = "item_name")
    private var itemName : String? = null

    @ColumnInfo(name = "item_quantity")
    private var itemQuantity : String? = null

    @ColumnInfo(name = "basket_id")
    private var basketId : Int = 0

    fun getItemId() : Int {
        return itemId
    }

    fun setItemId(id : Int) {
        itemId = id
    }

    fun getItemName() : String? {
        return itemName
    }

    fun setItemName(name : String?) {
        itemName = name
    }

    fun getItemQuantity() : String? {
        return itemQuantity
    }

    fun setItemQuantity(quantity : String?) {
        itemQuantity = quantity
    }

    fun getBasketId() : Int {
        return basketId
    }

    fun setBasketId(id : Int) {
        basketId = id
    }

}