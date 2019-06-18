package com.example.ktodosample.activity

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.ktodosample.R
import com.example.ktodosample.database.DatabaseClient
import com.example.ktodosample.databinding.ActivityUpdateTaskBinding
import com.example.ktodosample.entity.Task
import com.example.ktodosample.model.TaskModel
import kotlinx.android.synthetic.main.activity_update_task.*

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var taskModel: TaskModel
    private lateinit var binding: ActivityUpdateTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_task)

        taskModel = intent.getSerializableExtra("task") as TaskModel
        loadTask(taskModel.task)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_task)
        binding.activity = this
        binding.taskModel = taskModel
    }

    fun updateTask(view: View) {
        Toast.makeText(applicationContext, "Clicked", Toast.LENGTH_LONG).show()
        updateTaskInternal(taskModel.task)
    }

    fun deleteTask(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure?")
        builder.setPositiveButton("Yes") { _, _ ->
            deleteTask(taskModel.task)
        }

        builder.setNegativeButton("Cancel") { _, _ -> }

        val alert = builder.create()
        alert.show()
    }

    private fun deleteTask(task: Task) {
        class DeleteTask : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg p0: Void?): Void? {
                DatabaseClient.getInstance(this@UpdateTaskActivity)
                    .getAppDatabase()
                    .taskDao()
                    .delete(task)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(applicationContext, "Deleted", Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
        DeleteTask().execute()
    }

    private fun updateTaskInternal(task: Task) {
        if (taskModel.taskTitle.isEmpty() || taskModel.taskTitle.isBlank()) {
            editTextTask.error = "Task Required"
            editTextTask.requestFocus()
            return
        }

        if (taskModel.taskDescription.isEmpty() || taskModel.taskDescription.isBlank()) {
            editTextTask.error = "Description Required"
            editTextTask.requestFocus()
            return
        }

        if (taskModel.finishBy.isEmpty() || taskModel.finishBy.isBlank()) {
            editTextTask.error = "Finish By Required"
            editTextTask.requestFocus()
            return
        }

        class UpdateTask : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg p0: Void?): Void? {
                task.task = taskModel.taskTitle
                task.description = taskModel.taskDescription
                task.finishBy = taskModel.finishBy
                task.finished = checkBoxFinished.isChecked

                DatabaseClient.getInstance(this@UpdateTaskActivity)
                    .getAppDatabase()
                    .taskDao()
                    .update(task)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        UpdateTask().execute()
    }

    private fun loadTask(task: Task) {
        taskModel.taskTitle = task.task
        taskModel.taskDescription = task.description
        taskModel.finishBy = task.finishBy
        taskModel.finished = task.finished
    }
}
