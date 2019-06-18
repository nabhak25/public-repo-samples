package com.example.nabha.grocerylist.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by nabha on 26/03/18.
 */
@Entity(tableName = "basket_list")
class Basket {

    @PrimaryKey(autoGenerate = false)
    private var basketId : Int = 0

    @ColumnInfo(name = "basket_name")
    private var basketName : String? = null

    fun getBasketId() : Int {
        return basketId
    }

    fun setBasketId(id : Int) {
        basketId = id
    }

    fun getBasketName() : String? {
        return basketName
    }

    fun setBasketName(name : String?) {
        basketName = name
    }

}