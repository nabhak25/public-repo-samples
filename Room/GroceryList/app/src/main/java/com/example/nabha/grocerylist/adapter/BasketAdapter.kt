package com.example.nabha.grocerylist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nabha.grocerylist.R
import com.example.nabha.grocerylist.model.AppDatabase
import com.example.nabha.grocerylist.model.Basket

class BasketAdapter(var appDatabase: AppDatabase, var itemClickListener: IItemClickListener) : RecyclerView.Adapter<BasketAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val rootView = LayoutInflater.from(parent?.context).inflate(R.layout.single_basket, parent, false)
        return ItemViewHolder(rootView)
    }

    override fun getItemCount(): Int {
       return appDatabase.listDao().countBaskets()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val basket : Basket? = appDatabase.listDao().findByBasketId(position)
        holder.basketName?.text = basket?.getBasketName()
        holder.basketName?.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
    }

    class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
         var basketName = itemView?.findViewById<TextView>(R.id.basket_name)
    }

    interface IItemClickListener {
        fun onItemClick(basketId : Int)
    }

}