package com.example.nabha.grocerylist.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nabha.grocerylist.R
import com.example.nabha.grocerylist.model.AppDatabase

class ItemAdapter(var appDatabase: AppDatabase) : RecyclerView.Adapter<ItemAdapter.GroceryItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GroceryItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.single_grocery_item, parent, false)
        return GroceryItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appDatabase.itemListDao().countItems()
    }

    override fun onBindViewHolder(holder: GroceryItemViewHolder?, position: Int) {
        Log.i("TEST", "ID: " + appDatabase.itemListDao().findByItemById(position).getBasketId())

        val basket = appDatabase.itemListDao().findByBasketId(appDatabase.itemListDao().findByItemById(position).getBasketId())
        Log.i("TEST", "Basket name  " + basket.getBasketName())

        holder?.itemName?.text = appDatabase.itemListDao().findByItemById(position).getItemName()
        holder?.itemQuantity?.text = appDatabase.itemListDao().findByItemById(position).getItemQuantity()
    }

    class GroceryItemViewHolder(itemView : View?) : RecyclerView.ViewHolder(itemView) {
        var itemName = itemView?.findViewById<TextView>(R.id.item_name)
        var itemQuantity = itemView?.findViewById<TextView>(R.id.item_quantity)
    }

}