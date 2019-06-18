package com.example.ktodosample2.interfaces

import com.example.ktodosample2.model.ItemModel

interface ItemListener {

    fun onUpdate(itemModel: ItemModel)

    fun onDelete(itemModel: ItemModel)
}