package com.example.nabha.grocerylist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.nabha.grocerylist.R
import android.widget.Toast
import com.example.nabha.grocerylist.adapter.ItemAdapter
import com.example.nabha.grocerylist.model.AppDatabase
import com.example.nabha.grocerylist.model.GroceryItem
import com.example.nabha.grocerylist.utils.ItemIdGenerator


class ItemsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var fabAddItem: FloatingActionButton
    private lateinit var database : AppDatabase
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var placeholder : TextView

    private var basketId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        validateIntent()

        database = AppDatabase.getAppDatabase(this)
//        database.itemListDao().nukeTable()
        itemAdapter = ItemAdapter(database)

        placeholder = findViewById(R.id.textView)
        if (database.itemListDao().countItems() > 0)  placeholder.visibility = View.INVISIBLE
        fabAddItem = findViewById(R.id.fab_add_item)
        fabAddItem.setOnClickListener(this)
        itemRecyclerView = findViewById(R.id.item_recycler)
        itemRecyclerView.adapter = itemAdapter
        itemRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onClick(v: View?) {
        when (v) {
            fabAddItem -> addNewItem()
        }
    }

    private fun addNewItem() {
        setupAlertForAddItem()
    }

    private fun validateIntent() {
        if (intent.extras != null) {
            basketId = intent.getIntExtra("basket_id", 0)
        }
    }

    private fun setupAlertForAddItem() {
        val inflater = layoutInflater
        val alertLayout = inflater.inflate(R.layout.add_item_dialog, null)
        val itemEditText = alertLayout.findViewById<EditText>(R.id.item_editText)
        val quantityEditText = alertLayout.findViewById<EditText>(R.id.quantity_editText)
        quantityEditText.visibility = View.VISIBLE

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(resources.getString(R.string.add_item))
        alertDialogBuilder.setView(alertLayout)
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setNegativeButton(resources.getString(R.string.cancel), { dialog, _ ->
            dialog.dismiss()
        })

        alertDialogBuilder.setPositiveButton(resources.getString(R.string.ok), { dialog, _ ->
            val itemName = itemEditText.text.toString()
            val itemQuantity = quantityEditText.text.toString()
            if (validateItemName(itemName, itemQuantity)) {
                val groceryItem = GroceryItem()
                groceryItem.setItemId(ItemIdGenerator.getNextId())
                groceryItem.setItemName(itemName)
                groceryItem.setItemQuantity(itemQuantity)
                groceryItem.setBasketId(basketId)
                database.itemListDao().insert(groceryItem)
                Toast.makeText(applicationContext, "Count: " + database.itemListDao().countItems(), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })

        val dialog = alertDialogBuilder.create()
        dialog.show()
    }

    private fun validateItemName(itemName : String, quantity : String) : Boolean {
        if (itemName.equals("") || quantity.equals("")) {
            Toast.makeText(applicationContext, "Please enter a valid item/quantity!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }
}
