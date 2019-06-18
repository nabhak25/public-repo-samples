package com.example.ktodosample2.activity

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.ktodosample2.R
import com.example.ktodosample2.adapter.ItemsAdapter
import com.example.ktodosample2.database.DatabaseClient
import com.example.ktodosample2.entity.Item
import com.example.ktodosample2.entity.Task
import com.example.ktodosample2.interfaces.ItemListener
import com.example.ktodosample2.model.ItemModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.alert_dialog.view.*


class DetailActivity : AppCompatActivity(), ItemListener {

    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        itemRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        task = intent.getSerializableExtra("task") as Task
        loadSubtasks(task)

        addSubtask.setOnClickListener { saveSubtaskForTask(task) }
    }

    private fun saveSubtaskForTask(task: Task) {
        class SaveSubTask : AsyncTask<Void, Void, Boolean>() {
            override fun doInBackground(vararg p0: Void?): Boolean {
                val newItem = Item()
                val enteredString = itemEditText.text.toString().trim()
                return if (enteredString.isNotBlank() || enteredString.isNotEmpty()) {
                    newItem.itemDescription = enteredString
                    newItem.taskId = task.id

                    DatabaseClient.getInstance(this@DetailActivity)
                        .getAppDatabase()
                        .itemDao()
                        .insert(newItem)
                    true
                } else {
                    runOnUiThread { Toast.makeText(applicationContext, "Empty task!!", Toast.LENGTH_LONG).show() }
                    false
                }
            }

            override fun onPostExecute(result: Boolean) {
                super.onPostExecute(result)
                itemEditText.setText("")
                loadSubtasks(task)
                if (result) Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
            }
        }

        SaveSubTask().execute()
    }

    private fun loadSubtasks(task: Task) {
        class GetTasks : AsyncTask<Void, Void, List<Item>>() {

            override fun doInBackground(vararg p0: Void?): List<Item> {
                return DatabaseClient.getInstance(this@DetailActivity)
                    .getAppDatabase()
                    .taskDao()
                    .getItemsById(task.id)
            }

            override fun onPostExecute(result: List<Item>?) {
                super.onPostExecute(result)
                val adapter = result?.let { ItemsAdapter(it, this@DetailActivity) }
                itemRecycler.adapter = adapter
            }

        }

        GetTasks().execute()
    }

    override fun onDelete(itemModel: ItemModel) {
        class DeleteTask : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                DatabaseClient.getInstance(this@DetailActivity)
                    .getAppDatabase()
                    .itemDao()
                    .delete(itemModel.item)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                loadSubtasks(task = task)
                Toast.makeText(applicationContext, "${itemModel.item.itemDescription} deleted!", Toast.LENGTH_LONG).show()
            }
        }

        DeleteTask().execute()
    }

    override fun onUpdate(itemModel: ItemModel) {
        val alertLayout = layoutInflater.inflate(R.layout.alert_dialog, null)
        alertLayout.taskEditText.setText(itemModel.item.itemDescription)

        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setView(alertLayout)

        val alert = alertBuilder.create()
        alert.show()

        alertLayout.updateTask.setOnClickListener {
            val enteredString = alertLayout.taskEditText.text.toString().trim()
            if (enteredString.isNotEmpty() || enteredString.isNotBlank()) {
                class UpdateTask : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg p0: Void?): Void? {

                        itemModel.item.itemDescription = enteredString

                        DatabaseClient.getInstance(this@DetailActivity)
                            .getAppDatabase()
                            .itemDao()
                            .update(itemModel.item)
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        loadSubtasks(task = task)
                        alert.dismiss()
                        Toast.makeText(applicationContext, "Updated Task!", Toast.LENGTH_LONG).show()
                    }
                }

                UpdateTask().execute()
            } else Toast.makeText(applicationContext, "Empty task!!", Toast.LENGTH_LONG).show()
        }

    }

}
