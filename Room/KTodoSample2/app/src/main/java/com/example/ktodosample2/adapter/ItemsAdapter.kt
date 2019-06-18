package com.example.ktodosample2.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.example.ktodosample2.R
import com.example.ktodosample2.databinding.SingleDetailItemBinding
import com.example.ktodosample2.entity.Item
import com.example.ktodosample2.interfaces.ItemListener
import com.example.ktodosample2.model.ItemModel
import kotlinx.android.synthetic.main.single_task_item.view.*

class ItemsAdapter(
    private val itemsList: List<Item>,
    private val listener: ItemListener
): RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val binding = SingleDetailItemBinding.inflate(inflater)
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(p0: ItemViewHolder, p1: Int) = p0.bind(itemsList[p1])


    inner class ItemViewHolder(private val binding: SingleDetailItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.itemModel = ItemModel(item)
            binding.listener = listener
        }

        init {
            binding.root.imageMenu.setOnClickListener {
                val popupMenu = PopupMenu(binding.root.context, binding.root.imageMenu)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.update -> {
                            listener.onUpdate(ItemModel(itemsList[adapterPosition]))
                        }

                        R.id.delete -> {
                            listener.onDelete(ItemModel(itemsList[adapterPosition]))
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }
    }
}