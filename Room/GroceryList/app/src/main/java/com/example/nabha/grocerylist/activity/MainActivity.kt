package com.example.nabha.grocerylist.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.nabha.grocerylist.R
import com.example.nabha.grocerylist.adapter.BasketAdapter
import com.example.nabha.grocerylist.model.AppDatabase
import com.example.nabha.grocerylist.model.Basket
import com.example.nabha.grocerylist.utils.IdGenerator

/**
 * Created by nabha on 26/03/18.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener, BasketAdapter.IItemClickListener    {

    private lateinit var fabAddBasket: FloatingActionButton
    private lateinit var database : AppDatabase
    private lateinit var basketAdapter: BasketAdapter
    private lateinit var basketRecyclerView: RecyclerView
    private lateinit var placeholder : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = AppDatabase.getAppDatabase(this)
//        database.listDao().nukeTable()
        basketAdapter = BasketAdapter(database, this)

        placeholder = findViewById(R.id.textView)
        if (database.listDao().countBaskets() > 0)  placeholder.visibility = View.INVISIBLE
        fabAddBasket = findViewById(R.id.fab_add_basket)
        fabAddBasket.setOnClickListener(this)
        basketRecyclerView = findViewById(R.id.basket_recycler)
        basketRecyclerView.adapter = basketAdapter
        basketRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onClick(v: View?) {
        when (v) {
            fabAddBasket -> addNewBasket()
        }
    }

    private fun addNewBasket() {
        setupAlertForAddBasket()
    }

    private fun setupAlertForAddBasket() {
        val inflater = layoutInflater
        val alertLayout = inflater.inflate(R.layout.add_item_dialog, null)
        val itemEditText = alertLayout.findViewById<EditText>(R.id.item_editText)
        itemEditText.hint = getString(R.string.basket)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(resources.getString(R.string.add_basket))
        alertDialogBuilder.setView(alertLayout)
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setNegativeButton(resources.getString(R.string.cancel), { dialog, _ ->
            dialog.dismiss()
        })

        alertDialogBuilder.setPositiveButton(resources.getString(R.string.ok), { dialog, _ ->
            val basket = Basket()
            Log.i("TEST", "ID Main  : " + IdGenerator.currentId)
            basket.setBasketId(IdGenerator.getNextId())
            basket.setBasketName(validateBasketName(itemEditText.text.toString()))
            database.listDao().insert(basket)
            Toast.makeText(applicationContext, "Count: " + database.listDao().countBaskets(), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        })

        val dialog = alertDialogBuilder.create()
        dialog.show()
    }

    private fun validateBasketName(basketName : String) : String {
        Log.i("Test", "Value    $basketName")
        var name = ""
        if (basketName.equals("") || basketName.isEmpty())
            name = "Basket-${IdGenerator.currentId}"
        else
            name = basketName
        return name
    }

    override fun onItemClick(basketId : Int) {
        val intent = Intent(this, ItemsActivity::class.java)
        intent.putExtra("basket_id", basketId)
        startActivity(intent)
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }


}