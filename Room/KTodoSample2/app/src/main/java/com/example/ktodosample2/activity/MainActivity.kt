package com.example.ktodosample2.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.ktodosample2.R
import com.example.ktodosample2.adapter.TaskAdapter
import com.example.ktodosample2.database.DatabaseClient
import com.example.ktodosample2.entity.Task
import com.example.ktodosample2.interfaces.TaskListener
import com.example.ktodosample2.model.TaskModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alert_dialog.view.*

class MainActivity : AppCompatActivity(), TaskListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        fetchTasks()

        addTask.setOnClickListener { saveTask() }
    }

    private fun saveTask() {
        class SaveTask : AsyncTask<Void, Void, Boolean>() {
            override fun doInBackground(vararg p0: Void?): Boolean {
                val newTask = Task()
                val enteredString = taskEditText.text.toString().trim()
                return if (enteredString.isNotBlank() || enteredString.isNotEmpty()) {
                    newTask.taskDescription = enteredString
                    DatabaseClient.getInstance(this@MainActivity)
                        .getAppDatabase()
                        .taskDao()
                        .insert(newTask)
                    true
                } else {
                    runOnUiThread { Toast.makeText(applicationContext, "Empty task!!", Toast.LENGTH_LONG).show() }
                    false
                }
            }

            override fun onPostExecute(result: Boolean) {
                super.onPostExecute(result)
                taskEditText.setText("")
                fetchTasks()
                if (result) Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
            }
        }

        SaveTask().execute()
    }

    private fun fetchTasks() {
        class GetTasks : AsyncTask<Void, Void, List<Task>>() {

            override fun doInBackground(vararg p0: Void?): List<Task> {
                return DatabaseClient.getInstance(this@MainActivity)
                    .getAppDatabase()
                    .taskDao()
                    .getAll()
            }

            override fun onPostExecute(result: List<Task>?) {
                super.onPostExecute(result)
                val taskAdapter = result?.let { TaskAdapter(it, this@MainActivity) }
                taskRecycler.adapter = taskAdapter
            }

        }

        GetTasks().execute()
    }

    override fun onTaskSelected(taskModel: TaskModel) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("task", taskModel.task)
        startActivity(intent)
    }

    override fun onUpdate(taskModel: TaskModel) {
        val alertLayout = layoutInflater.inflate(R.layout.alert_dialog, null)
        alertLayout.taskEditText.setText(taskModel.task.taskDescription)

        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setView(alertLayout)

        val alert = alertBuilder.create()
        alert.show()

        alertLayout.updateTask.setOnClickListener {
            val enteredString = alertLayout.taskEditText.text.toString().trim()
            if (enteredString.isNotEmpty() || enteredString.isNotBlank()) {
                class UpdateTask : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg p0: Void?): Void? {

                        taskModel.task.taskDescription = enteredString

                        DatabaseClient.getInstance(this@MainActivity)
                            .getAppDatabase()
                            .taskDao()
                            .update(taskModel.task)
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        fetchTasks()
                        alert.dismiss()
                        Toast.makeText(applicationContext, "Updated Task!", Toast.LENGTH_LONG).show()
                    }
                }

                UpdateTask().execute()
            } else Toast.makeText(applicationContext, "Empty taskModel!!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDelete(taskModel: TaskModel) {
        class DeleteTask : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                DatabaseClient.getInstance(this@MainActivity)
                    .getAppDatabase()
                    .taskDao()
                    .delete(taskModel.task)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                fetchTasks()
                Toast.makeText(applicationContext, "${taskModel.task.taskDescription} deleted!", Toast.LENGTH_LONG).show()
            }
        }

        DeleteTask().execute()
    }
}
